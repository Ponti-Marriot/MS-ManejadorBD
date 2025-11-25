package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.HotelPropertyDTO;
import com.pontimarriot.manejadorDB.model.HotelProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HotelPropertyMapper {

    public static HotelPropertyDTO toDTO(HotelProperty entity) {
        if (entity == null) return null;

        return new HotelPropertyDTO(
                entity.getId(),
                entity.getName(),
                entity.getStars(),
                entity.getPropertyType(),
                entity.getLocationId(),
                entity.getAddress(),
                entity.getImagesId()
        );
    }

    public static HotelProperty toEntity(HotelPropertyDTO dto) {
        if (dto == null) return null;

        HotelProperty entity = new HotelProperty();
        entity.setName(dto.name());
        entity.setStars(dto.stars());
        entity.setPropertyType(dto.property_type());
        entity.setLocationId(dto.location_id());
        entity.setAddress(dto.address());
        entity.setImagesId(dto.images_id());

        return entity;
    }

    public static List<HotelPropertyDTO> toDTOList(List<HotelProperty> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(HotelPropertyMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<HotelProperty> toEntityList(List<HotelPropertyDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(HotelPropertyMapper::toEntity)
                .collect(Collectors.toList());
    }
}
