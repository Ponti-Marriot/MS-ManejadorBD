package com.pontimarriot.manejadorDB.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelacionResponse {
    private UUID id_solicitud_cancelacion;
    private String estado;  // "PENDIENTE", "APROBADO", "RECHAZADO"
    private LocalDateTime fecha_registro;
    private String observaciones;
}