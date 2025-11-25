package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.RoomServices;
import com.pontimarriot.manejadorDB.repository.RoomServicesJpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/room-services")
public class RoomServicesController {

    private final RoomServicesJpaRepository repository;

    public RoomServicesController(RoomServicesJpaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<RoomServices>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomServices> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-service/{serviceId}")
    public ResponseEntity<List<RoomServices>> findByServiceId(@PathVariable UUID serviceId) {
        return ResponseEntity.ok(repository.findByServiceId(serviceId));
    }

    @GetMapping("/by-room/{roomId}")
    public ResponseEntity<List<RoomServices>> findByRoomId(@PathVariable UUID roomId) {
        return ResponseEntity.ok(repository.findByRoomId(roomId));
    }

    @PostMapping
    public ResponseEntity<RoomServices> save(@RequestBody RoomServices roomServices) {
        RoomServices saved = repository.save(roomServices);
        return ResponseEntity.ok(saved);
    }
}
