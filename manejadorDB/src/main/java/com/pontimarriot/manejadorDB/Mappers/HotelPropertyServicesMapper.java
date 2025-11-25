package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.HotelPropertyServicesDTO;
import com.pontimarriot.manejadorDB.model.HotelPropertyServices;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class HotelPropertyServicesMapper {

    public static HotelPropertyServicesDTO toDTO(HotelPropertyServices entity) {
        if (entity == null) return null;
        return new HotelPropertyServicesDTO(
                entity.getId(),
                entity.getServiceId(),
                entity.getHotelPropertyId()
        );
    }

    public static HotelPropertyServices toEntity(HotelPropertyServicesDTO dto) {
        if (dto == null) return null;
        return new HotelPropertyServices(
                dto.service_id(),
                dto.hotel_property_id()
        );
    }

    public static List<HotelPropertyServicesDTO> toDTOList(List<HotelPropertyServices> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(HotelPropertyServicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<HotelPropertyServices> toEntityList(List<HotelPropertyServicesDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(HotelPropertyServicesMapper::toEntity)
                .collect(Collectors.toList());
    }
}
