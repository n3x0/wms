package com.kelonstudios.wms.inventory.service;

import com.kelonstudios.wms.inventory.entity.Inventory;
import com.kelonstudios.wms.inventory.repository.InventoryRepository;
import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.repository.LocationRepository;
import com.kelonstudios.wms.location.service.LocationService;
import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository repository;
    private final ProductRepository productRepository;
    private final LocationRepository locationRepository;
    private final InventoryValidator validator;
    private final LocationService locationService;

    public InventoryService(
            InventoryRepository repository,
            ProductRepository productRepository,
            LocationRepository locationRepository,
            InventoryValidator validator,
            LocationService locationService) {

        this.repository = repository;
        this.productRepository = productRepository;
        this.locationRepository = locationRepository;
        this.validator = validator;
        this.locationService = locationService;
    }

    @Transactional
    public Inventory addInventory(Long productId, Long locationId, int quantity) {
        // Cargar entidades
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + productId));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada: " + locationId));

        // Buscar inventario existente
        Inventory existingInventory = repository.findByProductAndLocation(product, location).orElse(null);

        // Validaciones
        validator.validateAddInventory(product, location, quantity, existingInventory);

        // Crear o actualizar inventario
        Inventory inv = existingInventory != null ? existingInventory : new Inventory(product, location, 0);
        inv.setQuantity(inv.getQuantity() + quantity);
        Inventory savedInventory = repository.save(inv);

        // Actualizar estado de ubicación
        locationService.updateStatusAfterInventory(location, savedInventory.getQuantity());

        return savedInventory;
    }

    public List<Inventory> getAll() {
        return repository.findAll();
    }

    // Consultar por ubicación
    public List<Inventory> getByLocation(Location location) {
        return repository.findAllByLocation(location);
    }

    // Consultar por producto
    public List<Inventory> getByProduct(Product product) {
        return repository.findAllByProduct(product);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @Transactional
    public Inventory removeInventory(Long productId, Long locationId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Ubicación no encontrada"));

        Inventory inventory = repository.findByProductAndLocation(product, location)
                .orElseThrow(() -> new IllegalArgumentException("No hay inventario del artículo " + productId + " en esa ubicación"));

        // VALIDACIONES
        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad no válida");
        }

        if (inventory.getQuantity() < quantity) {
            throw new IllegalArgumentException("Stock insuficiente: hay " + inventory.getQuantity() + " y se quieren sacar " + quantity + " unidades");
        }

        // 🔹 Actualizar cantidad
        inventory.setQuantity(inventory.getQuantity() - quantity);
        Inventory saved = repository.save(inventory);

        // 🔹 Actualizar estado de la ubicación
        locationService.updateStatusAfterInventory(location, saved.getQuantity());

        return saved;
    }

    @Transactional
    public void moveInventory(Long productId, Long fromLocationId, Long toLocationId, int quantity) {

        if (fromLocationId.equals(toLocationId)) {
            throw new IllegalArgumentException("Origen y destino no pueden ser iguales");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // 🔹 1. Quitar del origen
        removeInventory(productId, fromLocationId, quantity);

        try {
            // 🔹 2. Añadir al destino
            addInventory(productId, toLocationId, quantity);

        } catch (Exception e) {
            // 🔥 rollback manual semántico (aunque @Transactional ya ayuda)
            throw new RuntimeException("Error moviendo inventario, operación revertida", e);
        }
    }
}