/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author USER™
 */
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.service.CartService;
import com.bookstore.service.OrderService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderService orderService = new OrderService();
    private CartService cartService = new CartService();

    @POST
    public Response createOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cartItems = cartService.getCartItems(customerId);
        if (cartItems.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Cart is empty. Cannot create order.").build();
        }
        double totalPrice = cartItems.stream().mapToDouble(item -> cartService.getBookPrice(item.getBookId()) * item.getQuantity()).sum();
        Order newOrder = orderService.createOrder(customerId, cartItems, totalPrice);
        cartService.clearCart(customerId);
        System.out.println("Created Order for Customer ID: " + customerId);
        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    @GET
    public Response getOrders(@PathParam("customerId") int customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        System.out.println("Fetched Orders for Customer ID: " + customerId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/{orderId}")
    public Response getOrderById(@PathParam("customerId") int customerId, @PathParam("orderId") int orderId) {
        Order order = orderService.getOrderById(customerId, orderId);
        System.out.println("Fetched Order ID: " + orderId + " for Customer ID: " + customerId);
        return Response.ok(order).build();
    }
}
