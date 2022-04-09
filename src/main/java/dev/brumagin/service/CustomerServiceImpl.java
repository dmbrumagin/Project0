package dev.brumagin.service;

import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.Customer;

public class CustomerServiceImpl implements CustomerService{

    CustomerDAO customerDAO = new CustomerDAOPostgresImpl();

    @Override
    public Customer createCustomer(String firstName, String lastName) {
        if(firstName.equals("")||lastName.equals(""))
            return null;

        for(int i = 0 ; i < firstName.length(); i++){
            if(Character.isDigit(firstName.charAt(i))){
                return null;
            }
        }
        for(int i = 0 ; i < lastName.length(); i++){
            if(Character.isDigit(lastName.charAt(i))){
                return null;
            }
        }
        Customer customer = new Customer(firstName,lastName);
        customer = customerDAO.createCustomer(customer);
        return customer;
    }

    @Override
    public boolean deleteCustomer(int customerId){
        return customerDAO.deleteCustomer(customerId);
    }

    @Override
    public boolean updateName(Customer customer,String firstName,String lastName) {
        boolean bool = true;
        if(firstName.equals("")||lastName.equals(""))
            return false;

        for(int i = 0 ; i < firstName.length(); i++){
            if(!Character.isAlphabetic(firstName.charAt(i))){
                return false;
            }
        }
        for(int i = 0 ; i < lastName.length(); i++){
            if(!Character.isAlphabetic(lastName.charAt(i))){
                return false;
            }
        }

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer = customerDAO.updateCustomer(customer);
        return true;
    }

    @Override
    public Customer getCustomer(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public boolean registerNewOnlineAccount() {
        return false;
    }

    @Override
    public boolean login(String username) {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean updatePassword(String username, String currentPassword) {
        return false;
    }

    @Override
    public boolean closeOnlineAccount(String username, String password) {
        return false;
    }
}
