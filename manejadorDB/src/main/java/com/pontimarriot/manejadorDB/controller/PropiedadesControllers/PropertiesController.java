package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.dtos.HotelPropertyDTO;
import com.pontimarriot.manejadorDB.service.PropiedadesServices.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertiesController {
    @Autowired
    private PropertiesService propertiesService;

    // Return a Mono containing the whole list (non-blocking)
    @GetMapping("/propertyList")
    public List<HotelPropertyDTO> getProperties() {
        return propertiesService.getAllProperties();
    }

    @PostMapping
    public HotelPropertyDTO createProperty(@RequestBody HotelPropertyDTO propertyDTO) {
        return propertiesService.createProperty(propertyDTO);
    }
}