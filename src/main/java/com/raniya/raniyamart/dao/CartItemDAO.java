package com.raniya.raniyamart.dao;

import com.raniya.raniyamart.model.CartItem;
import java.util.List;

public interface CartItemDAO {
    CartItem saveOrUpdate(CartItem item);
    List<CartItem> findByBuyerId(Long buyerId);
    boolean delete(Long id);
    boolean clearCart(Long buyerId);
}
