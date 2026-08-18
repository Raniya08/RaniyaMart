package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductDAO productDAO;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productDAO);
    }

    @Test
    void createProduct_Success() {
        Product p = new Product();
        p.setSellerId(2L);
        p.setName("Wireless Earbuds");
        p.setDescription("Noise canceling earbuds");
        p.setPrice(new BigDecimal("2499.00"));
        p.setStockQty(20);
        p.setCategory("Electronics");

        when(productDAO.create(any(Product.class))).thenReturn(p);

        Product created = productService.createProduct(p);
        assertNotNull(created);
        assertEquals("Wireless Earbuds", created.getName());
        verify(productDAO).create(p);
    }

    @Test
    void createProduct_InvalidPrice_ThrowsException() {
        Product p = new Product();
        p.setSellerId(2L);
        p.setName("Invalid Price Item");
        p.setPrice(new BigDecimal("-10.00"));
        p.setStockQty(5);
        p.setCategory("Electronics");

        assertThrows(ValidationException.class, () -> productService.createProduct(p));
        verify(productDAO, never()).create(any());
    }

    @Test
    void getCatalog_CallsDAOWithFilters() {
        when(productDAO.findAll("Electronics", "wireless", "price_asc")).thenReturn(List.of(new Product()));

        List<Product> result = productService.getCatalog("Electronics", "wireless", "price_asc");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productDAO).findAll("Electronics", "wireless", "price_asc");
    }
}
