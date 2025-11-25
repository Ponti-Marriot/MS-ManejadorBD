package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StatusJpaRepository extends JpaRepository<Status, UUID> {

    List<Status> findByName(String name);
}
