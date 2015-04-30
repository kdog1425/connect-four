package edu.nyu.pqs.connectFour;

import edu.nyu.pqs.gui.View;

/**
 * A class derived from {@link Player}, representing a human player.
 * @author chenprice
 *
 */
public class HumanPlayer extends Player{
  
  public HumanPlayer(String name, CellColor cellColor){
     this.name = name;
     this.cellColor = cellColor;
   }
}
