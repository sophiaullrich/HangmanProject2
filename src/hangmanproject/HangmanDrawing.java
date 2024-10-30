package hangmanproject;

import javax.swing.*;
import java.awt.*;

public class HangmanDrawing extends JPanel {
    private int wrongGuesses = 0; // number of incorrect guesses

    // method to update the number of wrong guesses and repaint the panel
    public void updateDrawing(int wrongGuesses) {
        this.wrongGuesses = wrongGuesses; // set the wrong guesses
        repaint();  // repaint the panel to update the drawing
    }

    // reset the drawing for a new game
    public void resetDrawing() {
        wrongGuesses = 0; // reset wrong guesses
        repaint();  // reset and repaint the hangman
    }

    // draw the hangman based on the number of wrong guesses
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3));  // thicker lines

        // draw the gallows
        g2d.drawLine(75, 180, 75, 20);  // vertical post
        g2d.drawLine(75, 20, 125, 20);  // top beam
        g2d.drawLine(125, 20, 125, 40);  // rope

        // draw hangman parts based on the number of wrong guesses
        if (wrongGuesses >= 1) {
            g2d.drawOval(110, 40, 30, 30);  // head
        }
        if (wrongGuesses >= 2) {
            g2d.drawLine(125, 70, 125, 120);  // body
        }
        if (wrongGuesses >= 3) {
            g2d.drawLine(125, 90, 105, 110);  // left arm
        }
        if (wrongGuesses >= 4) {
            g2d.drawLine(125, 90, 145, 110);  // right arm
        }
        if (wrongGuesses >= 5) {
            g2d.drawLine(125, 120, 110, 150);  // left leg
        }
        if (wrongGuesses >= 6) {
            g2d.drawLine(125, 120, 140, 150);  // right leg
            g2d.drawString("game over", 50, 200);  // display "game over" message
        }
    }
}
