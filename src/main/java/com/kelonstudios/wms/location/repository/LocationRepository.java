package com.kelonstudios.wms.location.repository;

import com.kelonstudios.wms.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}