package com.pontimarriot.manejadorDB.service;


import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationsService {

    private final ReservaRepository reservationRepository;

    public ReservationsService(ReservaRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Get all reservations
    public List<Reserva> getAllReservations() {
        return reservationRepository.findAll();
    }

    // Get reservation by id
    public Reserva getReservationById(UUID reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    // Example: reservations by room
    public List<Reserva> getReservationsByRoom(UUID roomId) {
        return reservationRepository.findByRoomId(roomId);
    }

    // Example: reservations by property
    public List<Reserva> getReservationsByHotelProperty(UUID propertyId) {
        return reservationRepository.findByHotelId(propertyId);
    }
}
