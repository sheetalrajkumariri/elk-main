package com.poc.order.service;

import com.poc.order.client.ProductServiceClient;
import com.poc.order.dto.OrderItemResponse;
import com.poc.order.dto.OrderRequest;
import com.poc.order.dto.OrderResponse;
import com.poc.order.dto.ProductResponse;
import com.poc.order.entity.OrderEntity;
import com.poc.order.entity.OrderItem;
import com.poc.order.repository.OrderRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClient productServiceClient;

    @Override
    @Observed(name = "createOrder")
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Start::createOrder() inside OrderServiceImpl for customerId: {}", request.getCustomerId());

        List<OrderItem> orderItems = request.getItems().stream().map(itemRequest -> {
            log.info("Calling Product Service via Feign for productId: {}", itemRequest.getProductId());
            ProductResponse product = productServiceClient.findProductById(itemRequest.getProductId());

            BigDecimal subtotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setSubtotal(subtotal);
            return orderItem;
        }).toList();

        BigDecimal totalAmount = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrderEntity entity = new OrderEntity();
        entity.setCustomerId(request.getCustomerId());
        entity.setItems(orderItems);
        entity.setTotalAmount(totalAmount);
        entity.setShippingAddress(request.getShippingAddress());
        entity.setStatus("PENDING");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        entity = orderRepository.save(entity);
        log.info("End::createOrder() inside OrderServiceImpl, orderId: {}", entity.getId());
        return mapToResponse(entity);
    }

    @Override
    public OrderResponse findOrderById(String orderId) {
        log.info("Start::findOrderById() inside OrderServiceImpl with id: {}", orderId);
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        log.info("End::findOrderById() inside OrderServiceImpl with id: {}", orderId);
        return mapToResponse(entity);
    }

    @Override
    public List<OrderResponse> findAllOrders() {
        log.info("Start::findAllOrders() inside OrderServiceImpl");
        List<OrderEntity> entities = orderRepository.findAll();
        log.info("End::findAllOrders() inside OrderServiceImpl, count: {}", entities.size());
        return entities.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<OrderResponse> findOrdersByCustomerId(String customerId) {
        log.info("Start::findOrdersByCustomerId() inside OrderServiceImpl for customerId: {}", customerId);
        List<OrderEntity> entities = orderRepository.findByCustomerId(customerId);
        log.info("End::findOrdersByCustomerId() inside OrderServiceImpl, count: {}", entities.size());
        return entities.stream().map(this::mapToResponse).toList();
    }

    @Override
    public List<OrderResponse> findOrdersByStatus(String status) {
        log.info("Start::findOrdersByStatus() inside OrderServiceImpl with status: {}", status);
        List<OrderEntity> entities = orderRepository.findByStatus(status);
        log.info("End::findOrdersByStatus() inside OrderServiceImpl, count: {}", entities.size());
        return entities.stream().map(this::mapToResponse).toList();
    }

    @Override
    public OrderResponse updateOrderStatus(String orderId, String status) {
        log.info("Start::updateOrderStatus() inside OrderServiceImpl with id: {}, status: {}", orderId, status);
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        entity.setStatus(status);
        entity.setUpdatedAt(LocalDateTime.now());
        OrderEntity updated = orderRepository.save(entity);
        log.info("End::updateOrderStatus() inside OrderServiceImpl with id: {}", orderId);
        return mapToResponse(updated);
    }

    @Override
    public String deleteOrderById(String orderId) {
        log.info("Start::deleteOrderById() inside OrderServiceImpl with id: {}", orderId);
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        orderRepository.delete(entity);
        log.info("End::deleteOrderById() inside OrderServiceImpl with id: {}", orderId);
        return "Order deleted successfully";
    }

    @Override
    public String deleteAllOrders() {
        log.info("Start::deleteAllOrders() inside OrderServiceImpl");
        orderRepository.deleteAll();
        log.info("End::deleteAllOrders() inside OrderServiceImpl");
        return "All orders deleted successfully";
    }

    private OrderResponse mapToResponse(OrderEntity entity) {
        OrderResponse response = new OrderResponse();
        response.setId(entity.getId());
        response.setCustomerId(entity.getCustomerId());
        response.setStatus(entity.getStatus());
        response.setTotalAmount(entity.getTotalAmount());
        response.setShippingAddress(entity.getShippingAddress());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());

        List<OrderItemResponse> itemResponses = entity.getItems().stream().map(item -> {
            OrderItemResponse ir = new OrderItemResponse();
            ir.setProductId(item.getProductId());
            ir.setProductName(item.getProductName());
            ir.setQuantity(item.getQuantity());
            ir.setUnitPrice(item.getUnitPrice());
            ir.setSubtotal(item.getSubtotal());
            return ir;
        }).toList();

        response.setItems(itemResponses);
        return response;
    }
}
