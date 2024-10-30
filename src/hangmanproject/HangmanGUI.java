package hangmanproject;

import javax.swing.*;
import java.awt.*;

public class HangmanGUI {
    private JFrame frame;
    private JTextField guessInput;
    private JLabel wordLabel;
    private JButton submitButton;
    private JButton hintButton;
    private JButton exitButton;
    private JButton manageWordListButton; 
    private JLabel messageLabel;
    private JLabel hintsLabel;
    private JLabel nameLabel;
    private JLabel statsLabel;
    private HangmanGame game;
    private HangmanDrawing hangmanDrawing;
    private Statistics statistics;
    private Name playerName;

    public HangmanGUI() {
        // prompt the user to enter their nickname
        String playerNameInput = JOptionPane.showInputDialog(null, "Please enter your nickname:",
                "Welcome to Hangman!", JOptionPane.QUESTION_MESSAGE);

        // set a default player name if none is provided
        if (playerNameInput == null || playerNameInput.trim().isEmpty()) {
            playerNameInput = "Player";
        }

        // initialize game, statistics, and player name
        game = new HangmanGame();
        statistics = new Statistics();
        playerName = new Name(playerNameInput);

        // create the main game window
        frame = new JFrame("Hangman Game");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(255, 240, 245));
        frame.setLayout(null);

        // display the player's name
        nameLabel = new JLabel("Player: " + playerName.getName());
        nameLabel.setBounds(50, 10, 200, 30);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        frame.add(nameLabel);

