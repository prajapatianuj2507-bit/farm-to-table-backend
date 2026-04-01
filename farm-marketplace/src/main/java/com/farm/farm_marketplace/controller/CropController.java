package com.farm.farm_marketplace.controller;

import com.farm.farm_marketplace.model.Crop;
import com.farm.farm_marketplace.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {

    @Autowired
    private CropService cropService;

    // Add crop
    @PostMapping
    public Crop addCrop(@RequestBody Crop crop) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return cropService.addCrop(crop, email);
    }

    // Get all crops
    @GetMapping
    public List<Crop> getAllCrops() {
        return cropService.getAllCrops();
    }

    // Filter by type
    @GetMapping("/type/{type}")
    public List<Crop> getByType(@PathVariable String type) {
        return cropService.getByType(type);
    }

    // Filter by location
    @GetMapping("/location/{location}")
    public List<Crop> getByLocation(@PathVariable String location) {
        return cropService.getByLocation(location);
    }

    @GetMapping("/farmer")
    public List<Crop> getCropsByFarmer() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return cropService.getCropsByFarmerEmail(email);
    }
    @DeleteMapping("/{cropId}")
    public void deleteCrop(@PathVariable String cropId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        cropService.deleteCrop(cropId, email);
    }
}