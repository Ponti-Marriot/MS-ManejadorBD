package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pontimarriot.manejadorDB.model.Reserva;
import com.pontimarriot.manejadorDB.model.DeliberacionRequest;
import com.pontimarriot.manejadorDB.model.DeliberacionResponse;
import com.pontimarriot.manejadorDB.repository.ReservaRepository;

@Service
public class DeliberacionService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional
    public DeliberacionResponse procesarDeliberacion(DeliberacionRequest request) {

        // 1️⃣ Validar que el estado sea válido
        validarEstado(request.getEstado());

        // 2️⃣ Buscar la reserva
        Reserva reserva = reservaRepository.findById(request.getId_reserva())
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró la reserva con ID: " + request.getId_reserva()
                ));

        // 3️⃣ Validar que la reserva esté en estado PENDIENTE
        if (!"PENDIENTE".equals(reserva.getStatus())) {
            throw new RuntimeException(
                    "La reserva ya fue procesada. Estado actual: " + reserva.getStatus()
            );
        }

        // 4️⃣ Actualizar el estado según el pago
        String nuevoEstado = determinarEstadoFinal(request.getEstado());
        reserva.setStatus(nuevoEstado);

        // 5️⃣ Guardar la reserva actualizada
        Reserva reservaActualizada = reservaRepository.save(reserva);

        // 6️⃣ Crear y retornar la respuesta
        return new DeliberacionResponse(
                reservaActualizada.getReservationID(),
                convertirEstadoRespuesta(reservaActualizada.getStatus())
        );
    }

    private void validarEstado(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío");
        }

        if (!"CONFIRMADO".equalsIgnoreCase(status) && !"DENEGADO".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException(
                    "Estado inválido. Debe ser 'CONFIRMADO' o 'DENEGADO'. Recibido: " + status
            );
        }
    }

    private String determinarEstadoFinal(String estadoPago) {
        // Si el pago fue CONFIRMADO -> reserva CONFIRMADA
        // Si el pago fue DENEGADO -> reserva CANCELADA (libera los cupos)
        return "CONFIRMADO".equalsIgnoreCase(estadoPago) ? "CONFIRMADA" : "CANCELADA";
    }

    private String convertirEstadoRespuesta(String estadoInterno) {
        // Convertir el estado interno a la respuesta esperada
        if ("CONFIRMADA".equals(estadoInterno)) {
            return "CONFIRMADA";
        } else if ("CANCELADA".equals(estadoInterno)) {
            return "RECHAZADA";
        }
        return estadoInterno;
    }
}