package dev.brumagin.data;

import dev.brumagin.entity.Customer;
import dev.brumagin.entity.TransactionType;
import dev.brumagin.entity.Transation;
import dev.brumagin.utility.ConnectionUtility;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }
}
