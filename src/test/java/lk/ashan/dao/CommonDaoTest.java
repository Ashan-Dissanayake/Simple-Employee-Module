package lk.ashan.dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CommonDaoTest {

    private static Connection connection;

    @BeforeAll
    static void setupDatabase() throws Exception {
        // Create in-memory H2 DB
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

        Statement stmt = connection.createStatement();
        stmt.execute("CREATE TABLE gender (id INT PRIMARY KEY, name VARCHAR(50))");
        stmt.execute("INSERT INTO gender (id, name) VALUES (1, 'Male'), (2, 'Female'),(3,'Other')");

        stmt.execute("CREATE TABLE employee (id INT PRIMARY KEY ,name VARCHAR(45),gender_id INT references gender (id))");
        stmt.execute("INSERT INTO employee (id,name,gender_id) VALUES ( 1,'Navo',2 )");
    }

    @AfterAll
    static void tearDown() throws Exception {
        connection.close();
    }

    @Test
    void testGet_successfulQuery() throws Exception {
        ResultSet rs;
        try (Statement stmt = connection.createStatement()) {
            rs = stmt.executeQuery("SELECT * FROM gender WHERE id=1");
            assertTrue(rs.next());
            assertEquals("Male", rs.getString("name"));
        }
    }

    @Test
    void testModify_insertRow() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            int rows = stmt.executeUpdate("INSERT INTO employee (id, name,gender_id) VALUES (2, 'Ashan',1)");
            assertEquals(1, rows);

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE id=2");
            assertTrue(rs.next());
            assertEquals("Ashan", rs.getString("name"));
        }
    }

    @Test
    void testModify_updateRow() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            int rows = stmt.executeUpdate("UPDATE employee SET name='Minu' WHERE id=1");
            assertEquals(1, rows);

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE id=1");
            assertTrue(rs.next());
            assertEquals("Minu", rs.getString("name"));
        }
    }

    @Test
    void testModify_deleteRow() throws Exception {
        try (Statement stmt = connection.createStatement()) {
            int rows = stmt.executeUpdate("DELETE FROM employee WHERE id=1");
            assertEquals(1, rows);

            ResultSet rs = stmt.executeQuery("SELECT * FROM employee WHERE id=1");
            assertFalse(rs.next());
        }
    }
}
