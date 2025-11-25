package com.pontimarriot.manejadorDB.model;

import lombok.Data;

import java.util.UUID;
import java.util.Date;

@Data
public class ReservaRequest {
    UUID id_hotel;
    String codigo_tipo_habitacion;
    Date fecha_checkin;
    Date fecha_checkout;
    String cedula_reserva;
    Integer num_habitaciones;
    Integer num_adultos;
}
