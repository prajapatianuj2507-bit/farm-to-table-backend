package com.farm.farm_marketplace.service;

import com.farm.farm_marketplace.dto.OrderResponse;
import com.farm.farm_marketplace.model.*;
import com.farm.farm_marketplace.repository.CropRepository;
import com.farm.farm_marketplace.repository.OrderRepository;
import com.farm.farm_marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CropRepository cropRepository;

    public List<OrderResponse> getOrdersDTOByBuyerEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByBuyerId(user.getId());

        List<OrderResponse> result = new ArrayList<>();

        for (Order o : orders) {

            Crop crop = cropRepository.findById(o.getCropId())
                    .orElse(null);

            OrderResponse res = new OrderResponse();

            res.setId(o.getId());
            res.setQuantity(o.getQuantity());
            res.setTotalPrice(o.getTotalPrice());
            res.setStatus(o.getStatus().name());

            if (crop != null) {
                res.setCropName(crop.getCropName());
            }

            result.add(res);
        }

        return result;
    }

    @Autowired
    private UserRepository userRepository;


    // Place order
    public Order placeOrder(Order order, String email) {

        // 🔥 Get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Only BUYER can place order
        if (user.getRole() != Role.BUYER) {
            throw new RuntimeException("Only buyers can place orders");
        }

        // 🔍 Get crop details (your existing logic)
        Optional<Crop> cropOptional = cropRepository.findById(order.getCropId());

        if (cropOptional.isPresent()) {
            Crop crop = cropOptional.get();

            // 💰 Calculate total price (your logic)
            double total = crop.getPricePerKg() * order.getQuantity();
            order.setTotalPrice(total);

            // 🔥 AUTO SET buyerId
            order.setBuyerId(user.getId());

            // 📌 Set status
            order.setStatus(OrderStatus.PENDING);

            return orderRepository.save(order);

        } else {
            throw new org.springframework.web.server.ResponseStatusException(
                    org.springframework.http.HttpStatus.NOT_FOUND,
                    "Crop not found with ID: " + order.getCropId()
            );
        }
    }


    public Order updateOrderStatus(String orderId, OrderStatus status, String email) {

        // get logged-in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check role
        if (user.getRole() != Role.FARMER) {
            throw new RuntimeException("Only farmers can update order status");
        }

        // get order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // OPTIONAL: verify farmer owns crop (pro level)
        // we skip for now or can add later

        // update status
        order.setStatus(status);

        return orderRepository.save(order);
    }


    public List<Order> getOrdersByBuyerEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepository.findByBuyerId(user.getId());
    }



    public List<OrderResponse> getOrdersForFarmer(String email) {

        User farmer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get crops of this farmer
        List<Crop> crops = cropRepository.findByFarmerId(farmer.getId());

        List<OrderResponse> result = new ArrayList<>();

        for (Crop crop : crops) {

            List<Order> orders = orderRepository.findAll();

            for (Order o : orders) {

                if (o.getCropId().equals(crop.getId())) {

                    OrderResponse res = new OrderResponse();

                    res.setId(o.getId());
                    res.setQuantity(o.getQuantity());
                    res.setTotalPrice(o.getTotalPrice());
                    res.setStatus(o.getStatus().name());

                    // 🌾 Crop Name
                    res.setCropName(crop.getCropName());

                    // 👤 Buyer Name
                    User buyer = userRepository.findById(o.getBuyerId()).orElse(null);
                    if (buyer != null) {
                        res.setBuyerName(buyer.getName());
                    }

                    result.add(res);
                }
            }
        }

        return result;
    }
}