package com.kelonstudios.wms.location.controller;

import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.service.LocationService;
import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @PostMapping
    public Location create(@RequestBody Location location) {
        return service.create(location);
    }

    @GetMapping
    public List<Location> getAll() {
        return service.findAll();
    }

}

