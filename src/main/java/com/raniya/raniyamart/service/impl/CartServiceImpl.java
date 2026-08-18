package com.raniya.raniyamart.service.impl;

import com.raniya.raniyamart.dao.CartItemDAO;
import com.raniya.raniyamart.dao.ProductDAO;
import com.raniya.raniyamart.dao.impl.CartItemDAOImpl;
import com.raniya.raniyamart.dao.impl.ProductDAOImpl;
import com.raniya.raniyamart.exception.ResourceNotFoundException;
import com.raniya.raniyamart.exception.ValidationException;
import com.raniya.raniyamart.model.CartItem;
import com.raniya.raniyamart.model.Product;
import com.raniya.raniyamart.service.CartService;

import java.math.BigDecimal;
import java.util.List;

public class CartServiceImpl implements CartService {

    private final CartItemDAO cartItemDAO;
    private final ProductDAO productDAO;

    public CartServiceImpl() {
        this.cartItemDAO = new CartItemDAOImpl();
        this.productDAO = new ProductDAOImpl();
    }

    public CartServiceImpl(CartItemDAO cartItemDAO, ProductDAO productDAO) {
        this.cartItemDAO = cartItemDAO;
        this.productDAO = productDAO;
    }

    @Override
    public CartItem addToCart(Long buyerId, Long productId, int quantity) {
        if (buyerId == null || buyerId <= 0) throw new ValidationException("Invalid buyer ID.");
        if (productId == null || productId <= 0) throw new ValidationException("Invalid product ID.");
        if (quantity <= 0) throw new ValidationException("Quantity must be at least 1.");

        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found."));

        if (product.getStockQty() < quantity) {
            throw new ValidationException("Only " + product.getStockQty() + " units available in stock.");
        }

        CartItem item = new CartItem();
        item.setBuyerId(buyerId);
        item.setProductId(productId);
        item.setQuantity(quantity);

        return cartItemDAO.saveOrUpdate(item);
    }

    @Override
    public List<CartItem> getCartItems(Long buyerId) {
        if (buyerId == null || buyerId <= 0) throw new ValidationException("Invalid buyer ID.");
        return cartItemDAO.findByBuyerId(buyerId);
    }

    @Override
    public boolean updateQuantity(Long cartItemId, int quantity) {
        if (cartItemId == null || cartItemId <= 0) throw new ValidationException("Invalid cart item ID.");
        if (quantity <= 0) {
            return cartItemDAO.delete(cartItemId);
        }
        CartItem item = new CartItem();
        item.setId(cartItemId);
        item.setQuantity(quantity);
        return cartItemDAO.saveOrUpdate(item) != null;
    }

    @Override
    public boolean removeFromCart(Long cartItemId) {
        if (cartItemId == null || cartItemId <= 0) throw new ValidationException("Invalid cart item ID.");
        return cartItemDAO.delete(cartItemId);
    }

    @Override
    public BigDecimal getCartTotal(Long buyerId) {
        List<CartItem> items = getCartItems(buyerId);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : items) {
            if (item.getProduct() != null && item.getProduct().getPrice() != null) {
                BigDecimal itemTotal = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(itemTotal);
            }
        }
        return total;
    }
}
