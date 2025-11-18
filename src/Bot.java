//Bradley Jn-Baptiste

import java.util.Random;

public class Bot extends Player {
    private final Random rng = new Random();
    private int attemptsInRoom = 0;
    private boolean solvedCurrentRoom = false;
    private long startTime = 0;
    private long endTime = 0;
    private static final int timePerGuess = 5;


    // The code creates the bot opponent.
    public Bot(String name) {
        super(name); // Call Player constructor
        this.startTime = System.currentTimeMillis();
    }

    // This code resets the bot state when moving to a new room.
    public void resetForNewRoom() {
        attemptsInRoom = 0;
        solvedCurrentRoom = false;
    }

    public String attempt(Room room, String[] guessPool) {
        if (solvedCurrentRoom) {
            return getName() + " already solved Room " + room.getRoomNumber();
        }

        attemptsInRoom++;

        // This code picks a random guess
        String guess = guessPool[rng.nextInt(guessPool.length)];

        boolean correct = room.checkAnswer(guess);

        if (correct) {
            solvedCurrentRoom = true;
            String result = getName() + " guessed \"" + guess + "\" and solved Room "
                    + room.getRoomNumber() + " in " + attemptsInRoom + " attempts!";
            resetForNewRoom();
            return result;
        } else {
            numOfIncorrectGuesses++;
            return getName() + " guessed \"" + guess + "\" and got it wrong.";
        }
    }

    @Override
    public void finishGame() {
        this.endTime =  System.currentTimeMillis();
    }

    @Override
    public int getTotalTime() {
        long actualTime = (endTime - startTime) / 1000;
        return (int) actualTime+ (numOfIncorrectGuesses * timePerGuess);
    }

    @Override
    public String toString() {
        return "Bot(" + getName() + ")";
    }
}