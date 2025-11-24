package com.pontimarriot.manejadorDB.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pontimarriot.manejadorDB.model.Empleado;
import com.pontimarriot.manejadorDB.model.EmpleadoRequest;
import com.pontimarriot.manejadorDB.repository.EmpleadoRepository;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public Empleado guardarEmpleado(EmpleadoRequest req) {

        Empleado emp = new Empleado();
        emp.setId_keycloak(req.getId_keycloak());
        emp.setNombre(req.getNombre());
        emp.setCorreo(req.getCorreo());

        return empleadoRepository.save(emp);
    }
}