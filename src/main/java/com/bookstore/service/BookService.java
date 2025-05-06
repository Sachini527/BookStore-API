/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Book;
import com.bookstore.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private static Map<Integer, Book> bookStore = new HashMap<>();
    private static int currentId = 1;

    public List<Book> getAllBooks() {
        return new ArrayList<>(bookStore.values());
    }

    public Book getBookById(int id) {
        Book book = bookStore.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        return book;
    }

    public Book addBook(Book book) {
        book.setId(currentId++);
        bookStore.put(book.getId(), book);
        return book;
    }

    public Book updateBook(int id, Book book) {
        if (!bookStore.containsKey(id)) {
            throw new BookNotFoundException("Cannot update. Book with ID " + id + " not found.");
        }
        book.setId(id);
        bookStore.put(id, book);
        return book;
    }

    public void deleteBook(int id) {
        if (bookStore.remove(id) == null) {
            throw new BookNotFoundException("Cannot delete. Book with ID " + id + " not found.");
        }
    }
}
