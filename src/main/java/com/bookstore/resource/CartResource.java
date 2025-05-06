/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Cart;
import com.bookstore.service.CartService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private CartService cartService = new CartService();

    @POST
    @Path("/items")
    public Response addItemToCart(@PathParam("customerId") int customerId, @QueryParam("bookId") int bookId, @QueryParam("quantity") int quantity) {
        if (quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Quantity must be greater than 0.").build();
        }
        Cart cart = cartService.addItemToCart(customerId, bookId, quantity);
        System.out.println("Added book ID " + bookId + " to cart for customer ID: " + customerId);
        return Response.ok(cart).build();
    }

    @GET
    public Response getCart(@PathParam("customerId") int customerId) {
        Cart cart = cartService.getCartByCustomerId(customerId);
        System.out.println("Fetched cart for customer ID: " + customerId);
        return Response.ok(cart).build();
    }

    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, @QueryParam("quantity") int quantity) {
        if (quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Quantity must be greater than 0.").build();
        }
        Cart cart = cartService.updateItemQuantity(customerId, bookId, quantity);
        System.out.println("Updated quantity for book ID " + bookId + " in cart for customer ID: " + customerId);
        return Response.ok(cart).build();
    }

    @DELETE
    @Path("/items/{bookId}")
    public Response removeItemFromCart(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        Cart cart = cartService.removeItemFromCart(customerId, bookId);
        System.out.println("Removed book ID " + bookId + " from cart for customer ID: " + customerId);
        return Response.ok(cart).build();
    }
}
