package dev.brumagin.servicestests;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.service.BankAccountService;
import dev.brumagin.service.BankAccountServiceImpl;
import dev.brumagin.service.CustomerService;
import dev.brumagin.service.CustomerServiceImpl;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAccountServiceTests {

    static BankAccountService bankAccountService = new BankAccountServiceImpl();
    static BankAccount testAccount;

    static CustomerService cService = new CustomerServiceImpl();
    static Customer testCustomer;
    @Test
    @Order(1)
    void create_account(){
        testCustomer = cService.createCustomer("Santa","Claus");
        testAccount = bankAccountService.createAccount(testCustomer.getCustomerID(),0,'c');
        Assertions.assertNotEquals(0,testAccount.getAccountNumber());
    }

    @Test
    @Order(2)
    void deposit_small(){
        double currentMoney = testAccount.getAccountBalance();
        testAccount = bankAccountService.deposit(testAccount,200);
        Assertions.assertEquals(currentMoney +200,testAccount.getAccountBalance());
    }

    @Test
    @Order(3)
    void withdraw_small(){
        double currentMoney = testAccount.getAccountBalance();
        bankAccountService.withdraw(testAccount,200);
        Assertions.assertEquals(currentMoney -200,testAccount.getAccountBalance());
    }

    @Test
    @Order(4)
    void transfer_small(){
        double currentMoney = testAccount.getAccountBalance()+200;
        testAccount = bankAccountService.deposit(testAccount,200);
        BankAccount bankAccount = new CheckingBankAccount(testCustomer.getCustomerID(),300);
        bankAccount = bankAccountService.createAccount(bankAccount.getAccountHolder(),0,'c');
        bankAccountService.transferFunds(testAccount,bankAccount,200);
        Assertions.assertEquals(500,bankAccount.getAccountBalance());
        Assertions.assertEquals(0,currentMoney-200);
    }


}
