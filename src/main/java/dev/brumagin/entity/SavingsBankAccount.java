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

    public SavingsBankAccount(int accountHolder, int jointAccountHolder){
        super(accountHolder,jointAccountHolder);
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
