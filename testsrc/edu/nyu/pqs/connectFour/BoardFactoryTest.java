package edu.nyu.pqs.connectFour;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardFactoryTest {

  @Test
  public void testBoardAndBoardFactoryAreSingleton(){
    BoardFactory boardFactory1 = BoardFactory.getBoardFactory(6,7);
    Board board1 = boardFactory1.getBoard();
    BoardFactory boardFactory2 = BoardFactory.getBoardFactory(16, 19);
    Board board2 = boardFactory2.getBoard();
    assertTrue(board1.equals(board2));
  }

}
