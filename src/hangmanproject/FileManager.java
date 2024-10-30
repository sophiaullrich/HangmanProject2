package hangmanproject;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    // logger to record errors and other information
    private static final Logger LOGGER = Logger.getLogger(FileManager.class.getName());

    // reads all of the lines from a file using an inputstream
    public List<String> readLinesFromStream(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim()); // adds each line to the list after trimming
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "error reading file from stream", e);
            return Collections.emptyList();  // returns an empty list if an error occurs
        }
        return lines;
    }

    // writes a list of lines to a file
    public void writeLines(String filePath, List<String> lines) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (String line : lines) {
                pw.println(line);  // writes each line to the file
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "error writing to file: " + filePath, e);
        }
    }

    // method to append a single line to a file
    public void appendLine(String filePath, String line) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pw.println(line);  // writes the line to the file
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "error appending to file: " + filePath, e);
        }
    }

    // overwrites specific lines in a file
    public void overwriteLine(String filePath, int lineNumber, String newLine) {
        List<String> lines = readLinesFromStream(getClass().getClassLoader().getResourceAsStream(filePath));
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.set(lineNumber, newLine);  // updates the specific line
            writeLines(filePath, lines);  // writes all lines back to the file
        } else {
            LOGGER.log(Level.WARNING, "line number out of range: {0}", lineNumber);
        }
    }

    // deletes a specific line in a file
    public void deleteLine(String filePath, int lineNumber) {
        List<String> lines = readLinesFromStream(getClass().getClassLoader().getResourceAsStream(filePath));
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber);  // removes the specific line
            writeLines(filePath, lines);  // writes all lines back to the file
        } else {
            LOGGER.log(Level.WARNING, "line number out of range: {0}", lineNumber);
        }
    }
}
