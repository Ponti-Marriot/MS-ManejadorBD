package com.pontimarriot.manejadorDB.controller;

import com.pontimarriot.manejadorDB.model.DeliberacionRequest;
import com.pontimarriot.manejadorDB.model.DeliberacionResponse;
import com.pontimarriot.manejadorDB.service.DeliberacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/db/reservas")
public class DeliberacionController {

    @Autowired
    private DeliberacionService deliberacionService;

    @PutMapping("/deliberacion")
    public ResponseEntity<DeliberacionResponse> procesarDeliberacion(
            @RequestBody DeliberacionRequest request) {

        DeliberacionResponse response = deliberacionService.procesarDeliberacion(request);
        return ResponseEntity.ok(response);
    }
}