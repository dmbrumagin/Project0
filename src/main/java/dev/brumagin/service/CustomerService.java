package dev.brumagin.service;

import dev.brumagin.entity.Customer;

public interface CustomerService {
    Customer createCustomer(String firstName, String lastName);
    Customer getCustomer(int customerId);
    boolean updateName(Customer customer,String firstName,String lastName);
    boolean deleteCustomer(int customerId);
    boolean registerNewOnlineAccount();
    boolean login(String username);
    boolean login(String username,String password);
    boolean updatePassword(String username, String currentPassword);
    boolean closeOnlineAccount(String username, String password);
}
