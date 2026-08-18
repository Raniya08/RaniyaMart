package com.raniya.raniyamart.model;

import java.sql.Timestamp;

public class CartItem {
    private Long id;
    private Long buyerId;
    private Long productId;
    private Integer quantity;
    private Timestamp createdAt;

    // Additional transient view field
    private Product product;

    public CartItem() {}

    public CartItem(Long id, Long buyerId, Long productId, Integer quantity, Timestamp createdAt) {
        this.id = id;
        this.buyerId = buyerId;
        this.productId = productId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBuyerId() { return buyerId; }
    public void setBuyerId(Long buyerId) { this.buyerId = buyerId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
