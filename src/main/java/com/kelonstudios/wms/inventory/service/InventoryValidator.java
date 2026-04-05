package com.kelonstudios.wms.inventory.service;

import com.kelonstudios.wms.inventory.entity.Inventory;
import com.kelonstudios.wms.inventory.repository.InventoryRepository;
import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.entity.LocationStatus;
import com.kelonstudios.wms.product.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryValidator {

    private final InventoryRepository repository;

    public InventoryValidator(InventoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Valida que se pueda añadir cierta cantidad de producto en una ubicación
     */
    public void validateAddInventory(Product product, Location location, int quantity, Inventory existingInventory) {
        // Ubicación bloqueada o reservada
        if (location.getStatus() == LocationStatus.BLOCKED || location.getStatus() == LocationStatus.RESERVED) {
            throw new IllegalArgumentException(
                    "No se puede añadir inventario. Ubicación " + location.getCode() +
                            " está " + location.getStatus()
            );
        }

        // Validar capacidad del producto para este tipo de ubicación
        int maxCapacity = product.getMaxCapacityPerLocationType()
                .getOrDefault(location.getType(), Integer.MAX_VALUE);

        int currentQty = existingInventory != null ? existingInventory.getQuantity() : 0;

        if (currentQty + quantity > maxCapacity) {
            throw new IllegalArgumentException(
                    "No se puede añadir " + quantity + " unidades. Capacidad máxima para " +
                            location.getType() + " es " + maxCapacity
            );
        }

        //Validar que sean del mismo artículo
        if (repository.existsByLocationAndProductNot(location, product)){
            throw new IllegalStateException("La ubicación ya contiene otro producto");
        }
    }
}