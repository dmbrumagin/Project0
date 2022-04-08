package dev.brumagin.entity;

public class CheckingBankAccount extends BankAccount {

    public CheckingBankAccount(){
        super();
    }

    public CheckingBankAccount(Customer accountHolder){
        super(accountHolder);
    }

    public CheckingBankAccount(Customer accountHolder, double startingDeposit){
        super(accountHolder,startingDeposit);
    }

    public CheckingBankAccount(Customer accountHolder, Customer jointAccountHolder, double startingDeposit){
        super(accountHolder,jointAccountHolder,startingDeposit);
    }

}
