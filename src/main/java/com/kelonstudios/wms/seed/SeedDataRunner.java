package com.kelonstudios.wms.seed;

import com.kelonstudios.wms.inventory.entity.Inventory;
import com.kelonstudios.wms.inventory.repository.InventoryRepository;
import com.kelonstudios.wms.inventory.service.InventoryService;
import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.entity.LocationStatus;
import com.kelonstudios.wms.location.entity.LocationType;
import com.kelonstudios.wms.location.repository.LocationRepository;
import com.kelonstudios.wms.location.service.LocationService;
import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.product.repository.ProductRepository;
import com.kelonstudios.wms.product.service.ProductService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class SeedDataRunner implements CommandLineRunner {

    private final LocationService locationService;
    private final ProductService productService;
    private final InventoryService inventoryService;

    @PersistenceContext
    private EntityManager em;

    public SeedDataRunner(ProductService productService,
                          LocationService locationService,
                          InventoryService inventoryService) {
        this.locationService = locationService;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Purge de tablas
        inventoryService.deleteAll();
        locationService.deleteAll();
        productService.deleteAll();

        // Reiniciar secuencias en Oracle
        em.createNativeQuery("DROP SEQUENCE location_seq").executeUpdate();
        em.createNativeQuery("CREATE SEQUENCE location_seq START WITH 1 INCREMENT BY 1").executeUpdate();

        em.createNativeQuery("DROP SEQUENCE product_seq").executeUpdate();
        em.createNativeQuery("CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1").executeUpdate();

        // Crear Locations
        List<Location> locations = List.of(
                new Location("A-01-01", LocationType.EUR1, LocationStatus.AVAILABLE),
                new Location("A-01-02", LocationType.EUR2, LocationStatus.AVAILABLE),
                new Location("B-01-01", LocationType.ISO1, LocationStatus.AVAILABLE),
                new Location("B-01-02", LocationType.ISO2, LocationStatus.AVAILABLE)
        );

        locations.forEach(locationService::create);

        // Crear Products
        List<Product> products = List.of(
                new Product("Producto A", Map.of(
                        LocationType.EUR1, 100,
                        LocationType.EUR2, 50
                )),
                new Product("Producto B", Map.of(
                        LocationType.ISO1, 180,
                        LocationType.ISO2, 120
                ))
        );
        products.forEach(productService::create);

        // Crear stock
        record InventorySeed(Long productId, Long locationId, int qty) {}

        List<InventorySeed> seeds = List.of(
                new InventorySeed(products.get(0).getId(), locations.get(0).getId(), 50),
                new InventorySeed(products.get(0).getId(), locations.get(1).getId(), 30),
                new InventorySeed(products.get(1).getId(), locations.get(2).getId(), 70)
        );

        seeds.forEach(s ->
                inventoryService.addInventory(s.productId(), s.locationId(), s.qty())
        );

        System.out.println("Inserciones semilla correctas");
    }
}
