package edu.nyu.pqs.connectFour;

/**
 * This class follows the Singleton Factory pattern. It should be used to create
 * {@link Board} instances, which are also Singleton. Only a single board can be
 * generated in the application's lifetime. To ensure the game's board being a
 * Singleton object, {@link Boards} should only be instantiated by this class,
 * and not through the {@link Boards} constructor.
 * 
 * The factory and its methods are thread safe.
 * 
 * @author chenprice
 * 
 */
public class BoardFactory {
  final static int defaultRowSize = 6;
  final static int defaultColSize = 7;
  private static BoardFactory boardFactory;
  private static Board board;

  private BoardFactory(int row, int col) {
    board = new Board(row, col);
  }

  /**
   * When first called, creates a new {@link Board} instance.
   * 
   * @param rowSize
   * @param colSize
   * @return a Singleton {@link BoardFactory}
   */
  public static synchronized BoardFactory getBoardFactory(int rowSize, int colSize) {
    if (boardFactory == null) {
      boardFactory = new BoardFactory(rowSize, colSize);
    }
    return boardFactory;
  }

  /**
   * If not preceded by a call to {@link #getBoardFactory(int, int )}, creates a
   * board using default row/column sizes.
   * 
   * @return a reference to the Singleton {@BoardFactory} object.
   */
  public static synchronized BoardFactory getBoardFactory() {
    if (boardFactory == null) {
      return getBoardFactory(defaultRowSize, defaultColSize);
    }
    return boardFactory;
  }

  /**
   * 
   * @param rowSize
   * @param colSize
   * @return an instance of a {@link Board}. The instance is constructed on the
   *         first call to {@link BoardFactory#getBoardFactory()} . Subsequent
   *         calls return the same instance.
   */
  public Board getBoard() {
    return board;
  }

  /**
   * 
   * @param b
   * @return an copy of the passed in {@link Board}. The instance is constructed
   *         on the first method call. Subsequent calls return the same
   *         instance.
   */
  public Board getBoard(Board b) {
    return new Board(board);
  }

}
