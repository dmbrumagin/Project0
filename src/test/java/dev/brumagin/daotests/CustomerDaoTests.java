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
    @Order(9)
    void delete_customer_by_id(){
        boolean deleted = customerDAO.deleteCustomer(testCustomer.getCustomerID());
        Assertions.assertTrue(deleted);
    }

    @Test
    @Order(4)
    void create_login(){
        testCustomer.setUsername("username4");
        testCustomer.setPassword("passwordTest");
        Customer localCustomer = customerDAO.createLogin(testCustomer);
        Assertions.assertEquals(localCustomer.getUsername(),testCustomer.getUsername());
    }


    @Test
    @Order(5)
    void get_login_from_credential(){
        String login = customerDAO.getLogin(testCustomer.getUsername(),testCustomer.getPassword());
        Assertions.assertNotEquals(-1,login);
    }
    @Test
    @Order(6)
    void get_login_from_username(){
        boolean login = customerDAO.getLogin(testCustomer.getUsername());
        Assertions.assertTrue(login);
    }

    @Test
    @Order(7)
    void update_login_from_credential(){
        testCustomer.setPassword("passwordChanged");
        Customer customer = customerDAO.updateLogin(testCustomer);
        Assertions.assertEquals("passwordChanged",customer.getPassword());
    }
    @Test
    @Order(8)
    void delete_login_credentials(){
        boolean login = customerDAO.deleteLogin(testCustomer);
        Assertions.assertTrue(login);
    }

}
