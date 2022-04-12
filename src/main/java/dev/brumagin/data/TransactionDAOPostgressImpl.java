package dev.brumagin.data;

import dev.brumagin.entity.*;
import dev.brumagin.utility.ConnectionUtility;
import dev.brumagin.utility.LinkedList;
import dev.brumagin.utility.LogLevel;
import dev.brumagin.utility.Logger;

import java.sql.*;

public class TransactionDAOPostgressImpl implements TransactionDAO{
    @Override
    public Transation createTransaction(Transation transation) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "insert into transaction_log(origin_account,destination_account,amount_of_transaction,type_of_transaction,time_of_transaction) values(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, transation.getOriginAccount());
            ps.setLong(2, transation.getDestinationAccount());
            ps.setDouble(3, transation.getAmountOfTransation());
            ps.setString(4, transation.getTypeOfTransation().toString());
            ps.setLong(5, transation.getEpochTime());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            long transactionId = rs.getLong("transaction_id");
            transation.setTransactionId(transactionId);
            return transation;
        } catch (SQLException e) {
            Logger.log("Transaction not found: "+transation, LogLevel.WARNING);
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public Transation readTransaction(long transactionId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from transaction_log where transaction_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, transactionId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            Transation transation = new Transation(rs.getLong("origin_account"),rs.getLong("destination_account"),
                    rs.getDouble("amount_of_transaction"), TransactionType.valueOf(rs.getString("type_of_transaction")),rs.getLong("time_of_transaction"));

            return transation;
        } catch (SQLException e) {
            Logger.log("Transaction Id not found: "+transactionId, LogLevel.WARNING);
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public Transation updateTransaction(Transation transation) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update transaction_log set  origin_account = ?, destination_account = ?,amount_of_transaction = ?,type_of_transaction=?, time_of_transaction=? where transaction_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, transation.getOriginAccount());
            ps.setLong(2, transation.getDestinationAccount());
            ps.setDouble(3, transation.getAmountOfTransation());
            ps.setString(4, transation.getTypeOfTransation().name());
            ps.setLong(5, transation.getEpochTime());
            ps.setLong(6, transation.getTransactionId());
            ps.execute();
            return transation;
        } catch (SQLException e) {
            Logger.log("Transaction not found: "+transation, LogLevel.WARNING);
            // e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteTransaction(long transactionId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "delete from transaction_log where transaction_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, transactionId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            Logger.log("Transaction Id not found: "+transactionId, LogLevel.WARNING);
          //  e.printStackTrace();
            return false;
        }
    }

    @Override
    public LinkedList<Transation> getAllTransactions(long accountId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from transaction_log where origin_account = ? or destination_account = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,accountId);
            ps.setLong(2,accountId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            LinkedList<Transation> transations = new LinkedList<>();
            while(rs.next()) {

                long transactionId = rs.getLong("transaction_id");
                long account = rs.getLong("origin_account");
                long destination = rs.getLong("destination_account");
                double amount = rs.getDouble("amount_of_transaction");
                String type = rs.getString("type_of_transaction");
                long epoch = rs.getLong("time_of_transaction");
                Transation transation = new Transation(account,destination,amount,TransactionType.valueOf(type),epoch);
                transations.add(transation);
            }

            return transations;
        } catch (SQLException e) {
            Logger.log("Account number not found: "+ accountId, LogLevel.WARNING);
           // e.printStackTrace();
            return null;
        }
    }


}
