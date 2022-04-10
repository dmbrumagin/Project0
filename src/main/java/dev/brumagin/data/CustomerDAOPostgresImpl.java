package dev.brumagin.data;

import dev.brumagin.entity.Customer;
import dev.brumagin.utility.ConnectionUtility;

import java.sql.*;

public class CustomerDAOPostgresImpl implements CustomerDAO {


    @Override
    public Customer createCustomer(Customer customer) {

        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "insert into customer(first_name,last_name) values(?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            String customerId = rs.getString("customer_id");
            System.out.println(customerId);
            customer.setCustomerID(customerId);
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer getCustomerById(String customerId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from customer where customer_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customerId);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            Customer customer = new Customer(rs.getString("first_name"), rs.getString("last_name"));
            customer.setPassword(rs.getString("user_password"));
            customer.setUsername(rs.getString("username"));
            customer.setCustomerID(customerId);
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update customer set  first_name = ?, last_name = ? where customer_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getCustomerID());
            ps.execute();
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean deleteCustomer(String customerId) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "delete from customer where customer_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customerId);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Customer createLogin(Customer customer) {

        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update customer set username = ?,user_password = ? where customer_id = ?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getCustomerID());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return customer;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String getLogin(String username, String password) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from customer where username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            String customerID = rs.getString("customer_id");
            String passwordToCompare = rs.getString("user_password");
            System.out.println(passwordToCompare);
            System.out.println(password);
            if(passwordToCompare.equals(password))
                return customerID;
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public boolean getLogin(String username) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select * from customer where username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            String returnString = rs.getString("username");
            return username.equals(returnString);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Customer updateLogin(Customer customer) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update customer set  username = ?, user_password = ? where customer_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getUsername());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getCustomerID());
            ps.execute();
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteLogin(Customer customer) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update customer set username = null, user_password = null where customer_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerID());
            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}




