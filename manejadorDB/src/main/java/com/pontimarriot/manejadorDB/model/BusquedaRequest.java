package com.pontimarriot.manejadorDB.model;

import lombok.Data;

import java.util.Date;


@Data
public class BusquedaRequest {
    String ciudad_destino;
    Date fecha_checkin;
    Date fecha_checkout;
    Integer num_adultos;
    Integer num_habitaciones;
}
