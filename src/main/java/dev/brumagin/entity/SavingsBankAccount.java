package dev.brumagin.entity;

public class SavingsBankAccount extends BankAccount {

    private float interestRate;

    public SavingsBankAccount(){
        super();
    }

    public SavingsBankAccount(int accountHolder){
        super(accountHolder);
        this.interestRate= 0.025f;
    }

    public SavingsBankAccount(int accountHolder, double startingDeposit){
        super(accountHolder,startingDeposit);
        this.interestRate= 0.025f;
    }

    public SavingsBankAccount(int accountHolder, int jointAccountHolder, double startingDeposit){
        super(accountHolder,jointAccountHolder,startingDeposit);
        this.interestRate= 0.025f;
    }

    @Override
    public String toString() {
        return "SavingsBankAccount{" +
                "accountHolder=" + accountHolder +
                ", jointAccountHolder=" + jointAccountHolder +
                ", accountBalance=" + accountBalance +
                ", accountNumber=" + accountNumber +
                ", interestRate=" + interestRate +
                '}';
    }
}
