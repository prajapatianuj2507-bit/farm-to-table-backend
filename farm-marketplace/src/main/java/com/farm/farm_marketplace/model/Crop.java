package com.farm.farm_marketplace.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "crops")
public class Crop {

    @Id
    private String id;

    private String cropName;
    private double pricePerKg;
    private String cropType;   // Vegetable, Fruit, Grain
    private String location;
    private String farmerId;


}