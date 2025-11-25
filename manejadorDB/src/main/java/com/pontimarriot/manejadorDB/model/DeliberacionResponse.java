package com.pontimarriot.manejadorDB.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliberacionResponse {
    private UUID id_reserva;
    private String estado;  // "CONFIRMADA" o "RECHAZADA"
}