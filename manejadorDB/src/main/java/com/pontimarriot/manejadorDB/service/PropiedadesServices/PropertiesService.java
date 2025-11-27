package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.Mappers.HotelPropertyMapper;
import com.pontimarriot.manejadorDB.dtos.HotelCreationRequestDTO;
import com.pontimarriot.manejadorDB.dtos.HotelPropertyDTO;
import com.pontimarriot.manejadorDB.model.HotelProperty;
import com.pontimarriot.manejadorDB.model.Image;
import com.pontimarriot.manejadorDB.model.Location;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRepository;
import com.pontimarriot.manejadorDB.repository.ImageJpaRepository;
import com.pontimarriot.manejadorDB.repository.LocationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertiesService {

    @Autowired
    private HotelPropertyRepository hotelPropertyRepository;

    @Autowired
    private LocationJpaRepository locationJpaRepository;

    @Autowired
    private ImageJpaRepository imageJpaRepository;

    public List<HotelPropertyDTO> getAllProperties() {
        return hotelPropertyRepository.findAll().stream()
                .map(HotelPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HotelPropertyDTO createProperty(HotelCreationRequestDTO propertyDTO) {
        UUID locationId = locationJpaRepository.findFirstByCityNameIgnoreCase(propertyDTO.city())
                .map(Location::getId)
                .orElseThrow(() -> new RuntimeException("Location not found for city: " + propertyDTO.city()));
        UUID imageId = imageJpaRepository.findByUrl(propertyDTO.url())
                .map(Image::getId)
                .orElseGet(() -> {
                    Image newImage = new Image();
                    newImage.setUrl(propertyDTO.url());
                    return imageJpaRepository.save(newImage).getId();
                });
        HotelProperty hotelProperty = new HotelProperty(
                propertyDTO.name(),
                propertyDTO.stars(),
                propertyDTO.property_type(),
                locationId,
                propertyDTO.address(),
                imageId
        );
        HotelProperty savedProperty = hotelPropertyRepository.save(hotelProperty);
        return HotelPropertyMapper.toDTO(savedProperty);
    }

    public List<HotelPropertyDTO> getPropertyByName(String propertyName) {
        return hotelPropertyRepository.findByName(propertyName).stream()
                .map(HotelPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HotelPropertyDTO getPropertyByAddress(String propertyAddress) {
        return hotelPropertyRepository.findByAddress(propertyAddress)
                .map(HotelPropertyMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Property not found for address: " + propertyAddress));
    }
}
