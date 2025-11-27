package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.dtos.HotelPropertyRoomDTO;
import com.pontimarriot.manejadorDB.dtos.RoomCreationRequestDTO;
import com.pontimarriot.manejadorDB.service.PropiedadesServices.PropertyRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "http://10.43.103.38:4200"
})
@RequestMapping("/db/reservas/property-rooms")
public class PropertyRoomsController {

    @Autowired
    PropertyRoomsService propertyRoomsService;

    @PostMapping
    public HotelPropertyRoomDTO addRoomToProperty(@RequestBody RoomCreationRequestDTO roomCreationRequestDTO) {
        return propertyRoomsService.addRoomToProperty(roomCreationRequestDTO);
    }
}
