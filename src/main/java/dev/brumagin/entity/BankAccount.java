package dev.brumagin.entity;

abstract public class BankAccount{

    protected long accountNumber;
    protected double accountBalance;
    protected String accountHolder;
    protected String jointAccountHolder;

    public BankAccount(){
        this.accountHolder = null;
        this.jointAccountHolder = null;
        this.accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount( String accountHolder){
        this.accountHolder= accountHolder;
        this.accountBalance = 0;
        this.accountNumber = 0;
    }

    public BankAccount(String accountHolder, String jointAccountHolder){
        this.accountHolder= accountHolder;
        this.jointAccountHolder = jointAccountHolder;
        this.accountBalance = 0;
        this.accountNumber = 0;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getJointAccountHolder() {
        return jointAccountHolder;
    }

    public void setJointAccountHolder(String jointAccountHolder) {
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
