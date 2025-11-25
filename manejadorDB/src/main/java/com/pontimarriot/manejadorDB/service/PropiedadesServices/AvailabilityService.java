package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.dtos.AvailabilityQueryDTO;
import com.pontimarriot.manejadorDB.dtos.PropertyAndRoomsResponseDTO;
import com.pontimarriot.manejadorDB.dtos.QueryResponseDTO;
import com.pontimarriot.manejadorDB.dtos.RoomsResponseDTO;
import com.pontimarriot.manejadorDB.model.*;
import com.pontimarriot.manejadorDB.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AvailabilityService {

    @Autowired
    HotelPropertyRepository hotelPropertyRepository;
    @Autowired
    HotelPropertyRoomRepository hotelPropertyRoomRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    HotelPropertyServicesRepository hotelPropertyServicesRepository;
    @Autowired
    AvailabilityDatesRepository availabilityDatesRepository;
    @Autowired
    LocationJpaRepository locationRepository;
    @Autowired
    RoomServicesJpaRepository roomServicesRepository;
    @Autowired
    ServiceJpaRepository serviceRepository;
    @Autowired
    ImageJpaRepository imageRepository;

    public QueryResponseDTO checkRoomsAvailability(AvailabilityQueryDTO availabilityQueryDTO) {
        QueryResponseDTO response = new QueryResponseDTO();
        List<PropertyAndRoomsResponseDTO> hoteles = new ArrayList<>();

        List<HotelProperty> properties = hotelPropertyRepository.findAll();
        String ciudad = availabilityQueryDTO.ciudad_destino();
        boolean filterByCity = ciudad != null && !ciudad.isBlank();

        LocalDate checkin = availabilityQueryDTO.fecha_checkin();
        LocalDate checkout = availabilityQueryDTO.fecha_checkout();
        int numAdultos = availabilityQueryDTO.num_adultos();
        int numHabitacionesRequested = availabilityQueryDTO.num_habitaciones();

        String expectedRoomType = mapRoomTypeForAdults(numAdultos);

        for (HotelProperty hp : properties) {
            UUID locId = hp.getLocationId();
            Optional<Location> locOpt = (locId != null) ? locationRepository.findById(locId) : Optional.empty();
            String cityName = locOpt.map(Location::getCityName).orElse(null);

            if (filterByCity) {
                if (cityName == null || !ciudad.equalsIgnoreCase(cityName)) {
                    continue;
                }
            }

            PropertyAndRoomsResponseDTO dto = new PropertyAndRoomsResponseDTO();
            dto.setHotel_id(hp.getId());
            dto.setNombre(hp.getName());
            dto.setCategoria_estrellas(hp.getStars());
            dto.setDireccion(hp.getAddress());
            dto.setCiudad(cityName);

            // services
            List<com.pontimarriot.manejadorDB.model.HotelPropertyServices> hotelServices =
                    hotelPropertyServicesRepository.findByHotelPropertyId(hp.getId());

            Set<UUID> serviceIds = hotelServices.stream()
                    .map(com.pontimarriot.manejadorDB.model.HotelPropertyServices::getServiceId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            if (!serviceIds.isEmpty()) {
                List<com.pontimarriot.manejadorDB.model.Service> services =
                        serviceRepository.findAllById(serviceIds).stream().collect(Collectors.toList());

                List<String> serviceNames = services.stream()
                        .map(com.pontimarriot.manejadorDB.model.Service::getName)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                dto.setServicios_hotel(serviceNames);
            } else {
                dto.setServicios_hotel(new ArrayList<>());
            }

            // images
            Collection<UUID> imageIds = null;
            try {
                imageIds = (hp.getImagesId() != null) ? List.of(hp.getImagesId()) : Collections.emptyList();
            } catch (Exception e) {
                imageIds = Collections.emptyList();
            }

            if (!imageIds.isEmpty()) {
                List<Image> images = imageRepository.findAllById(imageIds);
                List<String> urls = images.stream()
                        .map(Image::getUrl)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                dto.setFotos(urls);
            } else {
                dto.setFotos(new ArrayList<>());
            }

            // Rooms: only consider properties that have more HotelPropertyRoom entries
            // matching the determined roomType than requested num_habitaciones
            List<HotelPropertyRoom> hotelRooms = hotelPropertyRoomRepository.findByHotelPropertyId(hp.getId());
            List<RoomsResponseDTO> habitaciones = new ArrayList<>();

            // Filter hotelRooms to those whose associated Room matches expectedRoomType
            List<HotelPropertyRoom> matchingHotelRooms = hotelRooms.stream()
                    .filter(hpr -> {
                        Optional<Room> r = roomRepository.findById(hpr.getRoomId());
                        return r.isPresent()
                                && r.get().getRoomType() != null
                                && r.get().getRoomType().equalsIgnoreCase(expectedRoomType);
                    })
                    .collect(Collectors.toList());

            if (matchingHotelRooms.size() > numHabitacionesRequested) {
                for (HotelPropertyRoom hpr : matchingHotelRooms) {
                    Optional<Room> roomOpt = roomRepository.findById(hpr.getRoomId());
                    if (roomOpt.isEmpty()) continue;
                    Room room = roomOpt.get();

                    // availability check: either no records or none overlap the requested window
                    List<AvailabilityDates> bookedRanges = availabilityDatesRepository.findByHotelpropertiesroomsId(hpr.getId());
                    boolean noConflict = bookedRanges.isEmpty() || bookedRanges.stream().allMatch(ad ->
                            ad.getFinishDate().isBefore(checkin) || ad.getStartDate().isAfter(checkout)
                    );

                    if (!noConflict) {
                        continue;
                    }

                    // Passed all checks -> build RoomsResponseDTO and set fields
                    RoomsResponseDTO roomDto = new RoomsResponseDTO();
                    roomDto.setHabitacion_id(room.getId());
                    roomDto.setTipo(room.getTitle());
                    roomDto.setDisponible(true);
                    roomDto.setPrecio(hpr.getPricePerNight());
                    roomDto.setCodigo_tipo_habitacion(room.getRoomType());

                    // Fetch room services and resolve service names
                    List<RoomServices> roomServiceLinks = roomServicesRepository.findByRoomId(room.getId());
                    Set<UUID> roomServiceIds = roomServiceLinks.stream()
                            .map(RoomServices::getServiceId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());

                    if (!roomServiceIds.isEmpty()) {
                        List<com.pontimarriot.manejadorDB.model.Service> roomServices = serviceRepository.findAllById(roomServiceIds);
                        List<String> roomServiceNames = roomServices.stream()
                                .map(com.pontimarriot.manejadorDB.model.Service::getName)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList());
                        roomDto.setServicios_habitacion(roomServiceNames);
                    } else {
                        roomDto.setServicios_habitacion(new ArrayList<>());
                    }

                    habitaciones.add(roomDto);
                }
            }

            dto.setHabitaciones(habitaciones);
            hoteles.add(dto);
        }

        response.setHoteles(hoteles);
        return response;
    }

    private String mapRoomTypeForAdults(int numAdultos) {
        if (numAdultos <= 1) return "simple";
        if (numAdultos == 2) return "doble";
        return "familiar";
    }
}
