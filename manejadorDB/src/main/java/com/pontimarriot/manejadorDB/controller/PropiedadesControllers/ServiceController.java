package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Service;
import com.pontimarriot.manejadorDB.repository.ServiceJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceJpaRepository repository;

    public ServiceController(ServiceJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Service>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<Service>> findByCategory(@PathVariable String category) {
        return ResponseEntity.ok(repository.findByCategory(category));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<Service>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(repository.findByName(name));
    }
}
