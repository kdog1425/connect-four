package edu.nyu.pqs.connectFour;

/**
 * A {@link HumanPlayer} factory class.
 * 
 * @author chenprice
 * 
 */
public class HumanPlayerFactory implements PlayerFactory {
  @Override
  public Player create(String name, CellColor cellColor) {
    return new HumanPlayer(name, cellColor);
  }
}
