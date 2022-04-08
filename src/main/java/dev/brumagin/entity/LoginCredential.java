package dev.brumagin.entity;

public class LoginCredential {
    private int loginID=0;
    private String username;
    private String password;

    public LoginCredential(){
        this.username = null;
        this.password = null;
    }

    public LoginCredential(String username,String password){
        this.username= username;
        this.password = password;

    }

    public int getLoginID() {       return loginID;    }

    public void setLoginID(int loginID) {        this.loginID = loginID;    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginCredential{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
