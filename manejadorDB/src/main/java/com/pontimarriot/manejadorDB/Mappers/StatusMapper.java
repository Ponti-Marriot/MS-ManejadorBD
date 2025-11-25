package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.StatusDTO;
import com.pontimarriot.manejadorDB.model.Status;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class StatusMapper {

    public static StatusDTO toDTO(Status entity) {
        if (entity == null) return null;
        String createdAt = entity.getCreatedAt() == null ? Instant.now().toString() : entity.getCreatedAt();
        return new StatusDTO(
                entity.getId(),
                entity.getName(),
                createdAt
        );
    }

    public static Status toEntity(StatusDTO dto) {
        if (dto == null) return null;
        String createdAt = dto.created_at() == null ? Instant.now().toString() : dto.created_at();
        return new Status(
                dto.name(),
                createdAt
        );
    }

    public static List<StatusDTO> toDTOList(List<Status> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(StatusMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Status> toEntityList(List<StatusDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(StatusMapper::toEntity)
                .collect(Collectors.toList());
    }
}
