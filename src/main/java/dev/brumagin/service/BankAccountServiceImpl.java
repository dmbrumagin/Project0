package dev.brumagin.service;

import dev.brumagin.data.BankAccountDAO;
import dev.brumagin.data.BankAccountDAOPostgresImpl;
import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.Customer;

public class BankAccountServiceImpl implements BankAccountService{

    BankAccountDAO bankAccountDAO = new BankAccountDAOPostgresImpl();
    CustomerDAO customerDAO = new CustomerDAOPostgresImpl();

    @Override
    public BankAccount createAccount(Customer customer) {
        //  BankAccount account = bankAccountDAO.createAccount();


        return null;
    }

    @Override
    public BankAccount createAccount(Customer customer, Customer jointAccount) {
        return null;
    }

    @Override
    public BankAccount deposit(BankAccount bankAccount, double moneyToDeposit) {
        return null;
    }

    public void deposit(double moneyToDeposit){
        //accountBalance +=moneyToDeposit;
    }

    @Override
    public boolean withdraw(BankAccount account, double amountToWithdraw) {
        return false;
    }

    @Override
    public boolean transferFunds(BankAccount origin, BankAccount destination, double moneyToMove) {
        return false;
    }

    public boolean withdraw(double amountToWithdraw){
       /* if(amountToWithdraw <= bankAccount.getAccountBalance()){

            accountBalance-=amountToWithdraw;
            printBalance();

            return true;
        }

        System.out.println("The operation could not be completed.");
        printBalance();
        System.out.printf("The amount $%.2f is greater than the available funds.",amountToWithdraw);*/

        return false;
    }

    public void printBalance(BankAccount account) {
        System.out.printf("The current balance of checking account #: %d  is $%.2f.%n", account.getAccountNumber(), account.getAccountBalance());

    }

    public boolean transferFunds(BankAccount destination, double moneyToMove) {

        if(withdraw(moneyToMove)){
            deposit(destination,moneyToMove);
            return true;
        }

        return false;
    }

}
