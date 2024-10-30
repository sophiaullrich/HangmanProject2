package hangmanproject;

public class HangmanGame extends Game {
    private final WordList wordList;
    private Player player;  // instance of Player to manage attempts and guesses
    private String currentWord;
    private StringBuilder displayedWord;
    private HangmanDrawing hangmanDrawing;
    private Hints hints;  // instance of Hints for managing hints
    private boolean gameWon;  // track if the game is won

    // constructor that initializes the game components
    public HangmanGame() {
        wordList = new WordList("words.txt");
        hangmanDrawing = new HangmanDrawing();
        player = new Player(); // initialize the player
        hints = new Hints(displayedWord); // initialize hints with displayedWord
        resetGame();  // initialize game state
    }

    // implement the required abstract method from Game
    @Override
    public void start(Statistics statistics) {
        System.out.println("Game started.");
        resetGame();  // start a new game
    }

    // resets the game state for a new round
    public void resetGame() {
        currentWord = wordList.getRandomWord();  // get a new word from the word list
        displayedWord = new StringBuilder("_".repeat(currentWord.length()));  // display underscores for the letters
        player = new Player();  // reset player for new game
        gameOver = false;  // mark the game as not over
        gameWon = false;  // reset the win status
        hangmanDrawing.resetDrawing();  // reset the hangman drawing for a new game

        System.out.println("New word: " + currentWord); // debugging: print the current word
        System.out.println("Displayed word: " + displayedWord); // debugging: initial state of displayedWord
    }

    // handle player's guess and update the game state
    public void handleGuess(char guess) {
        if (player.hasGuessed(guess)) {
            System.out.println("Letter '" + guess + "' has already been guessed. Try a different letter.");
            return;
        }

        player.addGuessedLetter(guess);
        boolean correct = false;

        // update displayed word if the guess is correct
        for (int i = 0; i < currentWord.length(); i++) {
            if (Character.toLowerCase(currentWord.charAt(i)) == Character.toLowerCase(guess)) {
                displayedWord.setCharAt(i, currentWord.charAt(i));
                correct = true;  // mark guess as correct
            }
        }

        // if the guess is incorrect, decrease attempts
        if (!correct) {
            System.out.println("Incorrect guess: " + guess);
            player.decreaseAttempts(); // decrease attempts if the guess is incorrect
            int wrongGuesses = Player.getMaxAttempts() - player.getAttemptsLeft(); // calculate wrong guesses
            hangmanDrawing.updateDrawing(wrongGuesses);  // update the drawing
            System.out.println("Attempts left after incorrect guess: " + player.getAttemptsLeft());
        } else {
            System.out.println("Correct guess: " + guess);
            System.out.println("Updated displayed word: " + displayedWord);
        }

        // check if the player has won or lost
        if (!displayedWord.toString().contains("_")) {
            gameWon = true;
            gameOver = true;
            System.out.println("Game won!");
        }
        if (player.getAttemptsLeft() == 0) {
            gameOver = true;
            System.out.println("Game over! No attempts left.");
        }
    }

    // provide a hint using the Hints class
    public boolean useHint() {
        return hints.useHint(currentWord, displayedWord);
    }

    // get the player object for access in GUI
    public Player getPlayer() {
        return player;
    }

    // return the displayed word
    public String getDisplayedWord() {
        return displayedWord.toString();
    }

    // return the current word
    public String getWord() {
        return currentWord;
    }

    // check if the game has been won
    public boolean isWon() {
        return gameWon;
    }

    // end the game by setting gameOver to true
    public void endGame() {
        gameOver = true;
    }

    // add a new word to the word list
    public void addWordToWordList(String newWord) {
        wordList.addWord(newWord); // add the word to the word list
    }

    // update a word in the word list
    public void updateWordInWordList(int index, String newWord) {
        wordList.updateWordInList(index, newWord); // update the word in the word list
    }

    // remove a word from the word list
    public void removeWordFromWordList(int index) {
        wordList.removeWordFromList(index); // remove the word from the word list
    }
}
