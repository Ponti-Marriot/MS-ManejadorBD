// java
package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "locations")
public class Location {

    @Id
    private UUID id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "state")
    private String state;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "subdiv_code")
    private String subdivCode;

    public Location() {
    }

    public Location(UUID id, String countryCode, String state, String cityCode, String cityName,
                    String timezone, String subdivCode) {
        this.id = id;
        this.countryCode = countryCode;
        this.state = state;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.timezone = timezone;
        this.subdivCode = subdivCode;
    }

}
