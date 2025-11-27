package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.Mappers.HotelPropertyRoomMapper;
import com.pontimarriot.manejadorDB.dtos.HotelPropertyRoomDTO;
import com.pontimarriot.manejadorDB.dtos.RoomCreationRequestDTO;
import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import com.pontimarriot.manejadorDB.model.Room;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRoomRepository;
import com.pontimarriot.manejadorDB.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PropertyRoomsService {

    @Autowired
    private HotelPropertyRoomRepository hotelPropertyRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    public HotelPropertyRoomDTO addRoomToProperty(RoomCreationRequestDTO roomCreationRequestDTO) {
        UUID roomTypeId = roomRepository.findByRoomType(roomCreationRequestDTO.room_type())
                .map(Room::getId)
                .orElseThrow(() -> new RuntimeException("Room type not found: " + roomCreationRequestDTO.room_type()));
        HotelPropertyRoom hotelPropertyRoom = new HotelPropertyRoom(
                roomCreationRequestDTO.hotel_property_id(),
                roomTypeId,
                roomCreationRequestDTO.bedrooms(),
                roomCreationRequestDTO.bathrooms(),
                roomCreationRequestDTO.price_per_night()
        );
        HotelPropertyRoom savedPropertyRoom = hotelPropertyRoomRepository.save(hotelPropertyRoom);
        return HotelPropertyRoomMapper.toDTO(savedPropertyRoom);
    }
}