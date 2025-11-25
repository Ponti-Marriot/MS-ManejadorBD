package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.Mappers.HotelPropertyRoomMapper;
import com.pontimarriot.manejadorDB.dtos.HotelPropertyRoomDTO;
import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyRoomsService {

    @Autowired
    private HotelPropertyRoomRepository hotelPropertyRoomRepository;

    public HotelPropertyRoomDTO addRoomToProperty(HotelPropertyRoomDTO hotelPropertyRoomDTO) {
        HotelPropertyRoom hotelPropertyRoom = HotelPropertyRoomMapper.toEntity(hotelPropertyRoomDTO);
        HotelPropertyRoom saved = hotelPropertyRoomRepository.save(hotelPropertyRoom);
        return HotelPropertyRoomMapper.toDTO(saved);
    }
}