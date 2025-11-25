package com.pontimarriot.manejadorDB.model;
import lombok.Data;

import java.util.UUID;


@Data
public class CancelacionRequest {
    UUID reservation_id;
    UUID transaction_id;
    String document_id;
    String origin;
    String causes;
    String observations;
}
