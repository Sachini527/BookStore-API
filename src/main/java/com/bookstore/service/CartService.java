/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Cart;
import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import com.bookstore.exception.CartNotFoundException;
import com.bookstore.model.CartItem;
import java.util.List;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.Map;

public class CartService {

    private static Map<Integer, Cart> cartStore = new HashMap<>();
    private BookService bookService = new BookService();

    public Cart getCartByCustomerId(int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart == null) {
            throw new CartNotFoundException("Cart for customer ID " + customerId + " not found.");
        }
        return cart;
    }

    public Cart createCart(int customerId) {
        Cart cart = new Cart(customerId);
        cartStore.put(customerId, cart);
        return cart;
    }

    public Cart addItemToCart(int customerId, int bookId, int quantity) {
        Cart cart = cartStore.computeIfAbsent(customerId, Cart::new);
        Book book = bookService.getBookById(bookId);
        if (book.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock available.");
        }
        book.setStock(book.getStock() - quantity);
        cart.getItems().merge(bookId, quantity, Integer::sum);
        return cart;
    }

    public Cart updateItemQuantity(int customerId, int bookId, int quantity) {
        Cart cart = getCartByCustomerId(customerId);
        if (!cart.getItems().containsKey(bookId)) {
            throw new IllegalArgumentException("Book not in cart.");
        }
        Book book = bookService.getBookById(bookId);
        int currentQty = cart.getItems().get(bookId);
        int difference = quantity - currentQty;
        if (book.getStock() < difference) {
            throw new IllegalArgumentException("Not enough stock available to update.");
        }
        book.setStock(book.getStock() - difference);
        cart.getItems().put(bookId, quantity);
        return cart;
    }

    public Cart removeItemFromCart(int customerId, int bookId) {
        Cart cart = getCartByCustomerId(customerId);
        Integer qty = cart.getItems().remove(bookId);
        if (qty != null) {
            Book book = bookService.getBookById(bookId);
            book.setStock(book.getStock() + qty);
        }
        return cart;
    }

    // Fetch cart items as a list of CartItem
    public List<CartItem> getCartItems(int customerId) {
        Cart cart = getCartByCustomerId(customerId);
        return cart.getItems().entrySet().stream()
                .map(entry -> new CartItem(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

// Get book price for given bookId
    public double getBookPrice(int bookId) {
        Book book = bookService.getBookById(bookId);
        return book.getPrice();
    }

// Clear customer's cart after order is placed
    public void clearCart(int customerId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.getItems().clear();
    }
}
