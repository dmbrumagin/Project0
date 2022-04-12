package dev.brumagin.service;

import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;
import dev.brumagin.utility.LinkedList;

public interface TransactionService {

    public Transation createTransaction(long bankAccount, long destinationAccount, TransactionType transactionType, double amountOfTransaction, long epochTime);
    public Transation readTransaction(long transactionId);
    public LinkedList<Transation> getAllTransactions(long accountNumber);
    public void printTransaction(Transation transation);

}
