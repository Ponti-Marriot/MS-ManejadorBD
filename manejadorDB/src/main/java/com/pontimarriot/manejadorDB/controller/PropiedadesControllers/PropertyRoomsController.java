package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.dtos.HotelPropertyRoomDTO;
import com.pontimarriot.manejadorDB.service.PropiedadesServices.PropertyRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/property-rooms")
public class PropertyRoomsController {

    @Autowired
    PropertyRoomsService propertyRoomsService;

    @PostMapping
    public HotelPropertyRoomDTO addRoomToProperty(@RequestBody HotelPropertyRoomDTO hotelPropertyRoomDTO) {
        return propertyRoomsService.addRoomToProperty(hotelPropertyRoomDTO);
    }
}
