package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Status;
import com.pontimarriot.manejadorDB.repository.StatusJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/statuses")
public class StatusController {

    private final StatusJpaRepository repository;

    public StatusController(StatusJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Status>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<Status>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(repository.findByName(name));
    }
}
