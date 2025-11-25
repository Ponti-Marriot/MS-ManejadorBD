package com.pontimarriot.manejadorDB.controller.PropiedadesControllers;

import com.pontimarriot.manejadorDB.dtos.RoomDTO;
import com.pontimarriot.manejadorDB.service.PropiedadesServices.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/list")
    public List<RoomDTO> listRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping
    public RoomDTO createRoom(@RequestBody RoomDTO roomDTO) {
        return roomService.createRoom(roomDTO);
    }
}
