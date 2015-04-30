package edu.nyu.pqs.control;

import java.util.HashMap;

import edu.nyu.pqs.connectFour.Board;
import edu.nyu.pqs.connectFour.CellColor;
import edu.nyu.pqs.connectFour.ComputerPlayer;
import edu.nyu.pqs.connectFour.ConnectFour;
import edu.nyu.pqs.connectFour.ConnectFour_OnePlayer;
import edu.nyu.pqs.connectFour.ConnectFour_TwoPlayer;
import edu.nyu.pqs.connectFour.Player;
import edu.nyu.pqs.gui.View;

/**
 * A controller class for a ConnectFour game application. This class controls
 * the game logic and the GUI.
 * 
 * @author chenprice
 * 
 */
public class Control {
  private ConnectFour connectFour;
  private View view;
  private int turn = -1;

  /**
   * A constructor class. It his here where the MVC pattern used in the game
   * application, is set up.
   * 
   * @param model
   * @param playerOne
   * @param playerTwo
   * @param view
   */
  public Control(ConnectFour connectFour, View view) {
    this.connectFour = connectFour;
    this.view = view;
  }

  /**
   * Prepares the game start - registers the view with the game, initializes the
   * board, and launches the GUI.
   */
  public void startGame(String gameType) {
    HashMap<String, String> options = connectFour.getOptions();
    if (gameType == "two players") {
      this.connectFour = new ConnectFour_TwoPlayer(options);
    } else {
      this.connectFour = new ConnectFour_OnePlayer(options);
    }
    connectFour.addObserver(view);
    turn = 1;
    connectFour.initBoard();
    view.startGameView();
  }

  /**
   * Performs a move in the game, also checks if the move leads to winning. If
   * the move is legal the board and registered views are updated using the
   * Observable/Observer pattern, and the turn is counted.
   * 
   * @param col
   */
  public void playTurn(int col) {
    final Player p1 = connectFour.getPlayerOne();
    final Player p2 = connectFour.getPlayerTwo();
    CellColor cellColor;
    if (turn % 2 == 1) {
      cellColor = p1.getCellColor();
    } else {
      cellColor = p2.getCellColor();
    }
    boolean hasWon = false;
    if (connectFour.addChip(cellColor, col)) {
      hasWon = checkWinner(cellColor);
      connectFour.sendUpdate();
      if (!hasWon) {
        turn++;
        view.setTurn();
        if (turn % 2 == 0 && p2 instanceof ComputerPlayer) {
          view.lock();
          new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e1) {
                e1.printStackTrace();
              }
              Board currBoard = connectFour.getBoard();
              int chosenCol = ((ComputerPlayer) p2).autoPlayTurn(currBoard);
              playTurn(chosenCol);
              view.unlock();
            }
          }, 0);
        }
      }
    }
  }

  /**
   * Signals the end the current game. Observers are unregistered from the game
   * model and the main menu is launched.
   */
  public void endGame() {
    connectFour.deleteObservers();
    view.displayMainMenu();
  }

  /**
   * 
   * @return an int representing the current turn.
   */
  public int getTurn() {
    return turn;
  }

  /**
   * Checks if the current board is in winning state.
   * 
   * @param cellColor
   * @param col
   */
  private boolean checkWinner(CellColor cellColor) {
    if (connectFour.isWinner(cellColor)) {
      connectFour.setWinState(true);
      return true;
    }
    return false;
  }

  /**
   * 
   * @return a reference to the {@link ConnectFour} member.
   */
  public ConnectFour getConnectFour() {
    return connectFour;
  }

  /**
   * Sets the {@link ConnectFour} member
   */
  public void setConnectFour(ConnectFour connectFour) {
    this.connectFour = connectFour;
  }
}
