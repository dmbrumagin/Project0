package dev.brumagin.data;

import dev.brumagin.entity.BankAccount;
import dev.brumagin.entity.CheckingBankAccount;
import dev.brumagin.entity.SavingsBankAccount;
import dev.brumagin.utility.ConnectionUtility;
import dev.brumagin.utility.LinkedList;
import dev.brumagin.utility.LogLevel;
import dev.brumagin.utility.Logger;

import java.sql.*;

public class BankAccountDAOPostgresImpl implements BankAccountDAO {
    @Override
    public BankAccount createAccount(BankAccount bankAccount,char accountType) {
       try {
           Connection connection = ConnectionUtility.createConnection();
           String sql = "insert into account(account_balance,account_type,account_holder,secondary_account_holder) values(?,?,?,?);";
           PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           ps.setDouble(1,bankAccount.getAccountBalance());
           if(accountType=='c') {
               ps.setString(2, "Checking");
               bankAccount.setAccountType("Checking");
           }
           else if(accountType=='s') {
               ps.setString(2, "Savings");
               bankAccount.setAccountType("Savings");
           }
           ps.setString(3,bankAccount.getAccountHolder());
           ps.setString(4,bankAccount.getJointAccountHolder());
           ps.execute();
           ResultSet rs = ps.getGeneratedKeys();

           rs.next();
           long bankAccountId = rs.getLong("account_id");
           bankAccount.setAccountNumber(bankAccountId);

        } catch (SQLException e) {
           Logger.log("Issue with Bank Account: "+bankAccount.getAccountHolder() + " Account type: "+accountType,LogLevel.WARNING);
            //e.printStackTrace();
            return null;
        }

        return bankAccount;
    }

    public LinkedList<BankAccount> getAllBankAccounts (String userId){
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from account where account_holder = ? or secondary_account_holder = ? order by account_id;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,userId);
            ps.setString(2,userId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            LinkedList<BankAccount> accounts = new LinkedList<>();
            while(rs.next()) {

                BankAccount account = null;
                String customer = rs.getString("account_holder");
                String joint = rs.getString("secondary_account_holder");
                if(rs.getString("account_type").equals("Checking"))
                    account = new CheckingBankAccount(customer, joint);
                else if(rs.getString("account_type").equals("Savings"))
                    account = new SavingsBankAccount(customer, joint);
                account.setAccountBalance(rs.getDouble("account_balance"));
                account.setAccountNumber(rs.getLong("account_id"));
                account.setAccountType(rs.getString("account_type"));
                accounts.add(account);
            }

            return accounts;
        } catch (SQLException e) {
            Logger.log("Username not found: "+userId,LogLevel.WARNING);
            //e.printStackTrace();
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
            String customer = rs.getString("account_holder");
            String joint = rs.getString("secondary_account_holder");
            BankAccount account = new CheckingBankAccount(customer,joint);
            account.setAccountBalance(rs.getDouble("account_balance"));
            account.setAccountNumber(rs.getLong("account_id"));
            account.setAccountType(rs.getString("account_type"));
            return account;
        } catch (SQLException e) {
            Logger.log("Account Number not found: "+accountNumber,LogLevel.WARNING);
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public BankAccount updateAccount(BankAccount bankAccount) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update account set  account_balance = ?, account_holder = ?, secondary_account_holder = ? ,account_type = ? where account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1,bankAccount.getAccountBalance());
            ps.setString(2,bankAccount.getAccountHolder());
            ps.setString(3,bankAccount.getJointAccountHolder());

            ps.setString(4,bankAccount.getAccountType());
            ps.setLong(5,bankAccount.getAccountNumber());

            ps.execute();
            return bankAccount;
        } catch (SQLException e) {
            Logger.log("Bank Account Id not found: "+bankAccount.getAccountNumber(),LogLevel.WARNING);
            //e.printStackTrace();
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
            Logger.log("Account Number not found: "+accountNumber,LogLevel.WARNING);
            //e.printStackTrace();
            return false;
        }
    }
}
