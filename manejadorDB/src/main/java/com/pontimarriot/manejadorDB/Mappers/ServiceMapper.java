package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.ServiceDTO;
import com.pontimarriot.manejadorDB.model.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ServiceMapper {

    public static ServiceDTO toDTO(Service entity) {
        if (entity == null) return null;
        return new ServiceDTO(
                entity.getId(),
                entity.getName(),
                entity.getCategory(),
                entity.getText()
        );
    }

    public static Service toEntity(ServiceDTO dto) {
        if (dto == null) return null;
        return new Service(
                dto.name(),
                dto.category(),
                dto.text()
        );
    }

    public static List<ServiceDTO> toDTOList(List<Service> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ServiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Service> toEntityList(List<ServiceDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(ServiceMapper::toEntity)
                .collect(Collectors.toList());
    }
}
