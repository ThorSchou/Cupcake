package app.persistence;

import app.entities.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "cupcake";


    private static ConnectionPool connectionPool;
    private static UserMapper userMapper;

    @BeforeAll
    public static void setupDatabase() {
        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);
        userMapper = new UserMapper(connectionPool);

        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS test.users");
            stmt.execute("""
                CREATE TABLE test.users (
                    user_id SERIAL PRIMARY KEY,
                    email VARCHAR(255) NOT NULL,
                    user_password VARCHAR(255) NOT NULL,
                    balance INT NOT NULL,
                    is_admin BOOLEAN NOT NULL
                )
            """);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @BeforeEach
    void resetTable() {
        try (Connection conn = connectionPool.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM test.users");
        } catch (Exception e) {
            fail("Error. Reset failed: " + e.getMessage());
        }

    }

    @Test
    void updateUserBalanceTest() {
        userMapper.createUser("test@example.com", "password123");
        User user = userMapper.getUserById(1);
        assertEquals(0, user.getBalance());
        int newBalance = 10;
        userMapper.updateUserBalance(user, newBalance);
        assertEquals(10, user.getBalance());
    }

    @Test
    void createUserTest() {
        String email = "test@example.com";
        String password = "password123";
        userMapper.createUser(email, password);
        User user = userMapper.getUserByEmail("test@example.com");

        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());

    }

    @Test
    void getUserByEmailTest() {
        userMapper.createUser("test@example.com", "password123");
        User user = userMapper.getUserByEmail("test@example.com");

        assertEquals(user.getEmail(), "test@example.com");

    }

    @Test
    void updateUserBalance() {
    }

    @Test
    void getUserByIdTest() {
        userMapper.createUser("test@example.com", "password123");
        User user = userMapper.getUserById(1);

        assertEquals(1, user.getId());

    }

    @Test
    void getAllUsers() {
        userMapper.createUser("test@example.com", "");
        userMapper.createUser("test2@example.com", "");
        List <User> users = userMapper.getAllUsers();

        assertEquals(2, users.size());
    }
}
