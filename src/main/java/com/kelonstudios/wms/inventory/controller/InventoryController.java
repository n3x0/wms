package com.kelonstudios.wms.inventory.controller;

import com.kelonstudios.wms.inventory.entity.Inventory;
import com.kelonstudios.wms.inventory.service.InventoryService;
import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.entity.LocationStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // GET inventario completo
    @GetMapping
    public List<InventoryDTO> getAllInventory() {
        return inventoryService.getAll().stream()
                .map(InventoryDTO::fromInventory)
                .collect(Collectors.toList());
    }

    // POST añadir inventario
    @PostMapping("/add")
    public InventoryDTO addInventory(
            @RequestParam Long productId,
            @RequestParam Long locationId,
            @RequestParam int quantity) {

        Inventory inv = inventoryService.addInventory(productId, locationId, quantity);
        return InventoryDTO.fromInventory(inv);
    }

    // DTO para exponer inventario con detalles
    public static class InventoryDTO {
        public String productName;
        public String locationCode;
        public String locationStatus;
        public String locationType;
        public int quantity;
        public int maxCapacity;
        public int remainingCapacity;

        public static InventoryDTO fromInventory(Inventory inv) {
            InventoryDTO dto = new InventoryDTO();
            Product p = inv.getProduct();
            Location l = inv.getLocation();

            dto.productName = p.getName();
            dto.locationCode = l.getCode();
            dto.locationStatus = l.getStatus().name();
            dto.locationType = l.getType().name();
            dto.quantity = inv.getQuantity();
            dto.maxCapacity = p.getMaxCapacityPerLocationType()
                    .getOrDefault(l.getType(), Integer.MAX_VALUE);
            dto.remainingCapacity = dto.maxCapacity - dto.quantity;
            return dto;
        }
    }

    @PostMapping("/move")
    public void moveInventory(
            @RequestParam Long productId,
            @RequestParam Long fromLocationId,
            @RequestParam Long toLocationId,
            @RequestParam int quantity) {

        inventoryService.moveInventory(productId, fromLocationId, toLocationId, quantity);
    }
}