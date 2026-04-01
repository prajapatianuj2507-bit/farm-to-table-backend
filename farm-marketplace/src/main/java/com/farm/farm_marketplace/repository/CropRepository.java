package com.farm.farm_marketplace.repository;

import com.farm.farm_marketplace.model.Crop;
import com.farm.farm_marketplace.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CropRepository extends MongoRepository<Crop, String> {
    List<Crop> findByCropType(String cropType);
    List<Crop> findByLocation(String location);
    List<Crop> findByFarmerId(String farmerId);




}