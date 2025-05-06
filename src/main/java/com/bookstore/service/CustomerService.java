/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.service;

/**
 *
 * @author USER™
 */
import com.bookstore.model.Customer;
import com.bookstore.exception.CustomerNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {

    private static Map<Integer, Customer> customerStore = new HashMap<>();
    private static int currentId = 1;

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerStore.values());
    }

    public Customer getCustomerById(int id) {
        Customer customer = customerStore.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return customer;
    }

    public Customer addCustomer(Customer customer) {
        customer.setId(currentId++);
        customerStore.put(customer.getId(), customer);
        return customer;
    }

    public Customer updateCustomer(int id, Customer customer) {
        if (!customerStore.containsKey(id)) {
            throw new CustomerNotFoundException("Cannot update. Customer with ID " + id + " not found.");
        }
        customer.setId(id);
        customerStore.put(id, customer);
        return customer;
    }

    public void deleteCustomer(int id) {
        if (customerStore.remove(id) == null) {
            throw new CustomerNotFoundException("Cannot delete. Customer with ID " + id + " not found.");
        }
    }
}
