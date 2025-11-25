package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import org.springframework.web.bind.annotation.*;

import com.pontimarriot.manejadorDB.model.AvailabilityDates;
import com.pontimarriot.manejadorDB.repository.AvailabilityDatesRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/availability-dates")
public class AvailabilityDatesController {

    private final AvailabilityDatesRepository repository;

    public AvailabilityDatesController(AvailabilityDatesRepository repository) {
        this.repository = repository;
    }

    // Obtener todos los registros
    @GetMapping
    public List<AvailabilityDates> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Optional<AvailabilityDates> findById(@PathVariable UUID id) {
        return repository.findById(id);
    }

    // Buscar por roomId
    @GetMapping("/room/{roomId}")
    public List<AvailabilityDates> findByRoomId(@PathVariable UUID roomId) {
        return repository.findByRoomId(roomId);
    }
}
