package dev.brumagin.entity;

abstract public class BankAccount{

    protected long accountNumber;
    protected double accountBalance;
    protected Customer accountHolder;
    protected Customer jointAccountHolder;

    public BankAccount(){
        this.accountHolder = null;
        this.jointAccountHolder = null;
        this.accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount(Customer accountHolder){
        this.accountHolder = accountHolder;
        accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount(Customer accountHolder, double accountBalance){
        this(accountHolder);
        this.accountBalance = accountBalance;
        this.accountNumber = 0;
    }

    public BankAccount(Customer accountHolder, Customer jointAccountHolder, double accountBalance){
        this(accountHolder);
        this.jointAccountHolder = jointAccountHolder;
        this.accountBalance = accountBalance;
        this.accountNumber = 0;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Customer accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Customer getJointAccountHolder() {
        return jointAccountHolder;
    }

    public void setJointAccountHolder(Customer jointAccountHolder) {
        this.jointAccountHolder = jointAccountHolder;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountHolder=" + accountHolder +
                ", jointAccountHolder=" + jointAccountHolder +
                ", accountBalance=" + accountBalance +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
