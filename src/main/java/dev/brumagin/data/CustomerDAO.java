package dev.brumagin.data;

import dev.brumagin.entity.Customer;

public interface CustomerDAO {
    Customer createCustomer (Customer customer);
    Customer getCustomerById (int customerId);
    Customer updateCustomer (Customer customer);
    boolean deleteCustomer(int customerId);

}
