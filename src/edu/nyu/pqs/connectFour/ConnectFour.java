package edu.nyu.pqs.connectFour;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;

/**
 * An abstract class which implements the {@link Observable} interface.
 * Instances of this class represent the data for a game of ConnectFour.
 * 
 * @author chenprice
 * 
 */
public abstract class ConnectFour extends Observable {
  protected Player playerOne;
  protected Player playerTwo;
  private HashMap<String, String> options;
  private Board board;

  /**
   * The constructor for the {@link ConnectFour} instance. The
   * <code>options</code> argument must have keys/values corresponding to
   * {'rowCount' : <code>int</code> and {'colCount' : <code>int</code>
   * 
   * @param options
   */
  public ConnectFour(HashMap<String, String> options) {
    this.options = options;
    createPlayers();
    createBoard();
  }

  /**
   * If there is free spaces in the given column, adds a chip of
   * <code>chipColor</code> color at given <code>column</code> in the named row.
   * 
   * @param chipColor
   * @param column
   * @return
   */
  public boolean addChip(CellColor chipColor, int column) {
    int freePos = board.nextRow(column);
    if (freePos >= 0) {
      board.insertChip(chipColor, freePos, column);
      notifyObservers(board);
      return true;
    }
    return false;
  }

  /**
   * A method that handles creation of {@link Player} instances for the game. It
   * should be implemented in sub classes of class {@link ConnectFour}.
   */
  protected abstract void createPlayers();

  /**
   * A method that handles creation of {@link Board} instances for the game. It
   * should be implemented in sub classes of class {@link ConnectFour}.
   */
  private void createBoard() {
    int row = Integer.parseInt(options.get("rowCount"));
    int col = Integer.parseInt(options.get("colCount"));
    BoardFactory bf = BoardFactory.getBoardFactory(row, col);
    board = bf.getBoard();
  }

  /**
   * 
   * @return a copy of the {@link Board} member of the instance.
   */
  public Board getBoard() {
    BoardFactory boardFactory = BoardFactory.getBoardFactory();
    Board boardCopy = boardFactory.getBoard(this.board);
    return boardCopy;
  }

  /**
   * Sets the {@link Board} member.
   * @param board
   */
  public void setBoard(Board board){
    this.board = board;
  }
  
  /**
   * 
   * @return the player one {@link Player} object
   */
  public Player getPlayerOne() {
    return playerOne;
  }

  /**
   * 
   * @return the player two {@link Player} object
   */
  public Player getPlayerTwo() {
    return playerTwo;
  }

  /**
   * Sends a message updating instances of objects that implement
   * {@link java.util.Observer}, with the latest state of the game's
   * {@link Board}.
   */
  public void sendUpdate() {
    setChanged();
    notifyObservers(board);
    clearChanged();
  }

  /**
   * Board initialization, sets all cells to white(empty).
   */
  public void initBoard() {
    board.setWinState(false);
    board.initCells();
    sendUpdate();
  }

  /**
   * Sets a boolean - true if game is won, false otherwise.
   * 
   * @param b
   */
  public void setWinState(boolean b) {
    board.winState = b;
  }

