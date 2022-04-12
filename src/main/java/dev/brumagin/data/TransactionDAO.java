package dev.brumagin.data;

import dev.brumagin.entity.Transation;

public interface TransactionDAO {
    Transation createTransaction(Transation transation);
    Transation readTransaction(long transactionId);
    Transation updateTransaction(Transation transation);
    boolean deleteTransaction(long transactionId);
}
