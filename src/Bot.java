//Bradley Jn-Baptiste

import java.util.Random;

public class Bot{
  private final String name;
  private final Random rng = new Random();

  private int attemptsInRoom = 0;
  private boolean finishedRoom = false;

// The code creates the bot opponent.
public Bot(String name){
  this.name = name;
}

/* 
  This code resets the bot for a new room.
  It also gets called when simulation moves to next room.
*/
public void resetForNewRoom(){
  attemptsInRoom = 0;
  finishedRoom = false;
}

//The code returns true if the bot already solved the answer.
public boolean hasFinishedRoom(){
  return finishedRoom;
}

//This code gets the name of this bot.
public String getName(){
  return name;
}

//This code tracks the number of attempts made in the current room.
public int getAttemptsInRoom(){
  return attemptsInRoom;
}

@Override
public String toString(){
  return "Bot(" + name + ")";
  }
}
