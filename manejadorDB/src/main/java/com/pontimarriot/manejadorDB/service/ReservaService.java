package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.model.ReservaRequest;
import com.pontimarriot.manejadorDB.model.ReservaResponse;
import com.pontimarriot.manejadorDB.model.Room;
import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.model.AvailabilityDates;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;
import com.pontimarriot.manejadorDB.repository.RoomRepository;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;
import com.pontimarriot.manejadorDB.repository.AvailabilityDatesRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelPropertyRoomRepository hotelPropertyRoomRepository;

    @Autowired
    private AvailabilityDatesRepository availabilityDatesRepository;

    @Transactional
    public List<ReservaResponse> guardarReserva(ReservaRequest req) {

        validarFechas(req.getFecha_checkin(), req.getFecha_checkout());

        // Get available HotelPropertyRoom entries (will throw if not enough)
        List<HotelPropertyRoom> hprDisponibles = obtenerHabitacionesDisponibles(
                req.getCodigo_tipo_habitacion(),
                req.getFecha_checkin(),
                req.getFecha_checkout(),
                req.getNum_habitaciones(),
                req.getId_hotel()
        );

        List<Room> rooms = roomRepository.findByRoomType(req.getCodigo_tipo_habitacion());
        if (rooms.isEmpty()) {
            throw new RuntimeException("No se encontró ningún tipo de habitación con el código: " + req.getCodigo_tipo_habitacion());
        }
        Room targetRoom = rooms.get(0);

        // Calculate number of nights once
        long numeroNoches = calcularNumeroNoches(req.getFecha_checkin(), req.getFecha_checkout());

        // Calculate total price for all selected rooms (sum of price_per_night * nights)
        BigDecimal totalPrecio = calcularPrecioTotal(hprDisponibles, req.getFecha_checkin(), req.getFecha_checkout());

        // Convert dates once using UTC (same as availability calculation)
        LocalDate reqStart = req.getFecha_checkin().toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        LocalDate reqEnd = req.getFecha_checkout().toInstant().atZone(ZoneOffset.UTC).toLocalDate();

        // Normalize reservation Date fields to UTC start-of-day to avoid timezone shift
        Date normalizedCheckIn = Date.from(reqStart.atStartOfDay(ZoneOffset.UTC).toInstant());
        Date normalizedCheckOut = Date.from(reqEnd.atStartOfDay(ZoneOffset.UTC).toInstant());

        // Create and persist the single Reserva for all rooms (so we have reservation id)
        Reserva reserva = crearReservaParaMultiple(req, targetRoom, totalPrecio, normalizedCheckIn, normalizedCheckOut);
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Create availability blocks for each selected HotelPropertyRoom including reservationId
        List<AvailabilityDates> newAvailabilityEntries = new ArrayList<>();
        for (HotelPropertyRoom hpr : hprDisponibles) {
            AvailabilityDates ad = new AvailabilityDates(
                    hpr.getId(),
                    reqStart,
                    reqEnd,
                    LocalDate.now().toString(),
                    reservaGuardada.getId()
            );
            newAvailabilityEntries.add(ad);
        }

        // Persist availability blocks
        availabilityDatesRepository.saveAll(newAvailabilityEntries);

        // Return single-item list (one reservation for all rooms)
        return Collections.singletonList(convertirAResponse(reservaGuardada));
    }

    private void validarFechas(Date checkIn, Date checkOut) {
        if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la de entrada");
        }
    }

    /**
     * Finds HotelPropertyRoom entries available for the given hotel and room type code.
     * Returns exactly 'cantidad' items or throws if not enough available.
     */
    private List<HotelPropertyRoom> obtenerHabitacionesDisponibles(String roomTypeCode, Date checkIn, Date checkOut, int cantidad, UUID hotelId) {
        // 1) Find the unique Room by roomType code
        List<Room> rooms = roomRepository.findByRoomType(roomTypeCode);
        if (rooms.isEmpty()) {
            throw new RuntimeException("No se encontró ningún tipo de habitación con el código: " + roomTypeCode);
        }
        Room targetRoom = rooms.get(0); // unique by design

        // Convert request dates to LocalDate for comparison with AvailabilityDates
        LocalDate reqStart = checkIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate reqEnd = checkOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // 2) Get all HotelPropertyRoom entries for the hotel
        List<HotelPropertyRoom> hotelRooms = hotelPropertyRoomRepository.findByHotelPropertyId(hotelId);

        // 3) Filter those that reference the target Room id
        List<HotelPropertyRoom> candidateHPR = hotelRooms.stream()
                .filter(hpr -> hpr.getRoomId().equals(targetRoom.getId()))
                .collect(Collectors.toList());

        List<HotelPropertyRoom> disponibles = new ArrayList<>();

        // 4) For each candidate, check AvailabilityDates for overlaps
        for (HotelPropertyRoom hpr : candidateHPR) {
            List<AvailabilityDates> existing = availabilityDatesRepository.findByHotelpropertiesroomsId(hpr.getId());

            boolean overlaps = false;
            for (AvailabilityDates ad : existing) {
                LocalDate adStart = ad.getStartDate();
                LocalDate adEnd = ad.getFinishDate();

                // Overlap check (inclusive)
                if (!(reqEnd.isBefore(adStart) || reqStart.isAfter(adEnd))) {
                    overlaps = true;
                    break;
                }
            }

            // 5) If no overlap, it's available
            if (!overlaps) {
                disponibles.add(hpr);
                if (disponibles.size() == cantidad) break;
            }
        }

        if (disponibles.size() < cantidad) {
            throw new RuntimeException("Solo hay " + disponibles.size() +
                    " habitaciones disponibles del tipo: " + roomTypeCode);
        }

        return disponibles;
    }

    // Create a single Reserva representing multiple HotelPropertyRoom entries.
    private Reserva crearReservaParaMultiple(ReservaRequest req, Room room, BigDecimal totalPrice, Date normalizedCheckIn, Date normalizedCheckOut) {
        Reserva reserva = new Reserva();
        reserva.setGuestID(req.getCedula_reserva());
        reserva.setHotelId(req.getId_hotel());
        // Set roomId to the Room id (schema expects single room reference)
        reserva.setRoomId(room.getId());
        // Use normalized Date values (UTC start-of-day) to avoid timezone shifts
        reserva.setCheckIn(normalizedCheckIn);
        reserva.setCheckOut(normalizedCheckOut);
        reserva.setStatus("PENDIENTE");
        reserva.setCurrency("COP");

        // Total price for all rooms (already sum(price_per_night) * nights)
        reserva.setPrice(totalPrice);

        return reserva;
    }

    /**
     * Calculate total price for a list of HotelPropertyRoom entries:
     * sum of each room's price_per_night, then multiply by number of nights.
     */
    private BigDecimal calcularPrecioTotal(List<HotelPropertyRoom> hotelPropertyRooms, Date checkIn, Date checkOut) {
        long numeroNoches = calcularNumeroNoches(checkIn, checkOut);

        double sumPrecioPorNoche = hotelPropertyRooms.stream()
                .mapToDouble(HotelPropertyRoom::getPricePerNight)
                .sum();

        return BigDecimal.valueOf(sumPrecioPorNoche).multiply(BigDecimal.valueOf(numeroNoches));
    }

    private long calcularNumeroNoches(Date checkIn, Date checkOut) {
        long diferenciaMilis = checkOut.getTime() - checkIn.getTime();
        long numeroNoches = TimeUnit.DAYS.convert(diferenciaMilis, TimeUnit.MILLISECONDS);
        if (numeroNoches <= 0) {
            throw new IllegalArgumentException("Debe haber al menos una noche de diferencia");
        }
        return numeroNoches;
    }

    private ReservaResponse convertirAResponse(Reserva reserva) {
        ReservaResponse response = new ReservaResponse();
        response.setId_reserva(reserva.getId());
        response.setPrecio_total(reserva.getPrice());
        response.setEstado(reserva.getStatus());
        response.setObservaciones(null);
        return response;
    }
}
