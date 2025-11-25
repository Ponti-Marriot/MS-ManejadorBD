package com.pontimarriot.manejadorDB.controller;

import com.pontimarriot.manejadorDB.model.ReservaRequest;
import com.pontimarriot.manejadorDB.model.ReservaResponse;
import com.pontimarriot.manejadorDB.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/db/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<List<ReservaResponse>> crearReserva(@RequestBody ReservaRequest request) {
        List<ReservaResponse> respuesta = reservaService.guardarReserva(request);
        return ResponseEntity.ok(respuesta);
    }
}