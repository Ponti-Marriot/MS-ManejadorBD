package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.model.CancelacionRequest;
import com.pontimarriot.manejadorDB.model.CancelacionResponse;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;

import java.time.LocalDateTime;

@Service
public class CancelacionService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional
    public CancelacionResponse procesarCancelacion(CancelacionRequest request) {

        // 1️⃣ Validar datos de entrada
        validarRequest(request);

        // 2️⃣ Buscar la reserva
        Reserva reserva = reservaRepository.findById(request.getReservation_id())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la reserva con ID: " + request.getReservation_id()
                ));


        // 5️⃣ Determinar el estado según el origen
        String estadoResultante = "APROBADO";

        // 6️⃣ Actualizar el estado de la reserva
        reserva.setStatus("CANCELADA");
        Reserva reservaActualizada = reservaRepository.save(reserva);

        // 7️⃣ Crear y retornar la respuesta
        return new CancelacionResponse(
                reservaActualizada.getReservationID(),
                estadoResultante,
                LocalDateTime.now(),
                "Su reserva ha sido cancelada"
        );
    }

    private void validarRequest(CancelacionRequest request) {
        if (request.getReservation_id() == null) {
            throw new IllegalArgumentException("El ID de reserva es obligatorio");
        }
        if (request.getTransaction_id() == null) {
            throw new IllegalArgumentException("El ID de transacción es obligatorio");
        }
        if (request.getDocument_id() == null || request.getDocument_id().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        if (request.getOrigin() == null || request.getOrigin().trim().isEmpty()) {
            throw new IllegalArgumentException("El origen es obligatorio");
        }
        if (!"CLIENTE".equalsIgnoreCase(request.getOrigin()) &&
                !"HOTEL".equalsIgnoreCase(request.getOrigin())) {
            throw new IllegalArgumentException(
                    "El origen debe ser 'CLIENTE' o 'HOTEL'. Recibido: " + request.getOrigin()
            );
        }
        if (request.getCauses() == null || request.getCauses().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de cancelación es obligatorio");
        }
    }


    private String determinarEstado(String origen) {
        // Si es HOTEL: se aprueba automáticamente → APROBADO
        // Si es CLIENTE: queda pendiente de aprobación → PENDIENTE
        if ("HOTEL".equalsIgnoreCase(origen)) {
            return "APROBADO";
        } else {
            return "PENDIENTE";
        }
    }
}