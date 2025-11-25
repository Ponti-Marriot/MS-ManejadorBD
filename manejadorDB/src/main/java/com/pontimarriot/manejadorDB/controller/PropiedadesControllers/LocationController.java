package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Location;
import com.pontimarriot.manejadorDB.repository.LocationJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationJpaRepository repository;

    public LocationController(LocationJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Location>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        Location saved = repository.save(location);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/save-all")
    public ResponseEntity<List<Location>> saveAll(@RequestBody List<Location> locations) {
        List<Location> saved = repository.saveAll(locations);
        return ResponseEntity.ok(saved);
    }
}
