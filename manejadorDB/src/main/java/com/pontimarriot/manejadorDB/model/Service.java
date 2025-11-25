package com.pontimarriot.manejadorDB.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String category;
    private String description;

    public Service() {
    }

    public Service(String name, String category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }
}
