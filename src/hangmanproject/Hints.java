package hangmanproject;

public class Hints {
    // constructor is simplified since availableHints is no longer needed
    public Hints(StringBuilder displayedWord) {
        // no need to track available hints if they are unlimited
    }

    // uses a hint to reveal a letter in the word
    public boolean useHint(String currentWord, StringBuilder displayedWord) {
        // ensure that the length of 'currentWord' and 'displayedWord' match to prevent out-of-bounds access
        if (currentWord.length() != displayedWord.length()) {
            return false;
        }

        // iterate over the word and find the first hidden letter ('_') to reveal
        for (int i = 0; i < currentWord.length(); i++) {
            if (displayedWord.charAt(i) == '_') {
                displayedWord.setCharAt(i, currentWord.charAt(i)); // reveal the letter
                return true; // a hint was used, revealing one letter
            }
        }
        return false; // no more hidden letters to reveal
    }
}
