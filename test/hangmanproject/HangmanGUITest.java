package hangmanproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class HangmanGUITest {

    @Test
    public void testInitialization() {
        // make sure the GUI initializes without errors
        HangmanGUI hangmanGUI = new HangmanGUI();
        assertNotNull("HangmanGUI instance should not be null upon initialization.", hangmanGUI);
    }

    @Test
    public void testPlayerNameInitialization() {
        // check if the player name defaults to "Player" when no input is given
        HangmanGUI hangmanGUI = new HangmanGUI();
        String playerName = hangmanGUI.getPlayerName();
        assertEquals("Player name should default to 'Player' if no input is provided.", "Player", playerName);
    }

    @Test
    public void testGameStart() {
        // start the game and check if it’s running
        HangmanGUI hangmanGUI = new HangmanGUI();
        hangmanGUI.startGame();
        assertTrue("The game should be running after startGame is called.", hangmanGUI.isGameRunning());
    }

    @Test
    public void testGameEnd() {
        // start and then end the game, check if it’s no longer running
        HangmanGUI hangmanGUI = new HangmanGUI();
        hangmanGUI.startGame();
        hangmanGUI.endGame();
        assertFalse("The game should not be running after endGame is called.", hangmanGUI.isGameRunning());
    }

    @Test
    public void testDisplayedWordInitialization() {
        // check that the displayed word label initializes with underscores
        HangmanGUI hangmanGUI = new HangmanGUI();
        hangmanGUI.startGame();
        
        // get the initial displayed word
        String displayedWord = hangmanGUI.getDisplayedWord();
        
        // verify the displayed word is initialized with underscores (indicating unguessed letters)
        assertTrue("Displayed word should initialize with underscores.", displayedWord.contains("_"));
    }

    @Test
public void testDisplayedWordUpdates() {
    // make sure the displayed word resets to underscores after starting a new game
    HangmanGUI hangmanGUI = new HangmanGUI();
    hangmanGUI.startGame();

    // get the length of the displayed word
    String initialDisplay = hangmanGUI.getDisplayedWord();
    int initialLength = initialDisplay.length();

    // start a new game, which should reset the displayed word
    hangmanGUI.startGame();

    // get the new displayed word and its length
    String updatedDisplay = hangmanGUI.getDisplayedWord();
    int updatedLength = updatedDisplay.length();

    // verify that the length of displayed word matches the expected word length (initial and updated should match)
    assertEquals("The displayed word length should match after resetting the game.", initialLength, updatedLength);

    // check that the displayed word is still initialized as underscores
    assertTrue("The displayed word should consist of underscores after starting a new game.", updatedDisplay.contains("_"));
}

}
