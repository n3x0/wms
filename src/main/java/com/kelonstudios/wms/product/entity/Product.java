package com.kelonstudios.wms.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.kelonstudios.wms.location.entity.LocationType;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    // Capacidad máxima por tipo de ubicación
    @ElementCollection
    @CollectionTable(name = "product_location_capacity", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "max_capacity")
    private Map<LocationType, Integer> maxCapacityPerLocationType = new HashMap<>();

    // Constructor con argumentos
    public Product(String name) {
        this.name = name;
    }

    // Opcional: constructor con nombre y mapa de capacidades
    public Product(String name, Map<LocationType, Integer> capacities) {
        this.name = name;
        this.maxCapacityPerLocationType = capacities;
    }
}