package com.raniya.raniyamart.service;

import com.raniya.raniyamart.model.CartItem;
import java.math.BigDecimal;
import java.util.List;

public interface CartService {
    CartItem addToCart(Long buyerId, Long productId, int quantity);
    List<CartItem> getCartItems(Long buyerId);
    boolean updateQuantity(Long cartItemId, int quantity);
    boolean removeFromCart(Long cartItemId);
    BigDecimal getCartTotal(Long buyerId);
}
