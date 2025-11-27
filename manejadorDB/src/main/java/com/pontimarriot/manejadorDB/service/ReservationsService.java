package com.pontimarriot.manejadorDB.service;

import com.pontimarriot.manejadorDB.dtos.ReservationDetailsDTO;
import com.pontimarriot.manejadorDB.dtos.ReservationSummaryDTO;
import com.pontimarriot.manejadorDB.model.HotelProperty;
import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.model.Location;
import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.model.Room;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRepository;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;
import com.pontimarriot.manejadorDB.repository.LocationRepository;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;
import com.pontimarriot.manejadorDB.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationsService {

    private final ReservaRepository reservationRepository;
    private final HotelPropertyRoomRepository hotelPropertyRoomRepository;
    private final HotelPropertyRepository hotelPropertyRepository;
    private final LocationRepository locationRepository;
    private final RoomRepository roomRepository;

    // ===== Listado para la tabla (summary) =====
    public List<ReservationSummaryDTO> getAllReservations() {
        return reservationRepository.findAllByOrderByCheckInDesc()
                .stream()
                .map(this::toSummaryDto)
                .toList();
    }

    // ===== Detalle para el popup =====
    public ReservationDetailsDTO getReservationDetails(UUID id) {
        Reserva r = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + id));

        return toDetailsDto(r);
    }

    // ===== Mappers =====

    private ReservationSummaryDTO toSummaryDto(Reserva r) {
        String reservationNumber = r.getId() != null
                ? "R-" + r.getId().toString().substring(0, 8)
                : null;

        // Ahora usamos el campo que realmente tienes en la entidad
        String guestName = r.getGuestID(); // placeholder de nombre

        BigDecimal totalAmount = r.getPrice();

        LocalDateTime checkIn = toLocalDateTime(r.getCheckIn());
        LocalDateTime checkOut = toLocalDateTime(r.getCheckOut());

        return new ReservationSummaryDTO(
                r.getId(),
                reservationNumber,
                guestName,
                checkIn,
                checkOut,
                totalAmount,
                r.getStatus()
        );
    }

    private ReservationDetailsDTO toDetailsDto(Reserva r) {
        ReservationDetailsDTO dto = new ReservationDetailsDTO();

        // ===== Datos base de la reserva =====
        dto.setId(r.getId());
        dto.setReservationNumber(
                r.getId() != null ? "R-" + r.getId().toString().substring(0, 8) : null
        );

        dto.setGuestId(r.getGuestID());
        dto.setGuestName(r.getGuestID()); // placeholder hasta que conectes con tabla de huéspedes
        dto.setObservations(null);        // Reserva actual no tiene campo observaciones

        dto.setCheckIn(toLocalDateTime(r.getCheckIn()));
        dto.setCheckOut(toLocalDateTime(r.getCheckOut()));
        dto.setCurrency(r.getCurrency());
        dto.setTotalAmount(r.getPrice());
        dto.setStatus(r.getStatus());
        dto.setCancelledAt(null);        // Reserva actual no tiene cancelled_at

        // ===== Room & Hotel =====
        // En reservations.room_id tienes el ID de hotelpropertiesrooms
        UUID hprId = r.getRoomId();
        UUID hotelIdFromReservation = r.getHotelId();

        if (hprId != null) {
            Optional<HotelPropertyRoom> optHpr = hotelPropertyRoomRepository.findById(hprId);
            if (optHpr.isPresent()) {
                HotelPropertyRoom hpr = optHpr.get();

                // Info del "hotelpropertiesrooms"
                dto.setRoomId(hpr.getId());
                dto.setBedrooms(hpr.getBedrooms());
                dto.setBathrooms(hpr.getBathrooms());
                dto.setPricePerNight(BigDecimal.valueOf(hpr.getPricePerNight()));

                // Info de la habitación base (rooms)
                if (hpr.getRoomId() != null) {
                    roomRepository.findById(hpr.getRoomId()).ifPresent(room -> {
                        dto.setRoomTitle(room.getTitle());
                        dto.setRoomType(room.getRoomType()); // simple / doble / familiar...
                    });
                }

                // Determinar qué hotel usar:
                //  - Si la reserva tiene hotelId, usamos ese.
                //  - Si no, usamos el hotel_property_id del HPR.
                UUID effectiveHotelId = hotelIdFromReservation != null
                        ? hotelIdFromReservation
                        : hpr.getHotelPropertyId();

                if (effectiveHotelId != null) {
                    hotelPropertyRepository.findById(effectiveHotelId).ifPresent(hp -> {
                        dto.setHotelId(hp.getId());
                        dto.setHotelName(hp.getName());
                        dto.setHotelAddress(hp.getAddress());

                        // Cargar Location a partir de locationId
                        if (hp.getLocationId() != null) {
                            locationRepository.findById(hp.getLocationId()).ifPresent(loc -> {
                                dto.setCity(loc.getCityName());
                                dto.setCountry(loc.getCountryCode());
                                dto.setRegion(loc.getSubdivCode());
                            });
                        }
                    });
                }
            }
        }

        return dto;
    }

    // ===== Helper para convertir Date -> LocalDateTime =====
    private LocalDateTime toLocalDateTime(java.util.Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public List<ReservationSummaryDTO> getReservationsByRoom(UUID roomId) {
        return reservationRepository.findAllByOrderByCheckInDesc()
                .stream()
                .filter(r -> roomId.equals(r.getRoomId()))
                .map(this::toSummaryDto)
                .toList();
    }

    public List<ReservationSummaryDTO> getReservationsByHotelProperty(UUID hotelPropertyId) {
        return reservationRepository.findAllByOrderByCheckInDesc()
                .stream()
                .filter(r -> hotelPropertyId.equals(r.getHotelId()))
                .map(this::toSummaryDto)
                .toList();
    }

}
