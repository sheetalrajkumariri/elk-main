package com.poc.order.service;

import com.poc.order.dto.OrderRequest;
import com.poc.order.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse findOrderById(String orderId);

    List<OrderResponse> findAllOrders();

    List<OrderResponse> findOrdersByCustomerId(String customerId);

    List<OrderResponse> findOrdersByStatus(String status);

    OrderResponse updateOrderStatus(String orderId, String status);

    String deleteOrderById(String orderId);

    String deleteAllOrders();
}
