package com.kelonstudios.wms.product.repository;

import com.kelonstudios.wms.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}