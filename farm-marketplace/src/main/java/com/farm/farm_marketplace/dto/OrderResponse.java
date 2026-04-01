package com.farm.farm_marketplace.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private String id; // Use Long if your DB ID is numeric
    private String cropName;
    private String buyerName;
    private int quantity;
    private double totalPrice;
    private String status;
}