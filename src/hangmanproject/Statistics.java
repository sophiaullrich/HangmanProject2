package hangmanproject;

public class Statistics {
    private int gamesPlayed; // total games played
    private int gamesWon;    // total games won
    private int gamesLost;   // total games lost

    // constructor initializes statistics
    public Statistics() {
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
    }

    // method to record a game outcome
    public void recordGame(boolean won) {
        gamesPlayed++; // increment games played
        if (won) {
            gamesWon++; // increment games won
        } else {
            gamesLost++; // increment games lost
        }
    }

    // getter for games played
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    // getter for games won
    public int getGamesWon() {
        return gamesWon;
    }

    // getter for games lost
    public int getGamesLost() {
        return gamesLost;
    }

    // method to display the current statistics
    public void displayStats() {
        System.out.println("Games Played: " + gamesPlayed);
        System.out.println("Games Won: " + gamesWon);
        System.out.println("Games Lost: " + gamesLost);
        System.out.println("Win Ratio: " + getWinRatio() + "%");
    }

    // method to calculate the win ratio
    private double getWinRatio() {
        if (gamesPlayed == 0) {
            return 0; // avoid division by zero
        }
        return ((double) gamesWon / gamesPlayed) * 100;
    }
}
