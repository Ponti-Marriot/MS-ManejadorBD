package com.pontimarriot.manejadorDB.service;

import com.pontimarriot.manejadorDB.dtos.HotelDTO;
import com.pontimarriot.manejadorDB.dtos.LocationSimpleDTO;
import com.pontimarriot.manejadorDB.dtos.RoomResponseDTO;
import com.pontimarriot.manejadorDB.model.*;
import com.pontimarriot.manejadorDB.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final LocationRepository locationRepository;

    public RoomsService(RoomJpaRepository roomRepository,
                        HotelPropertyRoomRepository hotelPropertyRoomRepository,
                        HotelPropertyRepository hotelPropertyRepository,
                        RoomServicesJpaRepository roomServicesRepository,
                        ServiceJpaRepository serviceRepository,
                        AvailabilityDatesRepository availabilityDatesRepository, LocationRepository locationRepository) {
        this.roomRepository = roomRepository;
        this.hotelPropertyRoomRepository = hotelPropertyRoomRepository;
        this.hotelPropertyRepository = hotelPropertyRepository;
        this.roomServicesRepository = roomServicesRepository;
        this.serviceRepository = serviceRepository;
        this.availabilityDatesRepository = availabilityDatesRepository;
        this.locationRepository = locationRepository;
    }

    // --- Rooms ---

    // Get all rooms
    public List<RoomResponseDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        List<RoomResponseDTO> result = new ArrayList<>();

        for (Room room : rooms) {

            // Relación con hotelproperty
            HotelPropertyRoom rel = hotelPropertyRoomRepository
                    .findByRoomId(room.getId())
                    .stream().findFirst().orElse(null);

            HotelDTO hotelDto = null;
            Double price = null;
            Integer bedrooms = null;
            Integer bathrooms = null;
            String status = "Available";

            if (rel != null) {

                // Precio y detalles
                price = rel.getPricePerNight();
                bedrooms = rel.getBedrooms();
                bathrooms = rel.getBathrooms();

                // Estado basado en availability
                status = computeStatus(rel.getId());

                // Obtener hotel
                HotelProperty hotel = hotelPropertyRepository
                        .findById(rel.getHotelPropertyId())
                        .orElse(null);

                if (hotel != null) {
                    Location loc = locationRepository.findById(hotel.getLocationId()).orElse(null);

                    if (loc != null) {
                        hotelDto = new HotelDTO(
                                hotel.getId().toString(),
                                hotel.getName(),
                                loc.getCityName(),
                                loc.getState(),        // region
                                loc.getCountryCode()
                        );
                    }
                }
            }

            // Construcción DTO final
            RoomResponseDTO dto = new RoomResponseDTO(
                    room.getId().toString(),
                    room.getTitle(),          // usamos title como roomNumber
                    room.getTitle(),
                    room.getDescription(),
                    room.getRoomType(),
                    price,
                    bedrooms,
                    bathrooms,
                    status,
                    hotelDto
            );

            result.add(dto);
        }

        return result;
    }

    private String computeStatus(UUID hprId) {
        LocalDate today = LocalDate.now();

        List<AvailabilityDates> ranges =
                availabilityDatesRepository.findByHotelpropertiesroomsId(hprId);

        for (AvailabilityDates range : ranges) {
            if (!today.isBefore(range.getStartDate()) &&
                    !today.isAfter(range.getFinishDate())) {
                return "Reserved";
            }
        }

        return "Available";
    }

    // Get room by id and convert to RoomResponseDTO
    public RoomResponseDTO getRoomById(UUID roomId) {
        Room room = roomRepository.findById(roomId)
                .orElse(null);

        if (room == null) {
            return null;
        }

        // Relación con hotelproperty
        HotelPropertyRoom rel = hotelPropertyRoomRepository
                .findByRoomId(room.getId())
                .stream().findFirst().orElse(null);

        HotelDTO hotelDto = null;
        Double price = null;
        Integer bedrooms = null;
        Integer bathrooms = null;
        String status = "Available";

        if (rel != null) {

            // Precio y detalles
            price = rel.getPricePerNight();
            bedrooms = rel.getBedrooms();
            bathrooms = rel.getBathrooms();

            // Estado basado en availability
            status = computeStatus(rel.getId());

            // Obtener hotel
            HotelProperty hotel = hotelPropertyRepository
                    .findById(rel.getHotelPropertyId())
                    .orElse(null);

            if (hotel != null) {
                Location loc = locationRepository.findById(hotel.getLocationId()).orElse(null);

                if (loc != null) {
                    hotelDto = new HotelDTO(
                            hotel.getId().toString(),
                            hotel.getName(),
                            loc.getCityName(),
                            loc.getState(),        // region
                            loc.getCountryCode()
                    );
                }
            }
        }

        // Construcción DTO final
        return new RoomResponseDTO(
                room.getId().toString(),
                room.getTitle(),          // usamos title como roomNumber
                room.getTitle(),
                room.getDescription(),
                room.getRoomType(),
                price,
                bedrooms,
                bathrooms,
                status,
                hotelDto
        );
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

    // Crear habitación para un hotel específico
    @Transactional
    public RoomResponseDTO createRoomForHotel(UUID hotelPropertyId,
                                              String title,
                                              String description,
                                              String roomType,
                                              BigDecimal pricePerNight,   // <-- BigDecimal
                                              Integer bedrooms,
                                              Integer bathrooms,
                                              List<UUID> serviceIds) {

        // 1. Verificar hotel
        HotelProperty hotel = hotelPropertyRepository.findById(hotelPropertyId)
                .orElseThrow(() -> new IllegalArgumentException("HotelProperty not found"));

        // 2. Crear Room
        Room room = new Room();
        room.setTitle(title);
        room.setDescription(description);
        room.setRoomType(roomType);
        room.setCreatedAt(LocalDateTime.now());
        room.setUpdatedAt(LocalDateTime.now());
        room = roomRepository.save(room);

        // 3. Crear relación HotelPropertyRoom
        double priceValue = pricePerNight != null ? pricePerNight.doubleValue() : 0.0;
        HotelPropertyRoom rel = new HotelPropertyRoom(
                hotel.getId(),
                room.getId(),
                bedrooms != null ? bedrooms : 0,
                bathrooms != null ? bathrooms : 0,
                priceValue
        );
        rel = hotelPropertyRoomRepository.save(rel);

        // 4. Crear servicios de la habitación (roomservices)
        if (serviceIds != null) {
            String now = LocalDateTime.now().toString();
            for (UUID serviceId : serviceIds) {
                if (!serviceRepository.existsById(serviceId)) {
                    throw new IllegalArgumentException("Service not found: " + serviceId);
                }
                RoomServices rs = new RoomServices(serviceId, room.getId(), now);
                roomServicesRepository.save(rs);
            }
        }

        // 5. Estado
        String status = computeStatus(rel.getId());

        // 6. HotelDTO
        HotelDTO hotelDto = null;
        Location loc = locationRepository.findById(hotel.getLocationId()).orElse(null);
        if (loc != null) {
            hotelDto = new HotelDTO(
                    hotel.getId().toString(),
                    hotel.getName(),
                    loc.getCityName(),
                    loc.getState(),
                    loc.getCountryCode()
            );
        }

        // 7. DTO de respuesta
        return new RoomResponseDTO(
                room.getId().toString(),
                room.getTitle(),
                room.getTitle(),
                room.getDescription(),
                room.getRoomType(),
                rel.getPricePerNight(),
                rel.getBedrooms(),
                rel.getBathrooms(),
                status,
                hotelDto
        );
    }

    // Actualizar datos de una habitación (incluye precio y servicios)
    @Transactional
    public RoomResponseDTO updateRoomForHotel(UUID roomId,
                                              UUID hotelPropertyId,
                                              String title,
                                              String description,
                                              String roomType,
                                              BigDecimal pricePerNight,   // <-- BigDecimal
                                              Integer bedrooms,
                                              Integer bathrooms,
                                              List<UUID> serviceIds) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        HotelPropertyRoom rel = hotelPropertyRoomRepository.findByRoomId(roomId)
                .stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("HotelPropertyRoom not found"));

        // Cambiar de hotel si llega uno nuevo
        if (hotelPropertyId != null && !Objects.equals(rel.getHotelPropertyId(), hotelPropertyId)) {
            hotelPropertyRepository.findById(hotelPropertyId)
                    .orElseThrow(() -> new IllegalArgumentException("HotelProperty not found"));
            rel.setHotelPropertyId(hotelPropertyId);
        }

        // Actualizar Room
        if (title != null) room.setTitle(title);
        if (description != null) room.setDescription(description);
        if (roomType != null) room.setRoomType(roomType);
        room.setUpdatedAt(LocalDateTime.now());
        roomRepository.save(room);

        // Actualizar relación
        if (bedrooms != null) rel.setBedrooms(bedrooms);
        if (bathrooms != null) rel.setBathrooms(bathrooms);
        if (pricePerNight != null) {
            rel.setPricePerNight(pricePerNight.doubleValue());
        }
        hotelPropertyRoomRepository.save(rel);

        // Actualizar servicios
        List<RoomServices> current = roomServicesRepository.findByRoomId(roomId);
        roomServicesRepository.deleteAll(current);

        if (serviceIds != null) {
            String now = LocalDateTime.now().toString();
            for (UUID serviceId : serviceIds) {
                if (!serviceRepository.existsById(serviceId)) {
                    throw new IllegalArgumentException("Service not found: " + serviceId);
                }
                RoomServices rs = new RoomServices(serviceId, roomId, now);
                roomServicesRepository.save(rs);
            }
        }

        // Estado
        String status = computeStatus(rel.getId());

        // HotelDTO
        HotelProperty hotel = hotelPropertyRepository.findById(rel.getHotelPropertyId()).orElse(null);
        HotelDTO hotelDto = null;
        if (hotel != null) {
            Location loc = locationRepository.findById(hotel.getLocationId()).orElse(null);
            if (loc != null) {
                hotelDto = new HotelDTO(
                        hotel.getId().toString(),
                        hotel.getName(),
                        loc.getCityName(),
                        loc.getState(),
                        loc.getCountryCode()
                );
            }
        }

        return new RoomResponseDTO(
                room.getId().toString(),
                room.getTitle(),
                room.getTitle(),
                room.getDescription(),
                room.getRoomType(),
                rel.getPricePerNight(),
                rel.getBedrooms(),
                rel.getBathrooms(),
                status,
                hotelDto
        );
    }

    // Eliminar habitación completamente (room + relación + servicios)
    @Transactional
    public void deleteRoomCompletely(UUID roomId) {

        // 1. borrar servicios de la habitación
        List<RoomServices> rsList = roomServicesRepository.findByRoomId(roomId);
        roomServicesRepository.deleteAll(rsList);

        // 2. borrar relaciones con hotel
        List<HotelPropertyRoom> relations = hotelPropertyRoomRepository.findByRoomId(roomId);
        hotelPropertyRoomRepository.deleteAll(relations);

        // 3. borrar availability de esas relaciones
        for (HotelPropertyRoom rel : relations) {
            List<AvailabilityDates> ranges =
                    availabilityDatesRepository.findByHotelpropertiesroomsId(rel.getId());
            availabilityDatesRepository.deleteAll(ranges);
        }

        // 4. borrar la room
        roomRepository.deleteById(roomId);
    }

    // Obtener todas las habitaciones de un hotel, con el mismo formato RoomResponseDTO
    public List<RoomResponseDTO> getRoomsByHotelPropertyDetailed(UUID hotelPropertyId) {
        List<HotelPropertyRoom> relations = hotelPropertyRoomRepository.findByHotelPropertyId(hotelPropertyId);
        List<RoomResponseDTO> result = new ArrayList<>();

        for (HotelPropertyRoom rel : relations) {

            Room room = roomRepository.findById(rel.getRoomId()).orElse(null);
            if (room == null) continue;

            Double price = rel.getPricePerNight();
            Integer bedrooms = rel.getBedrooms();
            Integer bathrooms = rel.getBathrooms();
            String status = computeStatus(rel.getId());

            HotelDTO hotelDto = null;
            HotelProperty hotel = hotelPropertyRepository.findById(rel.getHotelPropertyId()).orElse(null);
            if (hotel != null) {
                Location loc = locationRepository.findById(hotel.getLocationId()).orElse(null);
                if (loc != null) {
                    hotelDto = new HotelDTO(
                            hotel.getId().toString(),
                            hotel.getName(),
                            loc.getCityName(),
                            loc.getState(),
                            loc.getCountryCode()
                    );
                }
            }

            RoomResponseDTO dto = new RoomResponseDTO(
                    room.getId().toString(),
                    room.getTitle(),
                    room.getTitle(),
                    room.getDescription(),
                    room.getRoomType(),
                    price,
                    bedrooms,
                    bathrooms,
                    status,
                    hotelDto
            );

            result.add(dto);
        }

        return result;
    }

    public List<LocationSimpleDTO> getAllLocationsSimple() {
        List<Location> locations = locationRepository.findAll();
        List<LocationSimpleDTO> result = new ArrayList<>();

        for (Location loc : locations) {
            result.add(new LocationSimpleDTO(
                    loc.getId(),
                    loc.getCityName(),
                    loc.getState(),
                    loc.getCountryCode(),
                    loc.getCityCode(),
                    loc.getTimezone()
            ));
        }

        return result;
    }
}