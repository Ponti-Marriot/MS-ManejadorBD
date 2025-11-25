package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "status")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Column(name = "created_at")
    private String createdAt;

    public Status() {
    }

    public Status(String name, String createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }
}
