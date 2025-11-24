package com.pontimarriot.manejadorDB.model;

import lombok.Data;

@Data
public class EmpleadoRequest {
    private String id_keycloak;
    private String nombre;
    private String correo;
}