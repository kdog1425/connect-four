package edu.nyu.pqs.connectFour;

import java.awt.Point;

/**
 * A class for a ConnectFour board. A cell can be white(empty), red or blue. An
 * instance can be constructed by passing in a row and column number, or passing
 * in a {@link Board} which is then copied.
 * 
 * @author chenprice
 * 
 */
public class Board {
  CellColor[][] cells;
  int[] freeRows;
  int rowSize = -1;
  int colSize = -1;
  boolean winState = false;

  public Board(int row, int col) {
    rowSize = row;
    colSize = col;
    cells = new CellColor[rowSize][colSize];
    initCells();
  }

  public Board(Board oldBoard) {
    rowSize = oldBoard.getRowSize();
    colSize = oldBoard.getColSize();
    cells = new CellColor[rowSize][colSize];
    freeRows = new int[colSize];

    for (int r = 0; r < rowSize; ++r) {
      for (int c = 0; c < colSize; ++c) {
        cells[r][c] = oldBoard.getCells()[r][c];
      }
    }

    for (int col = 0; col < colSize; ++col) {
      freeRows[col] = oldBoard.getFreeRows()[col];
    }
    this.winState = oldBoard.isWin();
  }

  /**
   * Sets all cells to empty.
   */
  public void initCells() {
    freeRows = new int[colSize];
    for (int row = 0; row < rowSize; ++row) {
      for (int col = 0; col < colSize; ++col) {
        cells[row][col] = CellColor.WHITE;
      }
    }
    for (int col = 0; col < colSize; ++col) {
      freeRows[col] = rowSize - 1;
    }
  }

  public void changeChip(CellColor cellColor, int row, int col) {
    if (row < rowSize && row >= 0 && col < colSize && col >= 0) {
      cells[row][col] = cellColor;
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Adds a chip at the given position on the board. Throws a
   * {@link java.lang.IllegalStateException} if an attempt is made to add a chip
   * out of bounds.
   * 
   * @param newCellState
   * @param row
   * @param col
   */
  public void insertChip(CellColor newCellState, int row, int col) {
    if (row < rowSize && row >= 0 && col < colSize && col >= 0) {
      cells[row][col] = newCellState;
      freeRows[col]--;
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * Removes a chip from the given position on the board. Throws a
   * {@link java.lang.IllegalStateException} if an attempt is made to add a chip
   * out of bounds.
   * 
   * @param row
   * @param col
   */
  public void removeChip(int row, int col) {
    if (row < rowSize && row >= 0 && col < colSize && col >= 0) {
      cells[row][col] = CellColor.WHITE;
      freeRows[col]++;
    } else {
      throw new IllegalStateException();
    }
  }

  /**
   * 
   * @return a boolean value - true if there a winner.
   */
  public boolean isWin() {
    return winState;
  }

  /**
   * To be used by a {@link edu.nyu.pqs.control.Control} object
   * 
   * @param b
   */
  public void setWinState(boolean b) {
    winState = b;
  }

  /**
   * 
   * @param column
   * @return the row position of the next free cell in the given column
   */
  public int nextRow(int column) {
    return freeRows[column];
  }

  /**
   * Highlights the winning 4 chips.
   * 
   * @param winningCell
   * @param winningDirection
   */
  public void showWin(Point winningCell, String winningDirection) {
    int row = winningCell.x;
    int col = winningCell.y;
    int deltaRow = 0;
    int deltaCol = 0;

    if (winningDirection == "horizontal") {
      deltaCol = -1;
    } else if (winningDirection == "vertical") {
      deltaRow = -1;
    } else if (winningDirection == "negative-slope") {
      deltaRow = deltaCol = 1;
    } else if (winningDirection == "positive-slope") {
      deltaCol = 1;
      deltaRow = -1;
    }

    int count = 0;
    while (count++ < 4) {
      insertChip(CellColor.PINK, row, col);
      row += deltaRow;
      col += deltaCol;
    }
  }

  public CellColor[][] getCells() {
    return cells;
  }

  public void setCells(CellColor[][] cells) {
    this.cells = cells;
  }

  public int getRowSize() {
    return rowSize;
  }

  public void setRowSize(int rowSize) {
    this.rowSize = rowSize;
  }

  public int getColSize() {
    return colSize;
  }

  public void setColSize(int colSize) {
    this.colSize = colSize;
  }

  public int[] getFreeRows() {
    return freeRows;
  }

  public void printCellsToConsole() {
    // print column headers
    System.out.println();
    System.out.print("        ");
    for (int col = 0; col < colSize; ++col) {
      System.out.print(String.format("%10s", "col[" + col + "] "));
    }
    System.out.println();

    // print cells and row headers
    for (int row = 0; row < rowSize; ++row) {
      System.out.print("row[" + row + "] ");
      for (int col = 0; col < colSize; ++col) {
        System.out.print(String.format("%10s", cells[row][col] + " |"));
      }
      System.out.println();
    }
    System.out.println();
  }

}
