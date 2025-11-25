package com.pontimarriot.manejadorDB.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RoomsResponseDTO {
    private UUID habitacion_id;
    private String tipo;
    private boolean disponible;
    private String codigo_tipo_habitacion;
    private double precio;
    private List<String> servicios_habitacion;

    public RoomsResponseDTO(){

    }
}
