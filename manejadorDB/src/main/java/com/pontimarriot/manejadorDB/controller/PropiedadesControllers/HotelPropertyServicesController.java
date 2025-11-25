package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.HotelPropertyServices;
import com.pontimarriot.manejadorDB.repository.HotelPropertyServicesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotel-property-services")
public class HotelPropertyServicesController {

    private final HotelPropertyServicesRepository repository;

    public HotelPropertyServicesController(HotelPropertyServicesRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<HotelPropertyServices>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelPropertyServices> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-service/{serviceId}")
    public ResponseEntity<List<HotelPropertyServices>> findByServiceId(@PathVariable UUID serviceId) {
        return ResponseEntity.ok(repository.findByServiceId(serviceId));
    }

    @GetMapping("/by-property/{propertyId}")
    public ResponseEntity<List<HotelPropertyServices>> findByHotelPropertyId(@PathVariable UUID propertyId) {
        return ResponseEntity.ok(repository.findByHotelPropertyId(propertyId));
    }
}
