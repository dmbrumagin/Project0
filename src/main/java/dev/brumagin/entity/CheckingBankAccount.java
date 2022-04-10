package dev.brumagin.entity;

public class CheckingBankAccount extends BankAccount {

    public CheckingBankAccount(){
        super();
    }

    public CheckingBankAccount(int accountHolder){
        super(accountHolder);
    }


    public CheckingBankAccount(int accountHolder, int jointAccountHolder){
        super(accountHolder,jointAccountHolder);
    }

}
