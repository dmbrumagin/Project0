package dev.brumagin.entity;

abstract public class BankAccount{

    protected long accountNumber;
    protected double accountBalance;
    protected int accountHolder;
    protected int jointAccountHolder;

    public BankAccount(){
        this.accountHolder = 0;
        this.jointAccountHolder = 0;
        this.accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount(int accountHolder){
        this.accountHolder = accountHolder;
        accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount( int accountHolder, double accountBalance){
        this(accountHolder);
        this.accountBalance = accountBalance;
        this.accountNumber = 0;
    }

    public BankAccount(int accountHolder, int jointAccountHolder, double accountBalance){
        this(accountHolder);
        this.jointAccountHolder = jointAccountHolder;
        this.accountBalance = accountBalance;
        this.accountNumber = 0;
    }

    public int getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(int accountHolder) {
        this.accountHolder = accountHolder;
    }

    public int getJointAccountHolder() {
        return jointAccountHolder;
    }

    public void setJointAccountHolder(int jointAccountHolder) {
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
