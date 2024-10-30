import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {

    // jdbc url to create an embedded derby database named "hangmandb"
    private static final String DB_URL = "jdbc:derby:HangmanDB;create=true";

    public static void main(String[] args) {
        // create a connection to the database
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            // create the "users" table if it doesn't exist
            String createUsersTableSQL = "CREATE TABLE users (" +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                    "username VARCHAR(50) NOT NULL, " +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(createUsersTableSQL);
            System.out.println("users table created successfully.");

            // insert sample data into the "users" table
            insertUser(connection, "testUser1", "password123");
            insertUser(connection, "testUser2", "securepass456");

        } catch (SQLException e) {
            // if the table already exists, just insert the users
            if (e.getSQLState().equals("X0Y32")) {
                System.out.println("users table already exists.");
            } else {
                e.printStackTrace();
            }
        }
    }

    // method to insert a user into the "users" table
    private static void insertUser(Connection connection, String username, String password) {
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, username); // set the username
            pstmt.setString(2, password); // set the password
            pstmt.executeUpdate();
            System.out.println("user '" + username + "' added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
