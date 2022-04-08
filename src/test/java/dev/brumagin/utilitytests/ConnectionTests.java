package dev.brumagin.utilitytests;

import dev.brumagin.utility.ConnectionUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTests {
    @Test
    void connection_established(){
        Connection connection = ConnectionUtility.createConnection();
        Assertions.assertNotNull(connection);
    }
}
