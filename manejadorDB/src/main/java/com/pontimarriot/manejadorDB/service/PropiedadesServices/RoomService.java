package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.Mappers.RoomMapper;
import com.pontimarriot.manejadorDB.dtos.RoomDTO;
import com.pontimarriot.manejadorDB.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(RoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        return RoomMapper.toDTO(
                roomRepository.save(RoomMapper.toEntity(roomDTO))
        );
    }
}
