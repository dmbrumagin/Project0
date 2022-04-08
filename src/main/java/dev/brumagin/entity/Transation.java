package dev.brumagin.entity;

public class Transation {
    private double amountOfTransation;
    private String typeOfTransation;

    public double getAmountOfTransation() {
        return amountOfTransation;
    }

    public void setAmountOfTransation(double amountOfTransation) {
        this.amountOfTransation = amountOfTransation;
    }

    public String getTypeOfTransation() {
        return typeOfTransation;
    }

    public void setTypeOfTransation(String typeOfTransation) {
        this.typeOfTransation = typeOfTransation;
    }

    public int getOriginatingAccount() {
        return originatingAccount;
    }

    public void setOriginatingAccount(int originatingAccount) {
        this.originatingAccount = originatingAccount;
    }

    public int getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(int destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    private int originatingAccount;
    private int destinationAccount;

    public Transation( double amountOfTransation, String typeOfTransation, int originatingAccount, int destinationAccount){
        this.amountOfTransation = amountOfTransation;
        this.typeOfTransation = typeOfTransation;
        this.originatingAccount = originatingAccount;
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "Transation{" +
                "amountOfTransation=" + amountOfTransation +
                ", typeOfTransation='" + typeOfTransation + '\'' +
                ", originatingAccount=" + originatingAccount +
                ", destinationAccount=" + destinationAccount +
                '}';
    }
}
