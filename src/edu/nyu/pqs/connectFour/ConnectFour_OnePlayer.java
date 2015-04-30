package edu.nyu.pqs.connectFour;

import java.util.HashMap;

public class ConnectFour_OnePlayer extends ConnectFour {

  public ConnectFour_OnePlayer(HashMap<String, String> options) {
    super(options);
  }

  @Override
  protected void createPlayers() {
    HumanPlayerFactory humanPlayerFactory = new HumanPlayerFactory();
    ComputerPlayerFactory computerPlayerFactory = new ComputerPlayerFactory();
    playerOne = humanPlayerFactory.create("player 1", CellColor.BLUE);
    playerTwo = computerPlayerFactory.create("player 2", CellColor.RED);
  }
}
