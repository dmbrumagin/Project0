package dev.brumagin.data;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.utility.ConnectionUtility;
import dev.brumagin.utility.LinkedList;

import java.sql.*;

public class BankAccountDAOPostgresImpl implements BankAccountDAO {
    @Override
    public BankAccount createAccount(BankAccount bankAccount) {
       try {
           Connection connection = ConnectionUtility.createConnection();
           String sql = "insert into account(account_balance,account_holder,secondary_account_holder) values(?,?,?);";
           PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           ps.setDouble(1,bankAccount.getAccountBalance());
           ps.setInt(2,bankAccount.getAccountHolder());
           if(bankAccount.getJointAccountHolder()!=0)
           ps.setInt(3,bankAccount.getJointAccountHolder());
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

    public LinkedList<BankAccount> getAllBankAccounts (int userId){
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from account where account_holder = ? or secondary_account_holder = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,userId);
            ps.setLong(2,userId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            LinkedList<BankAccount> accounts = new LinkedList<>();
            while(rs.next()) {


                int customer = rs.getInt("account_holder");
                int joint = rs.getInt("secondary_account_holder");
                BankAccount account = new CheckingBankAccount(customer, joint, rs.getDouble("account_balance"));
                account.setAccountNumber(rs.getLong("account_id"));
                accounts.add(account);
            }

            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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
            int customer = rs.getInt("account_holder");
            int joint = rs.getInt("secondary_account_holder");
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
            String sql = "update account set  account_balance = ?, account_holder = ?, secondary_account_holder = ? where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1,bankAccount.getAccountBalance());
            ps.setInt(2,bankAccount.getAccountHolder());
            if(bankAccount.getJointAccountHolder()!=0)
                ps.setInt(3,bankAccount.getJointAccountHolder());
            else{
                ps.setInt(3,0);
            }
            ps.setLong(4,bankAccount.getAccountNumber());
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
