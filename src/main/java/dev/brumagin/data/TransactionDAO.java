package dev.brumagin.data;

import dev.brumagin.entity.Transation;
import dev.brumagin.utility.LinkedList;
import sun.awt.image.ImageWatched;

public interface TransactionDAO {
    Transation createTransaction(Transation transation);
    Transation readTransaction(long transactionId);
    Transation updateTransaction(Transation transation);
    boolean deleteTransaction(long transactionId);
    LinkedList<Transation> getAllTransactions(long accountId);
}
