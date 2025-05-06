/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Author;
import com.bookstore.service.AuthorService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private AuthorService authorService = new AuthorService();

    @POST
    public Response createAuthor(Author author) {
        if (author.getFirstName() == null || author.getFirstName().isEmpty()
                || author.getLastName() == null || author.getLastName().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Author first name and last name are required.").build();
        }
        Author createdAuthor = authorService.addAuthor(author);
        System.out.println("Created Author: " + createdAuthor.getId());
        return Response.status(Response.Status.CREATED).entity(createdAuthor).build();
    }

    @GET
    public Response getAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        System.out.println("Fetched all authors.");
        return Response.ok(authors).build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") int id) {
        Author author = authorService.getAuthorById(id);
        System.out.println("Fetched Author ID: " + id);
        return Response.ok(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        System.out.println("Updated Author ID: " + id);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        authorService.deleteAuthor(id);
        System.out.println("Deleted Author ID: " + id);
        return Response.noContent().build();
    }
}
