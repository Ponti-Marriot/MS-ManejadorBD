package com.pontimarriot.manejadorDB.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaResponse {
    private UUID id_reserva;
    private BigDecimal precio_total;
    private String estado;
    private String observaciones;
}