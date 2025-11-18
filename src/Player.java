//Gabriel Luciano
public class Player {
    String name;
    int numOfIncorrectGuesses;
    int secondsElapsed;
    int numOfHintsUsed;
    String riddleAnswer;
    private long startTime;
    private long endTime;

    /** Constructor for Player object, sets the number of incorrectGuesses,
     * secondsElapsed, and the number of hints used to zero, doing so makes a clean slate for the player
     * @param givenName is the name given by the user for the player, will be displayed if they win the game.
     */
    public Player (String givenName) {
        name = givenName;
        numOfIncorrectGuesses = 0;
        numOfHintsUsed = 0;
        secondsElapsed = 0;
        this.startTime = System.currentTimeMillis();
    }
    public void finishGame() {
        this.endTime = System.currentTimeMillis();
    }

    public int getTotalTime() {
        long actualTime = (endTime - startTime) / 1000;
        return (int) actualTime + secondsElapsed;
    }

    /** When the user clicks the "get hint" button, it will administer a time penalty
     * and reveal a letter of the correct answer at random, will increment the number of
     * hints used by 1.
     */
    public void getHint () {
        addTimePenalty(60);
        numOfHintsUsed++;
    }

    public void addTimePenalty (int seconds) {
        secondsElapsed += seconds;
    }

    /** Basic getter for the Players elapsed time statistic.
     * @return seconds elapsed during the game with time penalties accounted for
     */
    public int getSecondsElapsed() {
        return secondsElapsed;
    }

    /** Basic getter for the Players incorrect guesses statistic
     * @return number of incorrect guesses the player has accrued.
     */
    public int getNumOfIncorrectGuesses() {
        return numOfIncorrectGuesses;
    }

    public int getNumOfHintsUsed() {
        return numOfHintsUsed;
    }

    /** Basic getter for the Players name
     * @return the name of the Player
     */
    public String getName() {
        return name;
    }

    /** Organizes the players statistics including, name, time elapsed, number of incorrect guesses, and number of hints used.
     * @return a String of the Players statistics
     */
    @Override
    public String toString() {
        return "Player Name: " + name + "\n" +
                "Time Elapsed (including penalties): " + secondsElapsed + "\n" +
                "Number of incorrect guesses: " + numOfIncorrectGuesses + "\n" +
                "Number of hints used: " + numOfHintsUsed;
    }
}
