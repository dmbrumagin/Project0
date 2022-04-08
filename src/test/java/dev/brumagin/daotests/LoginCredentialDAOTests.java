package dev.brumagin.daotests;

import dev.brumagin.data.LoginCredentialDAO;
import dev.brumagin.data.LoginCredentialDAOPostgresImpl;
import dev.brumagin.entity.LoginCredential;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginCredentialDAOTests {

    public static LoginCredential testLogin;
    public static LoginCredentialDAO loginCredential = new LoginCredentialDAOPostgresImpl();

    @Test
    @Order(1)
    void create_login(){
        LoginCredential login = new LoginCredential("username4","passwordTest");
        LoginCredential localLogin = loginCredential.createLogin(login);
        testLogin = localLogin;
        Assertions.assertEquals(testLogin.getUsername(),login.getUsername());
    }


    @Test
    @Order(2)
    void get_login_from_credential(){
        LoginCredential login = loginCredential.getLogin(testLogin);
        Assertions.assertEquals(login.getUsername(),testLogin.getUsername());
        Assertions.assertEquals(login.getPassword(),testLogin.getPassword());
    }
    @Test
    @Order(3)
    void get_login_from_username(){
        boolean login = loginCredential.getLogin(testLogin.getUsername());
        Assertions.assertTrue(login);
    }

    @Test
    @Order(4)
    void update_login_from_credential(){
        testLogin.setPassword("passwordChanged");
        LoginCredential login = loginCredential.updateLogin(testLogin);
        Assertions.assertEquals("passwordChanged",login.getPassword());
    }
    @Test
    @Order(5)
    void delete_login_credentials(){
        boolean login = loginCredential.deleteLogin(testLogin);
        Assertions.assertTrue(login);
    }


}
