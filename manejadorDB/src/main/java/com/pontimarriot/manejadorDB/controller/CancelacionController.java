package com.pontimarriot.manejadorDB.controller;

import com.pontimarriot.manejadorDB.model.CancelacionRequest;
import com.pontimarriot.manejadorDB.model.CancelacionResponse;
import com.pontimarriot.manejadorDB.service.CancelacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/db/reservas")
public class CancelacionController {

    @Autowired
    private CancelacionService cancelacionService;

    @PutMapping("/cancelacion")
    public ResponseEntity<CancelacionResponse> cancelarReserva(
            @RequestBody CancelacionRequest request) {

        CancelacionResponse response = cancelacionService.procesarCancelacion(request);
        return ResponseEntity.ok(response);
    }
}