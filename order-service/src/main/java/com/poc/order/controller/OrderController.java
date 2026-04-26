package com.poc.order.controller;

import com.poc.order.dto.OrderRequest;
import com.poc.order.dto.OrderResponse;
import com.poc.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/createOrder")
    public OrderResponse createOrder(@RequestBody OrderRequest request) {
        log.info("Start::createOrder() inside OrderController for customerId: {}", request.getCustomerId());
        return orderService.createOrder(request);
    }

    @GetMapping("/findOrderById/{orderId}")
    public OrderResponse findOrderById(@PathVariable String orderId) {
        log.info("Start::findOrderById() inside OrderController with id: {}", orderId);
        return orderService.findOrderById(orderId);
    }

    @GetMapping("/findAllOrders")
    public List<OrderResponse> findAllOrders() {
        log.info("Start::findAllOrders() inside OrderController");
        return orderService.findAllOrders();
    }

    @GetMapping("/findByCustomer/{customerId}")
    public List<OrderResponse> findOrdersByCustomerId(@PathVariable String customerId) {
        log.info("Start::findOrdersByCustomerId() inside OrderController with customerId: {}", customerId);
        return orderService.findOrdersByCustomerId(customerId);
    }

    @GetMapping("/findByStatus/{status}")
    public List<OrderResponse> findOrdersByStatus(@PathVariable String status) {
        log.info("Start::findOrdersByStatus() inside OrderController with status: {}", status);
        return orderService.findOrdersByStatus(status);
    }

    @PutMapping("/updateStatus/{orderId}")
    public OrderResponse updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        log.info("Start::updateOrderStatus() inside OrderController with id: {}, status: {}", orderId, status);
        return orderService.updateOrderStatus(orderId, status);
    }

    @DeleteMapping("/deleteOrderById/{orderId}")
    public String deleteOrderById(@PathVariable String orderId) {
        log.info("Start::deleteOrderById() inside OrderController with id: {}", orderId);
        return orderService.deleteOrderById(orderId);
    }

    @DeleteMapping("/deleteAllOrders")
    public String deleteAllOrders() {
        log.info("Start::deleteAllOrders() inside OrderController");
        return orderService.deleteAllOrders();
    }
}
