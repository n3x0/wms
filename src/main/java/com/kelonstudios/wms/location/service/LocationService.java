package com.kelonstudios.wms.location.service;

import com.kelonstudios.wms.location.entity.Location;
import com.kelonstudios.wms.location.entity.LocationStatus;
import com.kelonstudios.wms.location.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    /**
     * Actualiza el estado de la ubicación a OCCUPIED si tiene inventario
     */
    public void updateStatusAfterInventory(Location location, int totalQuantity) {
        if (location.getStatus() == LocationStatus.AVAILABLE && totalQuantity > 0) {
            location.setStatus(LocationStatus.OCCUPIED);
            repository.save(location);
        }
        if (location.getStatus() == LocationStatus.OCCUPIED && totalQuantity == 0) {
            location.setStatus(LocationStatus.AVAILABLE);
            repository.save(location);
        }
        if (location.getStatus() == LocationStatus.RESERVED && totalQuantity == 0) {
            location.setStatus(LocationStatus.AVAILABLE);
            repository.save(location);
        }
    }

    public Location create(Location location) {
        return repository.save(location);
    }


    public List<Location> findAll() {
        return repository.findAll();
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}