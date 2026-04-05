package com.kelonstudios.wms.product;

import com.kelonstudios.wms.product.entity.Product;
import com.kelonstudios.wms.product.repository.ProductRepository;
import com.kelonstudios.wms.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldReturnProducts() {
        when(repository.findAll()).thenReturn(List.of(new Product()));

        List<Product> products = service.findAll();

        assertFalse(products.isEmpty());
        verify(repository).findAll();
    }
}