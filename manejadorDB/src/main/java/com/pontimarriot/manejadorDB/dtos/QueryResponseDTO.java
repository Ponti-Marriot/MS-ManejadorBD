package com.pontimarriot.manejadorDB.dtos;

import com.pontimarriot.manejadorDB.model.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QueryResponseDTO {
    private UUID consulta_id;
    private List<PropertyAndRoomsResponseDTO> hoteles;

    public QueryResponseDTO(){
        consulta_id = UUID.randomUUID();
    }
}
