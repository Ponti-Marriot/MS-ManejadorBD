package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.dtos.AvailabilityQueryDTO;
import com.pontimarriot.manejadorDB.dtos.PropertyAndRoomsResponseDTO;
import com.pontimarriot.manejadorDB.dtos.QueryResponseDTO;
import com.pontimarriot.manejadorDB.service.PropiedadesServices.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/available-rooms")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public QueryResponseDTO checkRoomAvailability(@RequestBody AvailabilityQueryDTO availabilityQueryDTO) {
        return availabilityService.checkRoomsAvailability(availabilityQueryDTO);
    }
}
