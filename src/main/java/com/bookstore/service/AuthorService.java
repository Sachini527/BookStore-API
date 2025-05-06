/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Author;
import com.bookstore.exception.AuthorNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorService {

    private static Map<Integer, Author> authorStore = new HashMap<>();
    private static int currentId = 1;

    public List<Author> getAllAuthors() {
        return new ArrayList<>(authorStore.values());
    }

    public Author getAuthorById(int id) {
        Author author = authorStore.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        return author;
    }

    public Author addAuthor(Author author) {
        author.setId(currentId++);
        authorStore.put(author.getId(), author);
        return author;
    }

    public Author updateAuthor(int id, Author author) {
        if (!authorStore.containsKey(id)) {
            throw new AuthorNotFoundException("Cannot update. Author with ID " + id + " not found.");
        }
        author.setId(id);
        authorStore.put(id, author);
        return author;
    }

    public void deleteAuthor(int id) {
        if (authorStore.remove(id) == null) {
            throw new AuthorNotFoundException("Cannot delete. Author with ID " + id + " not found.");
        }
    }
}
