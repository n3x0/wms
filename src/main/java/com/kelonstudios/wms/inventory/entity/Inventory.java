package com.kelonstudios.wms.inventory.entity;

import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.product.entity.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "location_id"}))
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
    @SequenceGenerator(name = "inventory_seq", sequenceName = "inventory_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(nullable = false)
    private Integer quantity = 0;

    // Constructor de conveniencia
    public Inventory(Product product, Location location, Integer quantity) {
        this.product = product;
        this.location = location;
        this.quantity = quantity;
    }
}