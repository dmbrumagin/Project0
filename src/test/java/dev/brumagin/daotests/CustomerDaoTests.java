package dev.brumagin.daotests;

import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.Customer;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerDaoTests {

    static CustomerDAO customerDAO = new CustomerDAOPostgresImpl();
    static Customer testCustomer;

    @Test
    @Order(1)
    void create_customer(){
        Customer customer = new Customer("Bob","Evans");
        Customer temporaryCustomer = customerDAO.createCustomer(customer);
        testCustomer = temporaryCustomer;
        Assertions.assertNotEquals(0,temporaryCustomer.getCustomerID());
    }

    @Test
    @Order(2)
    void get_customer_by_id(){
        Customer customer = customerDAO.getCustomerById(testCustomer.getCustomerID());
        Assertions.assertEquals(customer.getFirstName(),testCustomer.getFirstName());
    }

    @Test
    @Order(3)
    void update_customer_by_reference(){
        testCustomer.setFirstName("NotBob");
        Customer customer = customerDAO.updateCustomer(testCustomer);
        Assertions.assertEquals(testCustomer.getFirstName(),customer.getFirstName());
    }

    @Test
    @Order(4)
    void delete_customer_by_id(){
        boolean deleted = customerDAO.deleteCustomer(testCustomer.getCustomerID());
        Assertions.assertTrue(deleted);
    }

}
