package dev.brumagin.data;

import com.sun.xml.internal.bind.v2.TODO;
import dev.brumagin.entity.LoginCredential;
import dev.brumagin.utility.ConnectionUtility;
import java.sql.*;

public class LoginCredentialDAOPostgresImpl implements LoginCredentialDAO{
    @Override
    public LoginCredential createLogin(LoginCredential loginCredential) {

        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "insert into logincredential(username,user_password) values(?,?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,loginCredential.getUsername());
            ps.setString(2,loginCredential.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            loginCredential.setLoginID(rs.getInt("login_id"));
            return loginCredential;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int getLogin(LoginCredential loginCredential) {
        System.out.println(loginCredential);
        try {

            Connection connection = ConnectionUtility.createConnection();
            String sql = "select *  from logincredential where login_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,loginCredential.getLoginID());

            ResultSet rs = ps.executeQuery();
            rs.next();
            //TODO
            //LoginCredential login = new LoginCredential();
           // login.setUsername(rs.getString("username"));
            //login.setPassword(rs.getString("user_password"));
            return rs.getInt("user_id");
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public boolean getLogin(String username) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "select username from logincredential where username = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();
            return username.equals(rs.getString("username"));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LoginCredential updateLogin(LoginCredential loginCredential) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "update logincredential set  username = ?, user_password = ? where login_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,loginCredential.getUsername());
            ps.setString(2,loginCredential.getPassword());
            ps.setInt(3,loginCredential.getLoginID());
            ps.execute();
            return loginCredential;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteLogin(LoginCredential loginCredential) {
        try {
            Connection connection = ConnectionUtility.createConnection();
            String sql = "delete from logincredential where login_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,loginCredential.getLoginID());
            ps.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