  /**
   * Checks if there is a horizontal, vertical or diagonal sequence of 4 chips.
   * 
   * @param cellColor
   * @return true if there is such a sequence of passed in {@link CellColor},
   *         false otherwise
   */
  public boolean isWinner(CellColor cellColor) {
    CellColor[][] cells = board.getCells();

    // check horizontal
    for (int i = 0; i < board.getRowSize(); ++i) {
      int count = 0;
      for (int j = 0; j < board.getColSize(); ++j) {
        if (cells[i][j] == cellColor) {
          count++;
          if (count == 4) {
            board.setWinState(true);
            Point winningCell = new Point(i, j);
            String winningDirection = "horizontal";
            board.showWin(winningCell, winningDirection);
            board.setWinState(true);
            return true;
          }
        } else {
          count = 0;
        }
      }
    }

    // check vertical
    for (int j = 0; j < board.getColSize(); ++j) {
      int count = 0;
      for (int i = 0; i < board.getRowSize(); ++i) {
        if (cells[i][j] == cellColor) {
          count++;
          if (count == 4) {
            board.setWinState(true);
            Point winningCell = new Point(i, j);
            String winningDirection = "vertical";
            board.showWin(winningCell, winningDirection);
            board.setWinState(true);
            return true;
          }
        } else {
          count = 0;
        }
      }
    }

    // check diagonal - negative slope
    for (int i = 0; i < board.getRowSize(); ++i) {
      for (int j = 0; j < board.getColSize(); ++j) {
        boolean outOfBounds = false;
        int count = 0;
        int delta = 0;
        while (delta < 4 && !outOfBounds) {
          if (cells[i + count][j + count] == cellColor) {
            count++;
            if (count == 4) {
              board.setWinState(true);
              Point winningCell = new Point(i, j);
              String winningDirection = "negative-slope";
              board.showWin(winningCell, winningDirection);
              board.setWinState(true);
              return true;
            }
          } else {
            count = 0;
          }
          delta++;
          if (i + delta >= board.getRowSize() || j + delta >= board.getColSize()) {
            outOfBounds = true;
          }
        }
      }
    }

    // check diagonal - positive slope
    for (int i = board.getRowSize() - 1; i > 0; --i) {
      for (int j = 0; j < board.getColSize(); ++j) {
        boolean outOfBounds = false;
        int count = 0;
        int delta = 0;
        while (delta < 4 && !outOfBounds) {
          if (cells[i - delta][j + delta] == cellColor) {
            count++;
            if (count == 4) {
              Point winningCell = new Point(i, j);
              String winningDirection = "positive-slope";
              board.showWin(winningCell, winningDirection);
              board.setWinState(true);
              return true;
            }
          } else {
            count = 0;
          }
          delta++;
          if (i - delta < 0 || j + delta >= board.getColSize()) {
            outOfBounds = true;
          }
        }
      }
    }
    return false;
  }

  /**
   * A utility method, checks if there is a horizontal, vertical or diagonal
   * sequence of 4 chips in the given <code>board</code>.
   * 
   * @return true if there is such a sequence of passed in {@link CellColor},
   *         false otherwise
   * @param cellColor
   * @param board
   * @return
   */
  public static boolean isWinner(CellColor cellColor, Board board) {
    CellColor[][] cells = board.getCells();

    // check horizontal
    for (int i = 0; i < board.getRowSize(); ++i) {
      int count = 0;
      for (int j = 0; j < board.getColSize(); ++j) {
        if (cells[i][j] == cellColor) {
          count++;
          if (count == 4) {
            board.setWinState(true);
            Point winningCell = new Point(i, j);
            String winningDirection = "horizontal";
            board.showWin(winningCell, winningDirection);
            board.setWinState(true);
            return true;
          }
        } else {
          count = 0;
        }
      }
    }

    // check vertical

    for (int j = 0; j < board.getColSize(); ++j) {
      int count = 0;
      for (int i = 0; i < board.getRowSize(); ++i) {
        if (cells[i][j] == cellColor) {
          count++;
          if (count == 4) {
            board.setWinState(true);
            Point winningCell = new Point(i, j);
            String winningDirection = "vertical";
            board.showWin(winningCell, winningDirection);
            board.setWinState(true);
            return true;
          }
        } else {
          count = 0;
        }
      }
    }

    // check diagonal - negative slope
    for (int i = 0; i < board.getRowSize(); ++i) {
      for (int j = 0; j < board.getColSize(); ++j) {
        boolean outOfBounds = false;
        int count = 0;
        int delta = 0;
        while (delta < 4 && !outOfBounds) {
          if (cells[i + count][j + count] == cellColor) {
            count++;
            if (count == 4) {
              board.setWinState(true);
              Point winningCell = new Point(i, j);
              String winningDirection = "negative-slope";
              board.showWin(winningCell, winningDirection);
              board.setWinState(true);
              return true;
            }
          } else {
            count = 0;
          }
          delta++;
          if (i + delta >= board.getRowSize() || j + delta >= board.getColSize()) {
            outOfBounds = true;
          }
        }
      }
    }

    // check diagonal - positive slope
    for (int i = board.getRowSize() - 1; i > 0; --i) {
      for (int j = 0; j < board.getColSize(); ++j) {
        boolean outOfBounds = false;
        int count = 0;
        int delta = 0;
        while (delta < 4 && !outOfBounds) {
          if (cells[i - delta][j + delta] == cellColor) {
            count++;
            if (count == 4) {
              Point winningCell = new Point(i, j);
              String winningDirection = "positive-slope";
              board.showWin(winningCell, winningDirection);
              board.setWinState(true);
              return true;
            }
          } else {
            count = 0;
          }
          delta++;
          if (i - delta < 0 || j + delta >= board.getColSize()) {
            outOfBounds = true;
          }
        }
      }
    }
    return false;
  }

  public HashMap<String, String> getOptions() {
    return options;
  }
}
