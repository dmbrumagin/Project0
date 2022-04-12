package dev.brumagin.service;

import dev.brumagin.data.TransactionDAO;
import dev.brumagin.data.TransactionDAOPostgressImpl;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;
import dev.brumagin.utility.LinkedList;

import java.sql.Date;

public class TransactionServiceImpl implements TransactionService{
    static TransactionDAO transactionDAO = new TransactionDAOPostgressImpl();

    @Override
    public Transation createTransaction(long bankAccount, long destinationAccount, TransactionType transactionType, double amountOfTransaction, long epochTime) {
        Transation transation = new Transation(bankAccount,destinationAccount,amountOfTransaction,transactionType, epochTime);
        transation= transactionDAO.createTransaction(transation);
        return  transation;
    }

    @Override
    public Transation readTransaction(long transactionId) {
        return transactionDAO.readTransaction(transactionId);
    }

    @Override
    public LinkedList<Transation> getAllTransactions(long accountNumber){
        return transactionDAO.getAllTransactions(accountNumber);
    }

    public void printTransaction(Transation transation){
        if(transation.getTypeOfTransation()==TransactionType.TRANSFER)
            System.out.printf("Transaction: %s\tAccount: %d\tDestination Account: %d\tAmount of Transaction: $%.2f\tTime of Transaction: %tD %tT\n",
                    transation.getTypeOfTransation(),transation.getOriginAccount(),transation.getDestinationAccount(),transation.getAmountOfTransation(),new Date(transation.getEpochTime()),new Date(transation.getEpochTime()));
        else{
            System.out.printf("Transaction: %s\tAccount: %d\t----------------------\tAmount of Transaction: $%.2f\tTime of Transaction: %tD %tT\n",
                    transation.getTypeOfTransation(),transation.getOriginAccount(),transation.getAmountOfTransation(),new Date(transation.getEpochTime()),new Date(transation.getEpochTime()));
        }
    }
}
