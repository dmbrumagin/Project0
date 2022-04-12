package dev.brumagin.entity;

import java.sql.Date;

public class Transation {
    private long transactionId;
    private double amountOfTransation;
    private TransactionType typeOfTransation;
    private long originAccount;
    private long destinationAccount;
    private long epochTime;

    public Transation() {
    }

    public Transation(  long originAccount, long destinationAccount,double amountOfTransation, TransactionType typeOfTransation, long epochTime) {
        this.amountOfTransation = amountOfTransation;
        this.typeOfTransation = typeOfTransation;
        this.originAccount = originAccount;
        this.destinationAccount = destinationAccount;
        this.epochTime = epochTime;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmountOfTransation() {
        return amountOfTransation;
    }

    public void setAmountOfTransation(double amountOfTransation) {
        this.amountOfTransation = amountOfTransation;
    }

    public TransactionType getTypeOfTransation() {
        return typeOfTransation;
    }

    public void setTypeOfTransation(TransactionType typeOfTransation) {
        this.typeOfTransation = typeOfTransation;
    }

    public long getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(long originAccount) {
        this.originAccount = originAccount;
    }

    public long getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(long destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public long getEpochTime() {
        return epochTime;
    }

    public void setEpochTime(long epochTime) {
        this.epochTime = epochTime;
    }

    @Override
    public String toString() {
        return "Transation{" +
                "transactionId='" + transactionId + '\'' +
                ", amountOfTransation=" + amountOfTransation +
                ", typeOfTransation=" + typeOfTransation +
                ", originAccount=" + originAccount +
                ", destinationAccount=" + destinationAccount +
                ", epochTime=" + epochTime +
                '}';
    }
}
