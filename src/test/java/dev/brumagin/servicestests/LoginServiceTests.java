package dev.brumagin.servicestests;

import dev.brumagin.data.LoginCredentialDAO;
import dev.brumagin.data.LoginCredentialDAOPostgresImpl;
import dev.brumagin.entity.LoginCredential;
import org.junit.jupiter.api.Test;

public class LoginServiceTests {

    LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAOPostgresImpl();

    @Test
    void create_login_with_valid_security(){
        String username = "MuffinMan";
        String password = "Gingerbread127#";
        LoginCredential logon = new LoginCredential(username,password);
        LoginCredential createdLogon = loginCredentialDAO.createLogin(logon);

    }

}
