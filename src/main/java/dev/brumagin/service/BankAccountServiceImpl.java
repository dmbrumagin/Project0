package dev.brumagin.service;

import dev.brumagin.data.BankAccountDAO;
import dev.brumagin.data.BankAccountDAOPostgresImpl;
import dev.brumagin.data.CustomerDAO;
import dev.brumagin.data.CustomerDAOPostgresImpl;
import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.entity.SavingsBankAccount;
import dev.brumagin.utility.LinkedList;

public class BankAccountServiceImpl implements BankAccountService{

    BankAccountDAO bankAccountDAO = new BankAccountDAOPostgresImpl();
    CustomerDAO customerDAO = new CustomerDAOPostgresImpl();


    @Override
    public BankAccount createAccount(String customer, String jointAccount, char cS) {
        if(cS=='c'){
            BankAccount checkingBankAccount = new CheckingBankAccount(customer,jointAccount);
            checkingBankAccount =  bankAccountDAO.createAccount(checkingBankAccount,'c');
            return checkingBankAccount;
        }
        else if(cS=='s'){
            BankAccount savingsBankAccount = new SavingsBankAccount(customer,jointAccount);
            savingsBankAccount = bankAccountDAO.createAccount(savingsBankAccount,'s');
            return savingsBankAccount;
        }
        return null;
    }

    @Override
    public BankAccount deposit(BankAccount bankAccount, double moneyToDeposit) {
        bankAccount.setAccountBalance(bankAccount.getAccountBalance()+moneyToDeposit);
        bankAccount =bankAccountDAO.updateAccount(bankAccount);
        return bankAccount;
    }

    @Override
    public boolean withdraw(BankAccount bankAccount, double amountToWithdraw) {
        if(amountToWithdraw<= bankAccount.getAccountBalance()){
            bankAccount.setAccountBalance(bankAccount.getAccountBalance()-amountToWithdraw);
            bankAccountDAO.updateAccount(bankAccount);
            return  true;
        }
        return false;
    }

    @Override
    public boolean transferFunds(BankAccount origin, BankAccount destination, double moneyToMove) {
        if(withdraw(origin,moneyToMove)) {
            deposit(destination, moneyToMove);
            return true;
        }

        return false;
    }
    @Override
    public LinkedList<BankAccount> getAllAccounts(String customerId) {
        return bankAccountDAO.getAllBankAccounts(customerId);
    }

    @Override
    public boolean isAccount(long accountId) {
        return bankAccountDAO.getAccountByNumber(accountId)!=null;
    }

    @Override
    public BankAccount getAccount(long accountId){
        return bankAccountDAO.getAccountByNumber(accountId);
    }

    @Override
    public BankAccount updateAccount(BankAccount account){
       return bankAccountDAO.updateAccount(account);
    }

    public void printBalance(BankAccount account) {
        System.out.printf("The current balance of %s account #: %d  is $%.2f.%n",account.getAccountType(), account.getAccountNumber(), account.getAccountBalance());
    }

}
