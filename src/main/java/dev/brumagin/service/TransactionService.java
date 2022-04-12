package dev.brumagin.service;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;

import java.sql.Date;

public interface TransactionService {

    public Transation createTransaction(BankAccount bankAccount, BankAccount destinationAccount, TransactionType transactionType, double amountOfTransaction, long epochTime);


}
