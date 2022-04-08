package dev.brumagin.daotests;

import dev.brumagin.data.BankAccountDAO;
import dev.brumagin.data.BankAccountDAOPostgresImpl;
import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountDaoTests{

    static CustomerDAO customerDAO = new CustomerDAOPostgresImpl();
    static BankAccountDAO bankAccountDAO = new BankAccountDAOPostgresImpl();
    static BankAccount testAccount;
    static BankAccount secondaryAccount;

    @Test
    @Order(1)
    void create_account(){
        Customer customer = new Customer("Bob","Evans");

        BankAccount account = new CheckingBankAccount(customerDAO.createCustomer(customer), 200L);
        testAccount = bankAccountDAO.createAccount(account);
        Assertions.assertNotEquals( 0,testAccount.getAccountNumber());
    }

    @Test
    @Order(2)
    void get_account_by_id(){
        BankAccount account = bankAccountDAO.getAccountByNumber(testAccount.getAccountNumber());
        Assertions.assertEquals(account.getAccountNumber(),testAccount.getAccountNumber());
    }

    @Test
    @Order(3)
    void update_account_by_reference(){
        testAccount.setAccountBalance(9000);
        BankAccount account = bankAccountDAO.updateAccount(testAccount);
        Assertions.assertEquals(testAccount.getAccountNumber(),account.getAccountNumber());
    }


    @Test
    @Order(4)
    void delete_account_by_id(){
        boolean deleted = bankAccountDAO.deleteAccount(testAccount.getAccountNumber());
        Assertions.assertTrue(deleted);
    }


}
