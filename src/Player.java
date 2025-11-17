//Gabriel Luciano
public class Player {
    String name;
    int numOfIncorrectGuesses;
    int secondsElapsed;
    int numOfHintsUsed;
    String riddleAnswer;

    /** Constructor for Player object, sets the number of incorrectGuesses,
     * secondsElapsed, and the number of hints used to zero, doing so makes a clean slate for the player
     * @param givenName is the name given by the user for the player, will be displayed if they win the game.
     */
    public Player (String givenName) {
        name = givenName;
        numOfIncorrectGuesses = 0;
        numOfHintsUsed = 0;
        secondsElapsed = 0;
    }

    /** Takes in the correct answer to the current riddle and stores it locally.
     * @param givenRiddleAnswer is the correct answer to the current riddle, this is not know by the user
     * and is used to check the users guess for correctness
     */
    private void getAnswer(String givenRiddleAnswer) {
        riddleAnswer = givenRiddleAnswer;
    }
    /** Takes in users guess as a string and compares it to the answer of the riddle, returns typical boolean outputs
     * @param givenUserGuess is the users guess
     * @return returns true if the users guess matches the answer for the riddle.
     */
    public boolean makeGuess(String givenUserGuess) {
        return givenUserGuess.equals(riddleAnswer);
    }

    /** When the user clicks the "get hint" button, it will administer a time penalty
     * and reveal a letter of the correct answer at random, will increment the number of
     * hints used by 1.
     */
    public void getHint () {
        secondsElapsed += 60;
        numOfHintsUsed++;
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
