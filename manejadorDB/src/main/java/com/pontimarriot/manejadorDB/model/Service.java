package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String category;
    private String text;

    public Service() {
    }

    public Service(String name, String category, String text) {
        this.name = name;
        this.category = category;
        this.text = text;
    }
}
