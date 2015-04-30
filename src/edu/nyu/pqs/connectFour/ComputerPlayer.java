package edu.nyu.pqs.connectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class derived from {@link Player}, representing a computer player with
 * basic AI capabilities.
 * 
 * @author chenprice
 * 
 */
public class ComputerPlayer extends Player {
  public ComputerPlayer(String name, CellColor cellColor) {
    this.name = "Computer player";
    this.cellColor = cellColor;
  }

  /**
   * Computes a column where a chip should be inserted.
   * 
   * @param board
   * @return the winning column number if exists, a random column where a chip
   *         can be added otherwise.
   */
  public int autoPlayTurn(Board board) {
    List<Integer> available = new ArrayList<Integer>();
    int chosenCol = -1;
    chosenCol = findWinningMove(board);
    if (chosenCol != -1) {
      return chosenCol;
    }

    int[] free = board.getFreeRows();
    for (int col = 0; col < free.length; col++) {
      if (free[col] >= 0) {
        available.add(col);
      }
    }
    Random random = new Random();
    int randomCol = random.nextInt(available.size() - 1);
    return available.get(randomCol);
  }

  /**
   * Given a {@link Board} instance, finds the next winning move for this
   * {@link ComputerPlayer}.
   * 
   * @param board
   * @return the column where the chip should be inserted to win if such a
   *         column exists, -1 otherwise.
   */
  private int findWinningMove(Board board) {
    int col = 0;
    for (int freeRow : board.getFreeRows()) {
      if (freeRow >= 0) {
        board.changeChip(cellColor, freeRow, col);
        if (ConnectFour.isWinner(cellColor, board)) {
          return col;
        }
        board.changeChip(CellColor.WHITE, freeRow, col);
      }
      col++;
    }
    return -1;
  }
}
