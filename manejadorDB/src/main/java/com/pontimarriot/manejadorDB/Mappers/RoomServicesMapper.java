package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.RoomServicesDTO;
import com.pontimarriot.manejadorDB.model.RoomServices;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RoomServicesMapper {

    public static RoomServicesDTO toDTO(RoomServices entity) {
        if (entity == null) return null;
        String createdAt = entity.getCreatedAt() == null ? Instant.now().toString() : entity.getCreatedAt();
        return new RoomServicesDTO(
                entity.getId(),
                entity.getServiceId(),
                entity.getRoomId(),
                createdAt
        );
    }

    public static RoomServices toEntity(RoomServicesDTO dto) {
        if (dto == null) return null;
        String createdAt = dto.created_at() == null ? Instant.now().toString() : dto.created_at();
        return new RoomServices(
                dto.service_id(),
                dto.room_id(),
                createdAt
        );
    }

    public static List<RoomServicesDTO> toDTOList(List<RoomServices> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(RoomServicesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<RoomServices> toEntityList(List<RoomServicesDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(RoomServicesMapper::toEntity)
                .collect(Collectors.toList());
    }
}
