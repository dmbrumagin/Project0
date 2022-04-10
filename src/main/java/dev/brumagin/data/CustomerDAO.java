package dev.brumagin.data;

import dev.brumagin.entity.Customer;

public interface CustomerDAO {
    Customer createCustomer (Customer customer);
    Customer getCustomerById (String customerId);
    Customer updateCustomer (Customer customer);
    boolean deleteCustomer(String customerId);
    Customer createLogin(Customer customer);
    String getLogin(String username,String password);
    boolean getLogin(String username);
    Customer updateLogin(Customer customer);
    boolean deleteLogin(Customer customer);

}
