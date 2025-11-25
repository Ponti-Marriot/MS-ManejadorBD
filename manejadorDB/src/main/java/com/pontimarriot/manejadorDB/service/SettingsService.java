package com.pontimarriot.manejadorDB.service;

import com.pontimarriot.manejadorDB.model.Setting;
import com.pontimarriot.manejadorDB.repository.*;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SettingsService {

    private final HotelPropertyRepository hotelPropertyRepository;
    private final RoomRepository roomRepository;
    private final ReservaRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final HotelPropertyRoomRepository hotelPropertyRoomRepository;
    private final LocationJpaRepository locationRepository;

    public SettingsService(HotelPropertyRepository hotelPropertyRepository,
                           RoomRepository roomRepository,
                           ReservaRepository reservationRepository,
                           PaymentRepository paymentRepository,
                           HotelPropertyRoomRepository hotelPropertyRoomRepository,
                           LocationJpaRepository locationRepository) {
        this.hotelPropertyRepository = hotelPropertyRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.hotelPropertyRoomRepository = hotelPropertyRoomRepository;
        this.locationRepository = locationRepository;
    }

    // ---- Public API used by controller ----

    public List<Setting> getAllSettings() {
        return buildCurrentSettings();
    }

    public Setting getSettingById(UUID id) {
        return buildCurrentSettings().stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Setting getSettingByKey(String key) {
        return buildCurrentSettings().stream()
                .filter(s -> s.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }

    // ---- Core logic: build settings from existing tables ----

    private List<Setting> buildCurrentSettings() {
        List<Setting> settings = new ArrayList<>();

        long totalHotels = hotelPropertyRepository.count();
        long totalRooms = roomRepository.count();
        long totalReservations = reservationRepository.count();
        long totalPayments = paymentRepository.count();
        long totalLocations = locationRepository.count();
        long totalHotelRoomsRelations = hotelPropertyRoomRepository.count();

        // Average rooms per hotel (via hotelpropertiesrooms)
        BigDecimal avgRoomsPerHotel = BigDecimal.ZERO;
        if (totalHotels > 0) {
            avgRoomsPerHotel = BigDecimal
                    .valueOf(totalHotelRoomsRelations)
                    .divide(BigDecimal.valueOf(totalHotels), 2, BigDecimal.ROUND_HALF_UP);
        }

        // Setting: total hotels
        settings.add(buildSetting(
                "TOTAL_HOTELS",
                String.valueOf(totalHotels),
                "Total number of hotels registered in the system"
        ));

        // Setting: total rooms
        settings.add(buildSetting(
                "TOTAL_ROOMS",
                String.valueOf(totalRooms),
                "Total number of rooms registered in the system"
        ));

        // Setting: total reservations
        settings.add(buildSetting(
                "TOTAL_RESERVATIONS",
                String.valueOf(totalReservations),
                "Total number of reservations in the system"
        ));

        // Setting: total payments
        settings.add(buildSetting(
                "TOTAL_PAYMENTS",
                String.valueOf(totalPayments),
                "Total number of payments in the system"
        ));

        // Setting: total locations (cities/regions)
        settings.add(buildSetting(
                "TOTAL_LOCATIONS",
                String.valueOf(totalLocations),
                "Total number of locations (city/region) configured"
        ));

        // Setting: average number of rooms per hotel
        settings.add(buildSetting(
                "AVG_ROOMS_PER_HOTEL",
                avgRoomsPerHotel.toPlainString(),
                "Average number of rooms linked to each hotel"
        ));

        // Aquí puedes agregar más settings relacionados
        // con regiones, países, etc, usando locations + iso.*

        return settings;
    }

    private Setting buildSetting(String key, String value, String description) {
        Setting s = new Setting();
        s.setKey(key);
        s.setValue(value);
        s.setDescription(description);

        // Deterministic UUID based on the key
        UUID id = UUID.nameUUIDFromBytes(key.getBytes(StandardCharsets.UTF_8));
        s.setId(id);

        return s;
    }
}
