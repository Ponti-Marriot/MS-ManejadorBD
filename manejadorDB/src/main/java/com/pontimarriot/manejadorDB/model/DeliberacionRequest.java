package com.pontimarriot.manejadorDB.model;

import lombok.Data;

import java.util.UUID;


@Data
public class DeliberacionRequest {
    UUID id_reserva;
    UUID id_transaccion;
    String estado;
}
