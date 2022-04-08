package dev.brumagin.data;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.Customer;
import dev.brumagin.utility.ConnectionUtility;
import java.sql.*;

public class BankAccountDAOPostgresImpl implements BankAccountDAO {
    static CustomerDAO customerDAO = new CustomerDAOPostgresImpl();
    @Override
    public BankAccount createAccount(BankAccount bankAccount) {
       try {
           Connection connection = ConnectionUtility.createConnection();
           String sql = "insert into account(account_balance,account_holder,secondary_account_holder) values(?,?,?);";
           PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           ps.setDouble(1,bankAccount.getAccountBalance());
           ps.setInt(2,bankAccount.getAccountHolder().getCustomerID());
           if(bankAccount.getJointAccountHolder()!=null)
           ps.setInt(3,bankAccount.getJointAccountHolder().getCustomerID());
           else{
               ps.setInt(3,0);
           }
           ps.execute();
           ResultSet rs = ps.getGeneratedKeys();

           rs.next();
           long bankAccountId = rs.getLong("account_id");
           bankAccount.setAccountNumber(bankAccountId);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return bankAccount;
    }

    @Override
    public BankAccount getAccountByNumber(long accountNumber) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from account where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,accountNumber);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();


            customerDAO = new CustomerDAOPostgresImpl();

            Customer customer = customerDAO.getCustomerById(rs.getInt("account_holder"));
            Customer joint = customerDAO.getCustomerById(rs.getInt("secondary_account_holder"));
            BankAccount account = new CheckingBankAccount(customer,joint,rs.getDouble("account_balance"));
            account.setAccountNumber(rs.getLong("account_id"));
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BankAccount updateAccount(BankAccount bankAccount) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update account set  account_balance = ?, account_holder = ?, secondary_account_holder = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1,bankAccount.getAccountBalance());
            ps.setInt(2,bankAccount.getAccountHolder().getCustomerID());
            if(bankAccount.getJointAccountHolder()!=null)
                ps.setInt(3,bankAccount.getJointAccountHolder().getCustomerID());
            else{
                ps.setInt(3,0);
            }
            ps.execute();
            return bankAccount;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteAccount(long accountNumber) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "delete from account where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,accountNumber);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
