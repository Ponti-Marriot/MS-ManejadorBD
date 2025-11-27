package com.pontimarriot.manejadorDB.Mappers;

import com.pontimarriot.manejadorDB.dtos.AvailabilityDatesDTO;
import com.pontimarriot.manejadorDB.model.AvailabilityDates;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class AvailabilityDatesMapper {

    public static AvailabilityDatesDTO toDTO(AvailabilityDates entity) {
        if (entity == null) return null;
        return new AvailabilityDatesDTO(
                entity.getId(),
                entity.getHotelpropertiesroomsId(),
                entity.getStartDate(),
                entity.getFinishDate(),
                entity.getCreatedAt(),
                entity.getReservationId()
        );
    }

    public static AvailabilityDates toEntity(AvailabilityDatesDTO dto) {
        if (dto == null) return null;
        return new AvailabilityDates(
                dto.hotelpropertiesrooms_id(),
                dto.start_date(),
                dto.finish_date(),
                dto.created_at(),
                dto.reservation_id()
        );
    }

    public static List<AvailabilityDatesDTO> toDTOList(List<AvailabilityDates> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(AvailabilityDatesMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static List<AvailabilityDates> toEntityList(List<AvailabilityDatesDTO> dtos) {
        if (dtos == null) return Collections.emptyList();
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(AvailabilityDatesMapper::toEntity)
                .collect(Collectors.toList());
    }
}
