package com.kelonstudios.wms.location.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "location_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private LocationType type;

    @Enumerated(EnumType.STRING)
    private LocationStatus status;

    // Constructor con argumentos
    public Location(String code, LocationType type, LocationStatus status) {
        this.code = code;
        this.type = type;
        this.status = status;
    }
}