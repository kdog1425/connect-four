package edu.nyu.pqs.connectFour;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {
  Board board;
  
  @Before
  public void setupBoard(){
    final int row = 6;
    final int col = 7;

    BoardFactory boardFactory = BoardFactory.getBoardFactory(row, col);
    board = boardFactory.getBoard();
    board.initCells();
  }
  
  @Test
  public void testChangeCornersToBlue() {
    board.insertChip(CellColor.BLUE, 0, 6);
    board.insertChip(CellColor.BLUE, 0, 0);
    board.insertChip(CellColor.BLUE, 5, 0);
    board.insertChip(CellColor.BLUE, 5, 6);
    
    assertEquals(board.getCells()[0][6], CellColor.BLUE);
    assertEquals(board.getCells()[0][0], CellColor.BLUE);
    assertEquals(board.getCells()[5][0], CellColor.BLUE);
    assertEquals(board.getCells()[5][6], CellColor.BLUE);
  }
}
