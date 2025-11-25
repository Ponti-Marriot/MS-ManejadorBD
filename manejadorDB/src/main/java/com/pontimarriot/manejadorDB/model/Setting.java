package com.pontimarriot.manejadorDB.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Setting {

    private UUID id;
    private String key;
    private String value;
    private String description;
}