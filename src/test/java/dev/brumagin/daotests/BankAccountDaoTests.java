package dev.brumagin.daotests;

import dev.brumagin.data.BankAccountDAO;
import dev.brumagin.data.BankAccountDAOPostgresImpl;
import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.utility.LinkedList;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountDaoTests{

    static CustomerDAO customerDAO = new CustomerDAOPostgresImpl();
    static BankAccountDAO bankAccountDAO = new BankAccountDAOPostgresImpl();
    static BankAccount testAccount;
    static Customer testCustomer;

    @Test
    @Order(1)
    void create_account(){
        Customer customer = new Customer("Bob","Evans");
        testCustomer = customerDAO.createCustomer(customer);

        BankAccount account = new CheckingBankAccount(testCustomer.getCustomerID(), 200L);
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
        customerDAO.deleteCustomer(testCustomer.getCustomerID());
        Assertions.assertTrue(deleted);
    }

    @Test
    @Order(5)
    void get_all_accounts_by_user_id(){
        Customer customer = new Customer("Bob","Evans");
        BankAccount account = new CheckingBankAccount(customerDAO.createCustomer(customer).getCustomerID(), 200L);
        account = bankAccountDAO.createAccount(account);
        int customerId= account.getAccountHolder();
        LinkedList<BankAccount> bankAccountList= bankAccountDAO.getAllBankAccounts(customerId);
        Assertions.assertEquals(1,bankAccountList.size());
    }
}
