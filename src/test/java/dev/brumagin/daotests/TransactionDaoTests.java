package dev.brumagin.daotests;

import dev.brumagin.data.TransactionDAO;
import dev.brumagin.data.TransactionDAOPostgressImpl;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionDaoTests {

    static TransactionDAO transactionDAO = new TransactionDAOPostgressImpl();
    static Transation testTransaction;

    @Test
    @Order(1)
    void create_transaction(){
        Transation transation = new Transation(6,5,5.01, TransactionType.TRANSFER, System.currentTimeMillis());
        testTransaction = transactionDAO.createTransaction(transation);
        Assertions.assertNotEquals(0,testTransaction.getTransactionId());
    }

    @Test
    @Order(2)
    void read_transaction(){
        Transation transation = transactionDAO.readTransaction(testTransaction.getTransactionId());
        Assertions.assertEquals(testTransaction.getAmountOfTransation(),transation.getAmountOfTransation());
    }
    @Test
    @Order(3)
    void update_transaction(){
        Transation transation = new Transation(7,5,5.01, TransactionType.TRANSFER, System.currentTimeMillis());
        testTransaction= transactionDAO.updateTransaction(transation);
        Assertions.assertEquals(testTransaction.getOriginAccount(),transation.getOriginAccount());
    }

    @Test
    @Order(4)
    void delete_transaction(){
        boolean deleted = transactionDAO.deleteTransaction(testTransaction.getTransactionId());
        Assertions.assertTrue(deleted);
    }



}
