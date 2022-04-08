package dev.brumagin.data;

import dev.brumagin.entity.BankAccount;

public interface BankAccountDAO {
    BankAccount createAccount(BankAccount bankAccount);
    BankAccount getAccountByNumber(long accountNumber);
    BankAccount updateAccount(BankAccount bankAccount);
    boolean deleteAccount(long accountNumber);

}
