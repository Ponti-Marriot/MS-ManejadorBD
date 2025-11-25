package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pontimarriot.manejadorDB.model.Empleado;
import com.pontimarriot.manejadorDB.model.EmpleadoRequest;
import com.pontimarriot.manejadorDB.repository.EmpleadoRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado guardarEmpleado(EmpleadoRequest req) {

        log.info("➡️ Iniciando proceso de guardado de empleado...");
        log.info("📥 Datos recibidos en EmpleadoRequest: id_keycloak={}, nombre={}, correo={}", req.getId_keycloak(), req.getNombre(), req.getCorreo());
        Empleado emp = new Empleado();
        emp.setId_keycloak(req.getId_keycloak());
        emp.setNombre(req.getNombre());
        emp.setCorreo(req.getCorreo());
        Empleado guardado = empleadoRepository.save(emp);

        log.info("✅ Empleado guardado exitosamente en BD: {}", guardado);

        return guardado;
    }
}