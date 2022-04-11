package dev.brumagin.service;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.utility.LinkedList;

public interface BankAccountService {

    BankAccount createAccount(String customer,String jointAccount, char cS);
    BankAccount deposit(BankAccount bankAccount, double moneyToDeposit);
    boolean withdraw(BankAccount account, double amountToWithdraw);
    LinkedList<BankAccount> getAllAccounts(String customerId);
    boolean isAccount(long accountId);
    BankAccount getAccount(long accountId);
    BankAccount updateAccount(BankAccount account);
    boolean transferFunds(BankAccount origin, BankAccount destination, double moneyToMove);
    void printBalance(BankAccount account);
}
