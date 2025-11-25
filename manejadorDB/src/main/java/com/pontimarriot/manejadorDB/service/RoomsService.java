package com.pontimarriot.manejadorDB.service;

import com.pontimarriot.manejadorDB.model.*;
import com.pontimarriot.manejadorDB.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomsService {

    private final RoomJpaRepository roomRepository;
    private final HotelPropertyRoomRepository hotelPropertyRoomRepository;
    private final HotelPropertyRepository hotelPropertyRepository;
    private final RoomServicesJpaRepository roomServicesRepository;
    private final ServiceJpaRepository serviceRepository;
    private final AvailabilityDatesRepository availabilityDatesRepository;

    public RoomsService(RoomJpaRepository roomRepository,
                        HotelPropertyRoomRepository hotelPropertyRoomRepository,
                        HotelPropertyRepository hotelPropertyRepository,
                        RoomServicesJpaRepository roomServicesRepository,
                        ServiceJpaRepository serviceRepository,
                        AvailabilityDatesRepository availabilityDatesRepository) {
        this.roomRepository = roomRepository;
        this.hotelPropertyRoomRepository = hotelPropertyRoomRepository;
        this.hotelPropertyRepository = hotelPropertyRepository;
        this.roomServicesRepository = roomServicesRepository;
        this.serviceRepository = serviceRepository;
        this.availabilityDatesRepository = availabilityDatesRepository;
    }

    // --- Rooms ---

    // Get all rooms
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    // Get room by id
    public Room getRoomById(UUID roomId) {
        return roomRepository.findById(roomId)
                .orElse(null);
    }

    // Get all rooms of a given hotel property
    public List<Room> getRoomsByHotelProperty(UUID hotelPropertyId) {
        List<HotelPropertyRoom> relations = hotelPropertyRoomRepository.findByHotelPropertyId(hotelPropertyId);
        List<Room> result = new ArrayList<>();

        for (HotelPropertyRoom rel : relations) {
            roomRepository.findById(rel.getRoomId()).ifPresent(room -> result.add(room));
        }
        return result;
    }

    // --- Room services ---

    // Get all services available for a room
    public List<com.pontimarriot.manejadorDB.model.Service> getServicesByRoom(UUID roomId) {
        List<RoomServices> rsList = roomServicesRepository.findByRoomId(roomId);
        List<com.pontimarriot.manejadorDB.model.Service> services = new ArrayList<>();

        for (RoomServices rs : rsList) {
            serviceRepository.findById(rs.getServiceId())
                    .ifPresent(service -> services.add(service));
        }
        return services;
    }

    // --- Room availability ---

    // Get all availability ranges of a room
    public List<AvailabilityDates> getAvailabilityByRoom(UUID roomId) {
        return availabilityDatesRepository.findByHotelpropertiesroomsId(roomId);
    }
    // --- Hotel properties ---

    // Get all properties
    public List<HotelProperty> getAllHotelProperties() {
        return hotelPropertyRepository.findAll();
    }

    // Get property by id
    public HotelProperty getHotelPropertyById(UUID hotelPropertyId) {
        return hotelPropertyRepository.findById(hotelPropertyId)
                .orElse(null);
    }
}