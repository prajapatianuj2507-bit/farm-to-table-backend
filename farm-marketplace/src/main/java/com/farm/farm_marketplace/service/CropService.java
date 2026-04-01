package com.farm.farm_marketplace.service;

import com.farm.farm_marketplace.model.Crop;
import com.farm.farm_marketplace.model.User;
import com.farm.farm_marketplace.repository.CropRepository;
import com.farm.farm_marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {

    @Autowired
    private CropRepository cropRepository;


    @Autowired
    private UserRepository userRepository;

    // Add crop
    public Crop addCrop(Crop crop, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 AUTO SET farmerId
        crop.setFarmerId(user.getId());

        return cropRepository.save(crop);
    }

    // Get all crops
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    // Filter by type
    public List<Crop> getByType(String type) {
        return cropRepository.findByCropType(type);
    }

    // Filter by location
    public List<Crop> getByLocation(String location) {
        return cropRepository.findByLocation(location);
    }

    public List<Crop> getCropsByFarmer(String farmerId) {
        return cropRepository.findByFarmerId(farmerId);
    }

    public List<Crop> getCropsByFarmerEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cropRepository.findByFarmerId(user.getId());
    }
    public void deleteCrop(String cropId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Crop crop = cropRepository.findById(cropId)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        // 🔥 SECURITY CHECK
        if (!crop.getFarmerId().equals(user.getId())) {
            throw new RuntimeException("You can only delete your own crops");
        }

        cropRepository.deleteById(cropId);
    }
}