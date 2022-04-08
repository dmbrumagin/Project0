package dev.brumagin.data;

import dev.brumagin.entity.LoginCredential;

public interface LoginCredentialDAO {

    LoginCredential createLogin(LoginCredential loginCredential);
    LoginCredential getLogin(LoginCredential loginCredential);
    boolean getLogin(String username);
    LoginCredential updateLogin(LoginCredential loginCredential);
    boolean deleteLogin(LoginCredential loginCredential);

}
