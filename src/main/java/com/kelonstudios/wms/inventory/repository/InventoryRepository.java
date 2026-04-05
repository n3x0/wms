package com.kelonstudios.wms.inventory.repository;

import com.kelonstudios.wms.inventory.entity.Inventory;
import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductAndLocation(Product product, Location location);

    List<Inventory> findAllByLocation(Location location);

    List<Inventory> findAllByProduct(Product product);

    boolean existsByLocationAndProductNot(Location location, Product product);
}