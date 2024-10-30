import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUser {

    private static final String DB_URL = "jdbc:derby:HangmanDB;create=true";

    public static void main(String[] args) {
        // establish a connection to the database and prepare an insert statement
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

            // set the username and password
            statement.setString(1, "testUser");
            statement.setString(2, "password123");

            // execute the insert statement
            statement.executeUpdate();
            System.out.println("User inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace(); // print error details if any SQLException occurs
        }
    }
}
