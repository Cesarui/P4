//Bradley Jn-Baptiste

import java.util.Random;

public class Bot{
  private final String name;
  private final Random rng = new Random();

  private int attemptsInRoom = 0;
  private boolean solvedCurrentRoom = false;

// The code creates the bot opponent.
public Bot(String name){
  super(name);
}


//This code resets the bot state when moving to a new room.
public void resetForNewRoom(){
  attemptsInRoom = 0;
  solvedCurrentRoom = false;
}

public String attempt(Room room, String[] guessPool){
  if (solvedCurrentRoom){
    return getName() + " already solved Room " + room.getRoomNumber();
  }

  attemptsInRoom++;

  //This code picks a random guess
  String guess = guessPool[rng.nextInt(guessPool.length)];

  boolean correct = room.checkAnswer(guess);

  if (correct){
    solvedCurrentRoom = true;
    return getName() + " guessed \"" + guess + "\" and solved Room "
      + room.getRoomNumber() + " in " + attemptsInRoom + " attempts!";
      } else {
      numOfIncorrectGuesses++;
      return getName() + " guessed \"" + guess + "\" and got it wrong.";
    }
}

//The code returns true if the bot already solved the answer.
public boolean hasSolvedCurrentRoom(){
  return solvedCurrentRoom;
}

  public int getAttemptsInRoom(){
    return attemptsInRoom;
}
  


@Override
public String toString(){
  return "Bot(" + name + ")";
  }
}
