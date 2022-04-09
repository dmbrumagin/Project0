package dev.brumagin.service;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.Customer;

public interface BankAccountService {

    public BankAccount createAccount(Customer customer);
    public BankAccount createAccount(Customer customer,Customer jointAccount);
    public BankAccount deposit(BankAccount bankAccount, double moneyToDeposit);
    public boolean withdraw(BankAccount account, double amountToWithdraw);
    public boolean transferFunds(BankAccount origin, BankAccount destination, double moneyToMove);
    public void printBalance(BankAccount account);
}
