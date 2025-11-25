package com.pontimarriot.manejadorDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pontimarriot.manejadorDB.model.Reserva;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, UUID> {

    // Buscar reservas que se crucen con un rango de fechas
    List<Reserva> findByRoomIdAndCheckInLessThanEqualAndCheckOutGreaterThanEqual(
            UUID roomId, Date checkIn, Date checkOut
    );

    @Override
    Optional<Reserva> findById(UUID uuid);
}
