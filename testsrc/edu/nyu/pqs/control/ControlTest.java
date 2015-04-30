package edu.nyu.pqs.control;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.pqs.connectFour.Board;
import edu.nyu.pqs.connectFour.CellColor;
import edu.nyu.pqs.connectFour.ConnectFour;
import edu.nyu.pqs.connectFour.ConnectFour_OnePlayer;
import edu.nyu.pqs.connectFour.ConnectFour_TwoPlayer;
import edu.nyu.pqs.gui.View;

public class ControlTest {
  Control control;
  
  @Before
  public void setup() {
    final int row = 6;
    final int col = 7;

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("rowCount", Integer.toString(row));
    options.put("colCount", Integer.toString(col));
    ConnectFour connectFour = new ConnectFour_OnePlayer(options);
    View view = new View(row, col);
    control = new Control(connectFour, view);
    view.setControl(control);
    
  }
  
  @Test
  public void testStartGame_onePlayer() {
    control.startGame("one player");
    assertEquals(control.getTurn(), 1); 
    assertTrue(control.getConnectFour() instanceof ConnectFour_OnePlayer);
  }
  
  @Test
  public void testStartGame_twoPlayer() {
    control.startGame("two players");
    assertEquals(control.getTurn(), 1); 
    assertTrue(control.getConnectFour() instanceof ConnectFour_TwoPlayer);
  }
  
  @Test
  public void testPlayTurn_PlayerOneWins(){
    control.startGame("two players");
    control.playTurn(1);
    control.playTurn(1);
    control.playTurn(2);
    control.playTurn(2);
    control.playTurn(3);
    control.playTurn(3);
    control.playTurn(4);
    ConnectFour connectFour = control.getConnectFour();
    assertTrue(connectFour.isWinner(CellColor.PINK));
  }
  
}
