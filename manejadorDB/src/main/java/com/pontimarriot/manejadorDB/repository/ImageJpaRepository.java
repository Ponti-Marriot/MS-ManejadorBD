package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageJpaRepository extends JpaRepository<Image, UUID> {
}
