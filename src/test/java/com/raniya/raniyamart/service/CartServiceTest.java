package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dao.CartItemDAO;
import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartItemDAO cartItemDAO;

    @Mock
    private ProductDAO productDAO;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartServiceImpl(cartItemDAO, productDAO);
    }

    @Test
    void addToCart_Success() {
        Product p = new Product();
        p.setId(10L);
        p.setName("Laptop");
        p.setStockQty(5);
        p.setPrice(new BigDecimal("89999.00"));

        when(productDAO.findById(10L)).thenReturn(Optional.of(p));
        when(cartItemDAO.saveOrUpdate(any(CartItem.class))).thenAnswer(inv -> inv.getArgument(0));

        CartItem item = cartService.addToCart(3L, 10L, 2);

        assertNotNull(item);
        assertEquals(3L, item.getBuyerId());
        assertEquals(10L, item.getProductId());
        assertEquals(2, item.getQuantity());
    }

    @Test
    void addToCart_InsufficientStock_ThrowsException() {
        Product p = new Product();
        p.setId(10L);
        p.setName("Laptop");
        p.setStockQty(1);

        when(productDAO.findById(10L)).thenReturn(Optional.of(p));

        assertThrows(ValidationException.class, () -> cartService.addToCart(3L, 10L, 5));
        verify(cartItemDAO, never()).saveOrUpdate(any());
    }
}
