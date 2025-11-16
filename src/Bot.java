//Bradley Jn-Baptiste

import java.util.Random;

public class Bot{
  private final String name;
  private final Random rng = new Random();

  private int attemptsInRoom = 0;
  private boolean solvedCurrentRoom = false;

// The code creates the bot opponent.
public Bot(String name){
  this.name = name;
}


//This code resets the bot state when moving to a new room.
public void resetForNewRoom(){
  attemptsInRoom = 0;
  solvedCurrentRoom = false;
}

//The code returns true if the bot already solved the answer.
public boolean hasSolvedCurrentRoom(){
  return solvedCurrentRoom;
}

  public int getAttemptsInRoom(){
    return attemptsInRoom;
}
  
//This code gets the name of this bot.
public String getName(){
  return name;
}

@Override
public String toString(){
  return "Bot(" + name + ")";
  }
}
