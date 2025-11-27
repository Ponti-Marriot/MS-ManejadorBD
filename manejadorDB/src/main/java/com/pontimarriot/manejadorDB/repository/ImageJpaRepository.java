package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, UUID> {

    Optional<Image> findByUrl(String url);
}
