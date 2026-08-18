package com.raniya.raniyamart.service;

import com.raniya.raniyamart.dao.CartItemDAO;
import com.raniya.raniyamart.dao.OrderDAO;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Order;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderDAO orderDAO;

    @Mock
    private CartItemDAO cartItemDAO;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(orderDAO, cartItemDAO);
    }

    @Test
    void placeOrder_EmptyCart_ThrowsException() {
        when(cartItemDAO.findByBuyerId(3L)).thenReturn(Collections.emptyList());

        assertThrows(ValidationException.class, () -> orderService.placeOrder(3L));
        verify(orderDAO, never()).createOrder(any(), any());
    }

    @Test
    void placeOrder_Success() {
        Product p = new Product();
        p.setId(10L);
        p.setSellerId(2L);
        p.setName("Headphones");
        p.setPrice(new BigDecimal("18490.00"));
        p.setStockQty(10);

        CartItem item = new CartItem();
        item.setBuyerId(3L);
        item.setProductId(10L);
        item.setQuantity(2);
        item.setProduct(p);

        when(cartItemDAO.findByBuyerId(3L)).thenReturn(List.of(item));
        when(orderDAO.createOrder(any(Order.class), eq(List.of(item)))).thenAnswer(inv -> {
            Order o = inv.getArgument(0);
            o.setId(100L);
            return o;
        });

        Order placed = orderService.placeOrder(3L);

        assertNotNull(placed);
        assertEquals(100L, placed.getId());
        assertEquals(new BigDecimal("36980.00"), placed.getTotalAmount());
        assertEquals("CONFIRMED", placed.getStatus());
    }
}
