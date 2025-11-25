package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotel-property-rooms")
public class HotelPropertyRoomController {

    private final HotelPropertyRoomRepository repository;

    public HotelPropertyRoomController(HotelPropertyRoomRepository repository) {
        this.repository = repository;
    }

    // Obtener todos
    @GetMapping
    public List<HotelPropertyRoom> findAll() {
        return repository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<HotelPropertyRoom> findById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar por hotelPropertyId
    @GetMapping("/hotel-property/{hotelPropertyId}")
    public List<HotelPropertyRoom> findByHotelPropertyId(@PathVariable UUID hotelPropertyId) {
        return repository.findByHotelPropertyId(hotelPropertyId);
    }

    // Buscar por roomId
    @GetMapping("/room/{roomId}")
    public List<HotelPropertyRoom> findByRoomId(@PathVariable UUID roomId) {
        return repository.findByRoomId(roomId);
    }
}
