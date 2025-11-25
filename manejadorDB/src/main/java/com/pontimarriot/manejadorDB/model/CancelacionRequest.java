package com.pontimarriot.manejadorDB.model;
import lombok.Data;

import java.util.UUID;


@Data
public class CancelacionRequest {
    UUID id_reserva;
    UUID id_transaccion;
    String cedula_reserva;
    String origen_solicitud;
    String motivo;
    String observaciones;
}
