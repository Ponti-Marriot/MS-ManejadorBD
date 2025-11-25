package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.model.ReservaRequest;
import com.pontimarriot.manejadorDB.model.ReservaResponse;
import com.pontimarriot.manejadorDB.model.Room;
import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;
import com.pontimarriot.manejadorDB.repository.RoomRepository;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;

import java.math.BigDecimal;
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

    @Transactional
    public List<ReservaResponse> guardarReserva(ReservaRequest req) {

        validarFechas(req.getFecha_checkin(), req.getFecha_checkout());

        List<Room> roomsDisponibles = obtenerHabitacionesDisponibles(
                req.getCodigo_tipo_habitacion(),
                req.getFecha_checkin(),
                req.getFecha_checkout(),
                req.getNum_habitaciones()
        );

        List<Reserva> reservasCreadas = new ArrayList<>();

        for (int i = 0; i < req.getNum_habitaciones(); i++) {
            Room room = roomsDisponibles.get(i);
            Reserva reserva = crearReserva(req, room);
            reservasCreadas.add(reserva);
        }

        // Guardar todas las reservas
        List<Reserva> reservasGuardadas = reservaRepository.saveAll(reservasCreadas);

        // Convertir a ReservaResponse
        return reservasGuardadas.stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private void validarFechas(Date checkIn, Date checkOut) {
        if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
            throw new IllegalArgumentException("La fecha de salida debe ser posterior a la de entrada");
        }
    }

    private List<Room> obtenerHabitacionesDisponibles(String roomType, Date checkIn, Date checkOut, int cantidad) {
        List<Room> todasLasRooms = roomRepository.findByRoomType(roomType);
        List<Room> disponibles = new ArrayList<>();

        for (Room room : todasLasRooms) {
            List<Reserva> reservasExistentes = reservaRepository
                    .findByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
                            room.getId(), checkOut, checkIn
                    );

            if (reservasExistentes.isEmpty()) {
                disponibles.add(room);
                if (disponibles.size() == cantidad) break;
            }
        }

        if (disponibles.size() < cantidad) {
            throw new RuntimeException("Solo hay " + disponibles.size() +
                    " habitaciones disponibles del tipo: " + roomType);
        }

        return disponibles;
    }

    private Reserva crearReserva(ReservaRequest req, Room room) {
        Reserva reserva = new Reserva();
        reserva.setGuestID(req.getCedula_reserva());
        reserva.setHotelId(req.getId_hotel());
        reserva.setRoomId(room.getId());
        reserva.setCheckIn(req.getFecha_checkin());
        reserva.setCheckOut(req.getFecha_checkout());
        reserva.setStatus("PENDIENTE");
        reserva.setCurrency("COP");

        // Calcular el precio total
        BigDecimal precioTotal = calcularPrecioTotal(req.getId_hotel(), room.getId(), req.getFecha_checkin(), req.getFecha_checkout());
        reserva.setPrice(precioTotal);

        return reserva;
    }

    private BigDecimal calcularPrecioTotal(UUID hotelId, UUID roomId, Date checkIn, Date checkOut) {
        // Buscar el precio por noche
        HotelPropertyRoom hotelRoom = hotelPropertyRoomRepository
                .findByHotelPropertyIdAndRoomId(hotelId, roomId)
                .orElseThrow(() -> new RuntimeException("No se encontró precio para esta habitación en el hotel"));

        BigDecimal precioPorNoche = BigDecimal.valueOf(hotelRoom.getPricePerNight());

        // Calcular número de noches
        long diferenciaMilis = checkOut.getTime() - checkIn.getTime();
        long numeroNoches = TimeUnit.DAYS.convert(diferenciaMilis, TimeUnit.MILLISECONDS);

        if (numeroNoches <= 0) {
            throw new IllegalArgumentException("Debe haber al menos una noche de diferencia");
        }

        // Precio total = precio por noche * número de noches
        return precioPorNoche.multiply(BigDecimal.valueOf(numeroNoches));
    }

    private ReservaResponse convertirAResponse(Reserva reserva) {
        ReservaResponse response = new ReservaResponse();
        response.setId_reserva(reserva.getId());
        response.setPrecio_total(reserva.getPrice());
        response.setEstado(reserva.getStatus());
        response.setObservaciones(null); // Por defecto null ya que no es obligatorio
        return response;
    }
}