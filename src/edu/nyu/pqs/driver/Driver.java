package edu.nyu.pqs.driver;

import java.util.HashMap;

import javax.swing.JFrame;

import edu.nyu.pqs.connectFour.ConnectFour;
import edu.nyu.pqs.connectFour.ConnectFour_OnePlayer;
import edu.nyu.pqs.control.Control;
import edu.nyu.pqs.gui.View;

/**
 * The {link @Driver calss} is used to launch the Connect Four application.
 * @author chenprice
 *
 */
public class Driver {
  public static void main(String args[]) {

    final int row = 6;
    final int col = 7;

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("rowCount", Integer.toString(row));
    options.put("colCount", Integer.toString(col));
    ConnectFour connectFour = new ConnectFour_OnePlayer(options);
    View view = new View(row, col);
    Control control = new Control(connectFour, view);
    view.setControl(control);
    view.displayMainMenu();

  }

}
