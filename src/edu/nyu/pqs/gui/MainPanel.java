package edu.nyu.pqs.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import edu.nyu.pqs.connectFour.Board;
import edu.nyu.pqs.connectFour.BoardFactory;
import edu.nyu.pqs.connectFour.CellColor;

public class MainPanel extends JPanel {
  int colCount;
  int rowCount;
  int selectedCol;
  List<Rectangle> cells;
  Board board;
  View view;
  boolean listenersOn;
  MouseAdapter m1, m2;

  public MainPanel(int row, int col, final View view, Board board) {
    rowCount = row;
    colCount = col;
    this.view = view;
    cells = new ArrayList<>(colCount * rowCount);
    updateBoardState(board);

    m1 = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        if (listenersOn) {
          view.makeMove(selectedCol);
        }
      }
    };
    addMouseListener(m1);

    m2 = new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        int width = getWidth();
        int cellWidth = width / colCount;
        selectedCol = e.getX() / cellWidth;
        repaint();
      }
    };
    addMouseMotionListener(m2);

    listenersOn = false;
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  @Override
  public void invalidate() {
    cells.clear();
    super.invalidate();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    int width = getWidth();
    int height = getHeight();

    int cellWidth = width / colCount;
    int cellHeight = height / rowCount;

    int xOffset = (width - (colCount * cellWidth)) / 2;
    int yOffset = (height - (rowCount * cellHeight)) / 2;

    if (cells.isEmpty()) {
      for (int row = 0; row < rowCount; row++) {
        for (int col = 0; col < colCount; col++) {
          Rectangle cell = new Rectangle(xOffset + (col * cellWidth), yOffset + (row * cellHeight),
              cellWidth, cellHeight);
          cells.add(cell);
        }
      }
    }

    for (int row = 0; row < rowCount; row++) {
      for (int col = 0; col < colCount; col++) {
        int index = row * colCount + col;
        Rectangle cell = cells.get(index);
        g2d.setStroke(new BasicStroke(7));
        g2d.setColor(Color.YELLOW);
        g2d.draw(cell);

        if (board.getCells()[row][col] == CellColor.WHITE) {
          g2d.setColor(Color.WHITE);
        } else if (board.getCells()[row][col] == CellColor.RED) {
          g2d.setColor(Color.RED);
        } else if (board.getCells()[row][col] == CellColor.BLUE) {
          g2d.setColor(Color.BLUE);
        } else if (board.getCells()[row][col] == CellColor.PINK) {
          g2d.setColor(Color.PINK);
        }
        g2d.fillOval((int) cell.getX(), (int) cell.getY(), (int) cell.getWidth(),
            (int) cell.getHeight());
      }
    }

      for (int row = 0; row < rowCount; row++) {
        for (int col = 0; col < colCount; col++) {
          int index = row * colCount + col;
          Rectangle cell = cells.get(index);
          if (listenersOn && col == selectedCol) {
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.BLACK);
            g2d.draw(cell);
          }
        }
      }
    g2d.dispose();
  }

  public void updateBoardState(Board curr) {
    BoardFactory boardFactory = BoardFactory.getBoardFactory();
    board = boardFactory.getBoard(curr);
    repaint();
  }

  public void setListeners(boolean b) {
    listenersOn = b;
  }

  public void removeListeners() {
    removeMouseListener(m1);
    removeMouseListener(m2);
  }

}