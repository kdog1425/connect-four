package edu.nyu.pqs.connectFour;

import java.util.HashMap;

import edu.nyu.pqs.gui.View;

public interface PlayerFactory {
  public Player create(String name, CellColor cellColor);
}
