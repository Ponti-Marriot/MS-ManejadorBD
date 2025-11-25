package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.RoomDTO;
import com.pontimarriot.manejadorDB.model.Room;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RoomMapper {

    public static RoomDTO toDTO(Room entity) {
        if (entity == null) return null;

        LocalDateTime createdAt = entity.getCreatedAt() == null ? LocalDateTime.now() : entity.getCreatedAt();
        LocalDateTime updatedAt = entity.getUpdatedAt() == null ? LocalDateTime.now() : entity.getUpdatedAt();

        return new RoomDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getRoomType(),
                createdAt,
                updatedAt
        );
    }

    public static Room toEntity(RoomDTO dto) {
        if (dto == null) return null;

        Room entity = new Room(
                dto.title(),
                dto.description(),
                dto.room_type()
        );

        entity.setCreatedAt(dto.created_at() == null ? LocalDateTime.now() : dto.created_at());
        entity.setUpdatedAt(dto.updated_at() == null ? LocalDateTime.now() : dto.updated_at());

        return entity;
    }

    public static List<RoomDTO> toDTOList(List<Room> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(RoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<Room> toEntityList(List<RoomDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(RoomMapper::toEntity)
                .collect(Collectors.toList());
    }
}
