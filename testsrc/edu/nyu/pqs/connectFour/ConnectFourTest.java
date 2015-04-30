package edu.nyu.pqs.connectFour;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ConnectFourTest {
  ConnectFour connectFour;
  final int row = 6;
  final int col = 7;

  @Before
  public void setupConnectFour() {

    HashMap<String, String> options = new HashMap<String, String>();
    options.put("rowCount", Integer.toString(row));
    options.put("colCount", Integer.toString(col));
    connectFour = new ConnectFour_TwoPlayer(options);
  }

  @Test
  public void testFindingHorizontalWin() {
    Board board = connectFour.getBoard();
    board.initCells();
    board.insertChip(CellColor.BLUE, 3, 1);
    board.insertChip(CellColor.BLUE, 3, 2);
    board.insertChip(CellColor.BLUE, 3, 3);
    board.insertChip(CellColor.BLUE, 3, 4);
    connectFour.setBoard(board);
    assertTrue(connectFour.isWinner(CellColor.BLUE));
  }

  @Test
  public void testFindingVerticalWin() {
    Board board = connectFour.getBoard();
    board.initCells();
    board.insertChip(CellColor.BLUE, 1, 2);
    board.insertChip(CellColor.BLUE, 2, 2);
    board.insertChip(CellColor.BLUE, 3, 2);
    board.insertChip(CellColor.BLUE, 4, 2);
    connectFour.setBoard(board);
    assertTrue(connectFour.isWinner(CellColor.BLUE));
  }

  @Test
  public void testFindingNegativeSlopeWin() {
    Board board = connectFour.getBoard();
    board.initCells();
    board.insertChip(CellColor.BLUE, 2, 2);
    board.insertChip(CellColor.BLUE, 3, 3);
    board.insertChip(CellColor.BLUE, 4, 4);
    board.insertChip(CellColor.BLUE, 5, 5);
    connectFour.setBoard(board);
    assertTrue(connectFour.isWinner(CellColor.BLUE));
  }

  @Test
  public void testFindingPositiveSlopeWin() {
    Board board = connectFour.getBoard();
    board.initCells();
    board.insertChip(CellColor.RED, 5, 4);
    board.insertChip(CellColor.RED, 4, 3);
    board.insertChip(CellColor.RED, 3, 2);
    board.insertChip(CellColor.RED, 2, 1);
    connectFour.setBoard(board);
    assertTrue(connectFour.isWinner(CellColor.RED));
  }

  @Test
  public void testRowFull() {
    Board board = connectFour.getBoard();
    board.initCells();
    for (int row = 0; row < board.getRowSize() * 10; row++) {
      if (row % 2 == 0) {
        connectFour.addChip(CellColor.RED, 2);
      } else {
        connectFour.addChip(CellColor.BLUE, 2);
      }
    }
    connectFour.setBoard(board);
    assert (connectFour.getBoard().freeRows[2] == 0);
  }

  @Test
  public void testAllRowsFull() {
    Board board = connectFour.getBoard();
    board.initCells();
    connectFour.setBoard(board);
    for (int row = 0; row < board.getRowSize(); row++) {
      for (int col = 0; col < board.getColSize(); col++) {
        connectFour.addChip(CellColor.RED, col);
      }
      for (int i = 0; i < board.getColSize(); i++) {
        assert (connectFour.getBoard().freeRows[i] == 0);
      }
    }
  }
}
