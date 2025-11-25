package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Room;
import com.pontimarriot.manejadorDB.repository.RoomJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomJpaRepository repository;

    public RoomController(RoomJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Room> save(@RequestBody Room room) {
        Room saved = repository.save(room);
        return ResponseEntity.ok(saved);
    }
/*
    @GetMapping("/by-availability/{availabilityDatesId}")
    public ResponseEntity<List<Room>> findByAvailabilityDatesId(@PathVariable UUID availabilityDatesId) {
        List<Room> rooms = repository.findByAvailabilityDatesId(availabilityDatesId);
        return ResponseEntity.ok(rooms);
    }

 */
}
