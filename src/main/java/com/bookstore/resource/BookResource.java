/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Book;
import com.bookstore.service.BookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private BookService bookService = new BookService();

    @POST
    public Response createBook(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Book title is required.").build();
        }
        Book createdBook = bookService.addBook(book);
        System.out.println("Created Book: " + createdBook.getId());
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    @GET
    public Response getBooks() {
        List<Book> books = bookService.getAllBooks();
        System.out.println("Fetched all books.");
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = bookService.getBookById(id);
        System.out.println("Fetched Book ID: " + id);
        return Response.ok(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        System.out.println("Updated Book ID: " + id);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        bookService.deleteBook(id);
        System.out.println("Deleted Book ID: " + id);
        return Response.noContent().build();
    }
}
