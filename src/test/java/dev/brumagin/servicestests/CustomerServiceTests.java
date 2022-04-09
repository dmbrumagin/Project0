package dev.brumagin.servicestests;

import dev.brumagin.entity.Customer;
import dev.brumagin.service.CustomerService;
import dev.brumagin.service.CustomerServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CustomerServiceTests {

    static CustomerService cService = new CustomerServiceImpl();

    @Test
    void create_customer(){
        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertNotEquals(0,customer.getCustomerID());
    }

    @Test
    void create_customer_with_invalid_numbers(){
        String s1 = "Danny123";
        String s2 = "Boy456";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertNull(customer);
    }

    @Test
    void create_customer_with_foreign_name(){
        String s1 = "丁丂七";
        String s2 = "嘅燈膽";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertNotEquals(0,customer.getCustomerID());
    }

    @Test
    void create_customer_with_heiphen_and_space(){
        String s1 = "Danny-Boy";
        String s2 = "Boy Sr.";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertNotEquals(0,customer.getCustomerID());
    }

    @Test
    void update_customer(){
        Customer customer = cService.getCustomer(1);
        Assertions.assertTrue(cService.updateName(customer, customer.getFirstName()+"edit", customer.getLastName()+"edit"));

    }

    @Disabled
    @Test
    void create_login_with_valid_security(){
        String username = "MuffinMan";
        String password = "Gingerbread127#";
        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        customer.setUsername(username);
        customer.setPassword(password);

       // LoginCredential createdLogon = loginCredentialDAO.createLogin(logon);

    }
}
