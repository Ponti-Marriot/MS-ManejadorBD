package com.pontimarriot.manejadorDB.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PropertyAndRoomsResponseDTO{
    private UUID hotel_id;
    private String nombre;
    private int categoria_estrellas;
    private String ciudad;
    private String direccion;
    private List<RoomsResponseDTO> habitaciones;
    private List<String> servicios_hotel;
    private List<String> fotos;

    public PropertyAndRoomsResponseDTO(){

    }
}
