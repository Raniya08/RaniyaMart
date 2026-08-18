package com.raniya.raniyamart.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long sellerId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private Timestamp createdAt;

    private Product product;

    public OrderItem() {}

    public OrderItem(Long id, Long orderId, Long productId, Long sellerId, Integer quantity, BigDecimal pricePerUnit, Timestamp createdAt) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.sellerId = sellerId;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPricePerUnit() { return pricePerUnit; }
    public void setPricePerUnit(BigDecimal pricePerUnit) { this.pricePerUnit = pricePerUnit; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
