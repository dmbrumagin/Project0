package dev.brumagin.service;

public interface LoginService {

    boolean registerNewOnlineAccount();
    boolean login(String username);
    boolean login(String username,String password);
    boolean updatePassword(String username, String currentPassword);
    boolean closeOnlineAccount(String username, String password);

}
