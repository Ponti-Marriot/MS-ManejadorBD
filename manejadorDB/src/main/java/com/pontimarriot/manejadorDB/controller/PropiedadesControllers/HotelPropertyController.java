package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.HotelProperty;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotel-properties")
public class HotelPropertyController {

    private final HotelPropertyRepository repository;

    public HotelPropertyController(HotelPropertyRepository repository) {
        this.repository = repository;
    }

        @GetMapping
    public List<HotelProperty> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelProperty> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/location/{locationId}")
    public List<HotelProperty> findByLocationId(@PathVariable UUID locationId) {
        return repository.findByLocationId(locationId);
    }

    @GetMapping("/availability/{availabilityDatesId}")
    public List<HotelProperty> findByAvailabilityDatesId(@PathVariable UUID availabilityDatesId) {
        return repository.findByAvailabilityDatesId(availabilityDatesId);
    }

    @PostMapping
    public HotelProperty save(@RequestBody HotelProperty hotelProperty) {
        return repository.save(hotelProperty);
    }
}
