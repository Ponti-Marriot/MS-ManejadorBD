package com.pontimarriot.manejadorDB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.pontimarriot.manejadorDB.model.Empleado;
import java.util.UUID;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, UUID> {
}