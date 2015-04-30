package edu.nyu.pqs.connectFour;

import java.util.HashMap;

public class ComputerPlayerFactory implements PlayerFactory{
  public Player create(String name, CellColor cellColor){
    return new ComputerPlayer(name, cellColor);
  }
}
