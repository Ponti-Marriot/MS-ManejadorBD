package com.pontimarriot.manejadorDB.service.PropiedadesServices;

import com.pontimarriot.manejadorDB.Mappers.HotelPropertyMapper;
import com.pontimarriot.manejadorDB.dtos.HotelPropertyDTO;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertiesService {

    @Autowired
    private HotelPropertyRepository hotelPropertyRepository;

    public Optional<HotelPropertyDTO> getPropertyById(UUID propertyId) {
        return hotelPropertyRepository.findById(propertyId)
                .map(HotelPropertyMapper::toDTO);
    }

    public List<HotelPropertyDTO> getAllPropertiesStream() {
        return hotelPropertyRepository.findAll().stream()
                .map(HotelPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<HotelPropertyDTO> getAllProperties() {
        return hotelPropertyRepository.findAll().stream()
                .map(HotelPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public HotelPropertyDTO createProperty(HotelPropertyDTO propertyDTO) {
        return HotelPropertyMapper.toDTO(
                hotelPropertyRepository.save(HotelPropertyMapper.toEntity(propertyDTO))
        );
    }
}
