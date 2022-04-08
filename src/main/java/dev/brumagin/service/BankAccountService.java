package dev.brumagin.service;

import dev.brumagin.entity.BankAccount;

public interface BankAccountService {

    public void deposit(double moneyToDeposit);
    public void deposit(BankAccount bankAccount, double moneyToDeposit);
    public boolean withdraw(double amountToWithdraw);
    public boolean transferFunds(BankAccount destination, double moneyToMove);

    public void printBalance(BankAccount account);
}
