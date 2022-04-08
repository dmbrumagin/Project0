package dev.brumagin.service;

import dev.brumagin.entity.BankAccount;

public class BankAccountServiceImpl {
    public void deposit(double moneyToDeposit){
        //accountBalance +=moneyToDeposit;
    }

    public void deposit(BankAccount bankAccount, double moneyToDeposit){
       // bankAccount.setAccountBalance(bankAccount.getAccountBalance() +moneyToDeposit);
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
