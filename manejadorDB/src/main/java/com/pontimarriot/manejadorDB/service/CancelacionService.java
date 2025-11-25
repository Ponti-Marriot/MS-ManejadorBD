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
        Reserva reserva = reservaRepository.findById(request.getId_reserva())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la reserva con ID: " + request.getId_reserva()
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
        if (request.getId_reserva() == null) {
            throw new IllegalArgumentException("El ID de reserva es obligatorio");
        }
        if (request.getId_transaccion() == null) {
            throw new IllegalArgumentException("El ID de transacción es obligatorio");
        }
        if (request.getCedula_reserva() == null || request.getCedula_reserva().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento es obligatorio");
        }
        if (request.getOrigen_solicitud() == null || request.getOrigen_solicitud().trim().isEmpty()) {
            throw new IllegalArgumentException("El origen es obligatorio");
        }
        if (!"CLIENTE".equalsIgnoreCase(request.getOrigen_solicitud()) &&
                !"HOTEL".equalsIgnoreCase(request.getOrigen_solicitud())) {
            throw new IllegalArgumentException(
                    "El origen debe ser 'CLIENTE' o 'HOTEL'. Recibido: " + request.getOrigen_solicitud()
            );
        }
        if (request.getMotivo() == null || request.getMotivo().trim().isEmpty()) {
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