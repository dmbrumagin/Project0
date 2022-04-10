package dev.brumagin.data;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.utility.LinkedList;

public interface BankAccountDAO {
    BankAccount createAccount(BankAccount bankAccount);
    BankAccount getAccountByNumber(long accountNumber);
    BankAccount updateAccount(BankAccount bankAccount);
    LinkedList<BankAccount> getAllBankAccounts (String userId);
    boolean deleteAccount(long accountNumber);

}
