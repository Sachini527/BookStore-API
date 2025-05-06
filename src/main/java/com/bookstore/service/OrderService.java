/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

/**
 *
 * @author USER™
 */
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class to manage customer orders.
 */
public class OrderService {

    private static Map<Integer, List<Order>> customerOrders = new HashMap<>();
    private static int currentOrderId = 1;

    public Order createOrder(int customerId, List<CartItem> cartItems, double totalPrice) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new InvalidInputException("Cannot create an order with an empty cart.");
        }
        Order order = new Order(currentOrderId++, customerId, cartItems, totalPrice);
        customerOrders.computeIfAbsent(customerId, k -> new ArrayList<>()).add(order);
        System.out.println("Created Order ID: " + order.getOrderId() + " for Customer ID: " + customerId);
        return order;
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        return customerOrders.getOrDefault(customerId, new ArrayList<>());
    }

    public Order getOrderById(int customerId, int orderId) {
        List<Order> orders = customerOrders.get(customerId);
        if (orders == null || orders.isEmpty()) {
            throw new InvalidInputException("Customer ID " + customerId + " has no orders.");
        }
        return orders.stream()
                .filter(order -> order.getOrderId() == orderId)
                .findFirst()
                .orElseThrow(() -> new InvalidInputException("Order ID " + orderId + " not found for Customer ID " + customerId));
    }
}
