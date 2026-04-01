package com.farm.farm_marketplace.controller;

import com.farm.farm_marketplace.dto.OrderResponse;
import com.farm.farm_marketplace.model.Order;
import com.farm.farm_marketplace.model.OrderStatus;
import com.farm.farm_marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Place order
    @PostMapping
    public Order placeOrder(@RequestBody Order order) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return orderService.placeOrder(order, email);
    }

    @GetMapping("/buyer")
    public List<OrderResponse> getOrdersByBuyer() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return orderService.getOrdersDTOByBuyerEmail(email);
    }

    @PutMapping("/{orderId}/status")
    public Order updateStatus(@PathVariable String orderId,
                              @RequestParam OrderStatus status) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return orderService.updateOrderStatus(orderId, status, email);
    }

    @GetMapping("/farmer")
    public List<OrderResponse> getOrdersForFarmer() {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return orderService.getOrdersForFarmer(email);
    }

}