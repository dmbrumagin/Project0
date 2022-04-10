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

    @Test
    void create_login_with_valid_security(){
        String username = "MuffinMan";
        String password = "Gingerbread127#";
        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertTrue(cService.registerNewAccount(customer,username,password));

    }

    @Test
    void login_invalid_user(){
        String username = "Muffin";
        String password = "1234567890";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }

    @Test
    void login_invalid_user_big(){
        String username = "MuffinmanMuffinmanMuffinmanMuffinman";
        String password = "1234567890";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }
    @Test
    void login_invalid_user_space(){
        String username = "Muffin Man";
        String password = "1234567890";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }

    @Test
    void login_invalid_password_short(){
        String username = "MuffinMan";
        String password = "123456789";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }
    @Test
    void login_invalid_password_no_cap(){
        String username = "MuffinMan";
        String password = "blue#12345";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }
    @Test
    void login_invalid_password_no_num(){
        String username = "MuffinMan";
        String password = "Blue#abcde";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }
    @Test
    void login_invalid_password_no_special(){
        String username = "MuffinMan";
        String password = "Blues12345";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        Assertions.assertFalse(cService.registerNewAccount(customer,username,password));
    }
    @Test
    void login_with_valid(){
        String username = "MuffinMan2";
        String password = "Blues12345!";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        cService.registerNewAccount(customer,username,password);
        Assertions.assertEquals(customer.getCustomerID(),cService.login(username,password).getCustomerID());
    }
    @Test
    void update_logon(){
        String username = "MuffinMan";
        String password = "Blues12345!";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        cService.registerNewAccount(customer,username,password);

       customer =  cService.updatePassword(customer,"RedsMuffin1234!");
        Assertions.assertEquals("RedsMuffin1234!",cService.getCustomer(customer.getCustomerID()).getPassword());
    }

    @Test
    void delete_logon(){
        String username = "MuffinMan";
        String password = "Blues12345!";

        String s1 = "Danny";
        String s2 = "Boy";

        Customer customer = cService.createCustomer(s1,s2);
        cService.registerNewAccount(customer,username,password);
        cService.closeOnlineAccount(customer);
        Assertions.assertEquals(null,cService.getCustomer(customer.getCustomerID()).getUsername());
    }
}
