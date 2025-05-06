/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Customer;
import com.bookstore.service.CustomerService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private CustomerService customerService = new CustomerService();

    @POST
    public Response createCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().isEmpty()
                || customer.getLastName() == null || customer.getLastName().isEmpty()
                || customer.getEmail() == null || customer.getEmail().isEmpty()
                || customer.getPassword() == null || customer.getPassword().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("All fields (firstName, lastName, email, password) are required.").build();
        }
        Customer createdCustomer = customerService.addCustomer(customer);
        System.out.println("Created Customer: " + createdCustomer.getId());
        return Response.status(Response.Status.CREATED).entity(createdCustomer).build();
    }

    @GET
    public Response getCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("Fetched all customers.");
        return Response.ok(customers).build();
    }

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") int id) {
        Customer customer = customerService.getCustomerById(id);
        System.out.println("Fetched Customer ID: " + id);
        return Response.ok(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        System.out.println("Updated Customer ID: " + id);
        return Response.ok(updatedCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        customerService.deleteCustomer(id);
        System.out.println("Deleted Customer ID: " + id);
        return Response.noContent().build();
    }
}
