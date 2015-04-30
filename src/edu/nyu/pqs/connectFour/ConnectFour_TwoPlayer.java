package edu.nyu.pqs.connectFour;

import java.util.HashMap;

public class ConnectFour_TwoPlayer extends ConnectFour {

  /**
   * A class derived from {@link ConnectFour}, representing a connect four game
   * with two human players. The {@link HumanPlayer} objects are created at
   * instantiation.
   * 
   * @author chenprice
   * 
   */
  public ConnectFour_TwoPlayer(HashMap<String, String> options) {
    super(options);
  }

  @Override
  protected void createPlayers() {
    HumanPlayerFactory humanPlayerFactory = new HumanPlayerFactory();
    playerOne = humanPlayerFactory.create("player 1", CellColor.BLUE);
    playerTwo = humanPlayerFactory.create("player 2", CellColor.RED);
  }
}
