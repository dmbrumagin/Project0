package dev.brumagin.service;

public class LoginServiceImpl implements LoginService{
    @Override
    public boolean registerNewOnlineAccount() {
        return false;
    }

    @Override
    public boolean login(String username) {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public boolean updatePassword(String username, String currentPassword) {
        return false;
    }

    @Override
    public boolean closeOnlineAccount(String username, String password) {
        return false;
    }
}
