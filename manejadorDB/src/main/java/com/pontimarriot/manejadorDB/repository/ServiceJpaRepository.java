package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceJpaRepository extends JpaRepository<Service, UUID> {

    List<Service> findByCategory(String category);

    List<Service> findByName(String name);
}
