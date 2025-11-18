// Cesar Pimentel

public class Room {

    private final int roomNumber;
    private final String riddle;
    private final String answer;
    private boolean solved;
    private String currentHint;

    /**
     * Constructs a Room with a room number, riddle and it's answer.
     *
     * @param roomNumber the room number/order (e.g., 1..10)
     * @param riddle the riddle
     * @param answer the correct answer
     */
    public Room(int roomNumber, String riddle, String answer) {
        this.roomNumber = roomNumber;
        this.riddle = riddle;
        this.answer = answer;
        this.solved = false;
        this.currentHint = "";
    }

    public String getHint() {
        return currentHint;
    }

    public void setHint(String currentHint) {
        this.currentHint = currentHint;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRiddle() {
        return riddle;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isSolved() {
        return solved;
    }

    /**
     * Checks the player's guess.
     * If correct, marks the room as solved.
     *
     * @param guess the player's input
     * @return true if guess matches the answer
     */
    public boolean checkAnswer(String guess) {
        if (guess == null) return false;
        boolean correct = guess.trim().equalsIgnoreCase(answer);
        if (correct) solved = true;
        return correct;
    }

    /**
     * Resets the solved state, just in case for testing.
     */
    public void reset() {
        solved = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [solved=" + solved + "]";
    }
}
