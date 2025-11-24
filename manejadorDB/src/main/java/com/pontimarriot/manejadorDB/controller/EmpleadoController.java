package com.pontimarriot.manejadorDB.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pontimarriot.manejadorDB.model.Empleado;
import com.pontimarriot.manejadorDB.model.EmpleadoRequest;
import com.pontimarriot.manejadorDB.service.EmpleadoService;

@RestController
@RequestMapping("/db/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    public Empleado guardar(@RequestBody EmpleadoRequest req) {
        return empleadoService.guardarEmpleado(req);
    }
}