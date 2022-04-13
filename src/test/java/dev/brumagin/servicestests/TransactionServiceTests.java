package dev.brumagin.servicestests;

import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;
import dev.brumagin.service.TransactionService;
import dev.brumagin.service.TransactionServiceImpl;
import dev.brumagin.utility.LinkedList;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TransactionServiceTests {

    static TransactionService transactionService = new TransactionServiceImpl();
    static Transation testTransation;
    @Test
    @Order(1)
    void create_transaction(){
        Transation transation = transactionService.createTransaction(2,2,TransactionType.TRANSFER,5.01,System.currentTimeMillis());
        testTransation = transation;
        System.out.println(transation);
        Assertions.assertNotEquals(0,transation.getTransactionId());
    }

    @Test
    @Order(2)
    void read_transaction(){
        Transation transation2 = transactionService.readTransaction(testTransation.getTransactionId());
        Assertions.assertEquals(transation2.getTransactionId(),testTransation.getTransactionId());

    }

    @Test
    void print_transaction(){
        Transation transation = new Transation(7,5,5.01, TransactionType.TRANSFER, System.currentTimeMillis());
        transactionService.printTransaction(transation);
        transation = new Transation(7,0,5.01, TransactionType.DEPOSIT, System.currentTimeMillis());
        transactionService.printTransaction(transation);
        transation = new Transation(7,0,5.01, TransactionType.WITHDRAWAL, System.currentTimeMillis());
        transactionService.printTransaction(transation);
    }

    @Test
    void print_all_transactions(){
        LinkedList<Transation> transactions = transactionService.getAllTransactions(5);

        for(Transation t : transactions){
            transactionService.printTransaction(t);
        }
    }
}
