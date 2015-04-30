package edu.nyu.pqs.connectFour;

import edu.nyu.pqs.gui.View;

/**
 * An abstract class representing a player in the connect four game.
 * @author chenprice
 *
 */
public abstract class Player {
  String name;
  CellColor cellColor;
  View view = null;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public CellColor getCellColor() {
    return cellColor;
  }

  public void setCellColor(CellColor cellColor) {
    this.cellColor = cellColor;
  }

  public View getView() {
    return view;
  }
}
