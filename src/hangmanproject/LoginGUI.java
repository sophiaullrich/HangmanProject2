import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import hangmanproject.HangmanGUI;

public class LoginGUI {
    // jdbc url for derby database
    private static final String DB_URL = "jdbc:derby:C:/Users/sophi/OneDrive/Desktop/HangmanProject/databases/HangmanDB;create=true";

    public LoginGUI() {
        // set up the login frame with a pink and white theme
        JFrame loginFrame = new JFrame("Cute Hangman Login");
        loginFrame.setSize(400, 250);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.getContentPane().setBackground(new Color(255, 223, 243)); // light pink background
        loginFrame.setLayout(null);

        // username label and field with cute theme
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 100, 30);
        usernameLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        usernameLabel.setForeground(new Color(255, 105, 180)); // hot pink text
        loginFrame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 30, 150, 30);
        usernameField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 3)); // light pink border
        loginFrame.add(usernameField);

        // password label and field with cute theme
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 100, 30);
        passwordLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        passwordLabel.setForeground(new Color(255, 105, 180)); // hot pink text
        loginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 70, 150, 30);
        passwordField.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 3)); // light pink border
        loginFrame.add(passwordField);

        // login button with pink and white theme
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 120, 100, 30);
        loginButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        loginButton.setBackground(new Color(255, 182, 193)); // light pink background
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2)); // hot pink border
        loginFrame.add(loginButton);

        // register button for new users with the cute theme
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(200, 120, 100, 30);
        registerButton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        registerButton.setBackground(new Color(255, 182, 193)); // light pink background
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createLineBorder(new Color(255, 105, 180), 2)); // hot pink border
        loginFrame.add(registerButton);

        // action listener for login button
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim(); // ensure no extra spaces
            String password = new String(passwordField.getPassword()).trim();

            if (validateCredentials(username, password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful! Welcome, " + username + "!");
                loginFrame.dispose();  // close the login window

                // launch the HangmanGUI game after successful login
                new HangmanGUI();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid login credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // action listener for register button
        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();  // trim spaces
            String password = new String(passwordField.getPassword()).trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(loginFrame, "Registration successful! You can now log in.");
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Registration failed. Try a different username.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.setVisible(true);
    }

    // validate the user credentials by checking the database
    private boolean validateCredentials(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();  // return true if the user exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // register a new user by inserting into the database
    private boolean registerUser(String username, String password) {
        String insertSQL = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, username);
            statement.setString(2, password);
            int rowsInserted = statement.executeUpdate();

            // return true if a row was successfully inserted
            return rowsInserted > 0;
        } catch (SQLException e) {
            // print the stack trace if registration fails (e.g., username might already exist)
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        new LoginGUI();  // start the login screen
    }
}
