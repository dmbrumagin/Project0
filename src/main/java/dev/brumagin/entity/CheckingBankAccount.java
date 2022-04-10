package dev.brumagin.entity;

public class CheckingBankAccount extends BankAccount {

    public CheckingBankAccount(){
        super();
    }

    public CheckingBankAccount(String accountHolder){
        super(accountHolder);
    }


    public CheckingBankAccount(String accountHolder, String jointAccountHolder){
        super(accountHolder,jointAccountHolder);
    }

}