        // display the word to guess
        wordLabel = new JLabel(formatDisplayedWord(game.getDisplayedWord()));
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 28));
        wordLabel.setBounds(50, 50, 600, 50);
        wordLabel.setForeground(new Color(255, 105, 180));
        frame.add(wordLabel);

        // input field for guesses
        guessInput = new JTextField(1);
        guessInput.setBounds(50, 150, 50, 40);
        guessInput.setFont(new Font("SansSerif", Font.PLAIN, 24));
        guessInput.setHorizontalAlignment(JTextField.CENTER);
        frame.add(guessInput);

        // button to submit guesses
        submitButton = new JButton("Submit Guess");
        submitButton.setBounds(150, 150, 150, 40);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        submitButton.setBackground(new Color(255, 105, 180));
        submitButton.setForeground(Color.WHITE);
        frame.add(submitButton);

        // button to use a hint
        hintButton = new JButton("Use Hint");
        hintButton.setBounds(320, 150, 100, 40);
        hintButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        hintButton.setBackground(new Color(255, 182, 193));
        hintButton.setForeground(Color.WHITE);
        frame.add(hintButton);

        // button to exit the game
        exitButton = new JButton("Exit Game");
        exitButton.setBounds(450, 150, 120, 40);
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        exitButton.setBackground(new Color(255, 182, 193));
        exitButton.setForeground(Color.WHITE);
        frame.add(exitButton);

        // button to manage the word list
        manageWordListButton = new JButton("Manage Word List");
        manageWordListButton.setBounds(590, 150, 180, 40);
        manageWordListButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        manageWordListButton.setBackground(new Color(255, 182, 193));
        manageWordListButton.setForeground(Color.WHITE);
        frame.add(manageWordListButton);

        // label to display available hints
        hintsLabel = new JLabel("Hints available: Unlimited");
        hintsLabel.setBounds(50, 100, 300, 30);
        frame.add(hintsLabel);

        // label to show messages to the player
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Serif", Font.ITALIC, 18));
        messageLabel.setBounds(50, 200, 600, 30);
        messageLabel.setForeground(new Color(255, 0, 0));
        frame.add(messageLabel);

        // label to display game statistics
        statsLabel = new JLabel("Games Played: 0 | Wins: 0 | Losses: 0");
        statsLabel.setBounds(50, 250, 600, 30);
        frame.add(statsLabel);

        // panel to draw the hangman
        hangmanDrawing = new HangmanDrawing();
        hangmanDrawing.setBounds(400, 300, 300, 200);
        hangmanDrawing.setBackground(new Color(255, 240, 245));
        hangmanDrawing.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        frame.add(hangmanDrawing);

        // add action listeners to buttons
        submitButton.addActionListener(e -> handleGuessInput());
        hintButton.addActionListener(e -> handleHint());
        exitButton.addActionListener(e -> System.exit(0));
        manageWordListButton.addActionListener(e -> openWordListManager());

        frame.setVisible(true);
    }

    // handles input from the guess field
    private void handleGuessInput() {
        String guess = guessInput.getText().trim().toLowerCase();
        if (!guess.isEmpty() && guess.length() == 1) {
            if (game.getPlayer().hasGuessed(guess.charAt(0))) {
                messageLabel.setText("You've already guessed '" + guess + "'. Try a different letter.");
            } else {
                game.handleGuess(guess.charAt(0));
                wordLabel.setText(formatDisplayedWord(game.getDisplayedWord()));

                // calculate wrong guesses based on attempts left
                int wrongGuesses = Player.getMaxAttempts() - game.getPlayer().getAttemptsLeft();
                hangmanDrawing.updateDrawing(wrongGuesses);

                if (game.isGameOver()) {
                    handleGameEnd();
                }

                // update statistics and hint label
                statsLabel.setText("Games Played: " + statistics.getGamesPlayed() +
                        " | Wins: " + statistics.getGamesWon() + " | Losses: " + statistics.getGamesLost());
                hintsLabel.setText("Hints available: Unlimited");
                messageLabel.setText("");
            }
        } else {
            messageLabel.setText("Please enter a valid letter.");
        }
        guessInput.setText("");
    }

    // handles the use of a hint
    private void handleHint() {
        if (!game.isGameOver()) {
            if (game.useHint()) {
                wordLabel.setText(formatDisplayedWord(game.getDisplayedWord()));
                hintsLabel.setText("Hints available: Unlimited");
                messageLabel.setText("");
            } else {
                messageLabel.setText("No letters left to reveal.");
            }
        }
    }

    // opens the word list management options
    private void openWordListManager() {
        String[] options = {"Add Word", "Update Word", "Remove Word", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Select an action:",
                "Manage Word List",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        switch (choice) {
            case 0:
                addWord();
                break;
            case 1:
                updateWord();
                break;
            case 2:
                removeWord();
                break;
            default:
                System.out.println("Word list management cancelled.");
                break;
        }
    }

    // adds a new word to the word list
    private void addWord() {
        String newWord = JOptionPane.showInputDialog(frame, "Enter the new word:");
        if (newWord != null && !newWord.trim().isEmpty()) {
            game.addWordToWordList(newWord.trim().toLowerCase());
            JOptionPane.showMessageDialog(frame, "Word added successfully!");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid word. Please try again.");
        }
    }

    // updates an existing word in the word list
    private void updateWord() {
        String indexStr = JOptionPane.showInputDialog(frame, "Enter the line number to update:");
        String newWord = JOptionPane.showInputDialog(frame, "Enter the new word:");
        try {
            int index = Integer.parseInt(indexStr);
            if (newWord != null && !newWord.trim().isEmpty()) {
                game.updateWordInWordList(index, newWord.trim().toLowerCase());
                JOptionPane.showMessageDialog(frame, "Word updated successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid word. Please try again.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid line number. Please try again.");
        }
    }

    // removes a word from the word list
    private void removeWord() {
        String indexStr = JOptionPane.showInputDialog(frame, "Enter the line number to remove:");
        try {
            int index = Integer.parseInt(indexStr);
            game.removeWordFromWordList(index);
            JOptionPane.showMessageDialog(frame, "Word removed successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid line number. Please try again.");
        }
    }

    // formats the displayed word with spaces between each letter
    private String formatDisplayedWord(String displayedWord) {
        return displayedWord.replace("", " ").trim();
    }

    // handles the end of the game and prompts the user to play again or exit
    private void handleGameEnd() {
        String message;
        if (game.isWon()) {
            message = "Good job, " + playerName.getName() + "! You won! Would you like to play again?";
            statistics.recordGame(true);
        } else {
            message = "Good try, " + playerName.getName() + "! The word was: " + game.getWord() + ". Would you like to play again?";
            statistics.recordGame(false);
        }

        int option = JOptionPane.showOptionDialog(
                frame,
                message,
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new String[]{"Play Again", "Exit"},
                "Play Again"
        );

        // if the user wants to play again, reset the game
        if (option == JOptionPane.YES_OPTION) {
            game.resetGame();
            wordLabel.setText(formatDisplayedWord(game.getDisplayedWord())); // ensure the displayed word updates
            hangmanDrawing.resetDrawing(); // reset drawing for the new game
            submitButton.setEnabled(true);
            hintButton.setEnabled(true);
            messageLabel.setText("");
        } else {
            System.exit(0); // exit the game if the user chooses to
        }
    }

    // simple method to get the player's name
    public String getPlayerName() {
        return playerName.getName();
    }

    // starts the game for testing purposes
    public void startGame() {
        game.resetGame();
    }

    // ends the game for testing purposes
    public void endGame() {
        game.endGame(); // assuming there's a method to end the game in HangmanGame
    }

    // checks if the game is running
    public boolean isGameRunning() {
        return !game.isGameOver();
    }

    // gets the displayed word from the label (for testing)
    public String getDisplayedWord() {
        return wordLabel.getText();
    }

    public static void main(String[] args) {
        new HangmanGUI();
    }
}
