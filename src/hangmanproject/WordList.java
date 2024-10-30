package hangmanproject;

import java.io.InputStream;
import java.util.*;

public class WordList {
    private List<String> words;  // list of words loaded from the file
    private Map<Integer, String> wordMap; // a map to manage words by line number
    private final FileManager fileManager;  // file manager to handle file operations
    private final String filePath; // path to the word list file

    // constructor that loads the word list from a file
    public WordList(String filePath) {
        this.filePath = filePath;
        fileManager = new FileManager();

        // load words using a resource stream (for bundled resources in JAR)
        InputStream inputStream = getClass().getResourceAsStream(filePath);

        // debug message to verify if the file is found
        if (inputStream == null) {
            System.out.println("Resource file not found: " + filePath);
            throw new IllegalStateException("The word list is empty or could not be loaded. Please check the words.txt file.");
        } else {
            System.out.println("Resource file found: " + filePath);
        }

        // read words from the input stream and store them in the list
        words = fileManager.readLinesFromStream(inputStream);
        wordMap = new HashMap<>();
        for (int i = 0; i < words.size(); i++) {
            wordMap.put(i, words.get(i)); // map each word to its line number
        }

        // check if the word list is empty or didn't load
        if (words.isEmpty()) {
            throw new IllegalStateException("The word list is empty or could not be loaded. Please check the words.txt file.");
        }
    }

    // method to get a random word from the list
    public String getRandomWord() {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    // adds a new word to the list and writes changes to the file
    public void addWord(String newWord) {
        words.add(newWord);
        wordMap.put(words.size() - 1, newWord); // update the map with the new word
        fileManager.appendLine(filePath, newWord); // append the new word to the file
        System.out.println("New word added: " + newWord);
    }

    // updates a specific word in the list and writes changes to the file
    public void updateWordInList(int lineNumber, String newWord) {
        if (lineNumber >= 0 && lineNumber < words.size()) {
            words.set(lineNumber, newWord); // update the word in the list
            wordMap.put(lineNumber, newWord); // update the map with the new word
            fileManager.writeLines(filePath, words); // write the updated list back to the file
            System.out.println("Word at line " + lineNumber + " has been updated to: " + newWord);
        } else {
            System.out.println("Index out of range. Cannot edit word.");
        }
    }

    // removes a specific word from the list and writes changes to the file
    public void removeWordFromList(int lineNumber) {
        if (lineNumber >= 0 && lineNumber < words.size()) {
            words.remove(lineNumber); // remove the word from the list
            wordMap.remove(lineNumber); // remove the word from the map
            fileManager.writeLines(filePath, words); // write the updated list back to the file
            System.out.println("Word at line " + lineNumber + " has been removed.");
        } else {
            System.out.println("Index out of range. Cannot remove word.");
        }
    }

    // returns a copy of the current word list
    public List<String> getWords() {
        return new ArrayList<>(words); // return a copy to avoid modifying the original list
    }
}
