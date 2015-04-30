package edu.nyu.pqs.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import edu.nyu.pqs.connectFour.Board;
import edu.nyu.pqs.connectFour.BoardFactory;
import edu.nyu.pqs.control.Control;

/**
 * A GUI for the Connect Four game. The GUI is used by two players playing the
 * game on a single machine.
 * 
 * @author chenprice
 * 
 */
public class View extends JFrame implements Observer {
  private MainPanel mainPanel = null;
  private JFrame mainFrame = null;
  private JPanel newGamePanel = null;
  private int rowCount;
  private int colCount;
  private Control control = null;
  private View that = this;

  /**
   * A constructor method for the {@link View} object
   * 
   * @param row
   * @param col
   */
  public View(final int row, final int col) {
    rowCount = row;
    colCount = col;
  }

  /**
   * Sets up a new board and displays it.
   */
  public void startGameView() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | UnsupportedLookAndFeelException ex) {
        }
        if (mainPanel != null) {
          mainPanel.removeListeners();
          mainPanel = null;
        }
        if (mainFrame != null) {
          mainFrame.dispose();
        }
        mainFrame = new JFrame();
        BoardFactory boardFactory = BoardFactory.getBoardFactory(rowCount, colCount);
        Board freshBoard = boardFactory.getBoard();
        mainPanel = new MainPanel(rowCount, colCount, that, freshBoard);
        mainPanel.setListeners(true);
        setTurn();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();
      }
    });
  }

  /**
   * Changes the title of the frame.
   */
  public void setTitle(String title) {
    mainFrame.setTitle(title);
  }

  /**
   * Displays a menu before each game is started.
   */
  public void displayMainMenu() {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        if (mainPanel != null) {
          mainPanel = null;
        }
        if (mainFrame != null) {
          mainFrame.dispose();
        }
        mainFrame = new JFrame("Connect Four - Main Menu");

        final JPanel newGamePanel = new JPanel();
        JButton buttonOnePlayer = new JButton("One Player");
        buttonOnePlayer.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            requestStartGame("one player");
          }
        });

        JButton buttonTwoPlayers = new JButton("Two Players");
        buttonTwoPlayers.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            requestStartGame("two players");
          }
        });

        JButton buttonQuit = new JButton("quit");
        buttonQuit.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            // Execute when button is pressed
            System.exit(0);
          }
        });

        // Create the StyleContext, the document and the pane
        StyleContext sc = new StyleContext();
        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        final JTextPane pane = new JTextPane(doc);

        // Create and add the main document style
        Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
        StyleConstants.setLeftIndent(mainStyle, 16);
        StyleConstants.setRightIndent(mainStyle, 16);
        StyleConstants.setFirstLineIndent(mainStyle, 16);
        StyleConstants.setFontFamily(mainStyle, "serif");
        StyleConstants.setFontSize(mainStyle, 22);

        // Set the foreground color and change the font
        pane.setForeground(Color.magenta);
        pane.setFont(new Font("Monospaced", Font.ITALIC, 24));

        // Add the text to the document
        try {
          doc.insertString(0, "Start a new game?", null);
        } catch (BadLocationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }

        pane.setEditable(false);
        newGamePanel.add(pane);
        newGamePanel.add(buttonOnePlayer);
        newGamePanel.add(buttonTwoPlayers);
        newGamePanel.add(buttonQuit);

        mainFrame.getContentPane().add(newGamePanel);
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
      }
    });
  }

  /**
   * Used to connect the GUI with a {@link Control} object.
   * 
   * @param control
   */
  public void setControl(Control control) {
    this.control = control;
  }

  /**
   * Sends a request to start a new game to the {@link Control} object.
   */
  private void requestStartGame(String gameType) {
    control.startGame(gameType);
  }

  /**
   * Sends a request to place a chip at the given column <code>col</code>
   * 
   * @param col
   */
  public void makeMove(int col) {
    control.playTurn(col);
  }

  /**
   * Following the {@link java.lang.Observer} pattern, enables receipt of
   * messages from an {@link java.lang.Observable} object, in this case - a
   * {@link ConnectFour} object. .
   */
  @Override
  public void update(Observable o, Object arg) {
    Board currBoard = (Board) arg;
    if (mainPanel != null) {
      mainPanel.updateBoardState(currBoard);
      if (currBoard.isWin()) {
        winRoutine();
      }
      mainPanel.revalidate();
      mainPanel.repaint();
      mainFrame.repaint();
    }
  }

  /**
   * Shows who made the winning move.
   * 
   * @param turn
   */
  private void anounceWinner(int turn) {
    if (turn % 2 == 0) {
      setTitle("Congrads player one!!");
    } else {
      setTitle("Congrads player two!!");
    }
  }

  /**
   * Displays who has the current turn.
   */
  public void setTurn() {
    if (mainFrame != null) {
      int turn = control.getTurn();
      if (turn % 2 == 1) {
        mainFrame.setTitle("move[" + turn + "] : turn => Player One");
      } else {
        mainFrame.setTitle("move[" + turn + "] : turn => Player Two");
      }
    }
  }

  /**
   * Refreshes the board graphics.
   */
  public void refresh() {
    mainPanel.repaint();
  }

  /**
   * Disables GUI user input, declares winner, performs cleanup.
   */
  private void winRoutine() {
    lock();
    new java.util.Timer().schedule(new java.util.TimerTask() {
      @Override
      public void run() {
        anounceWinner(control.getTurn() - 1);
        try {
          Thread.sleep(1500);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        control.endGame();
      }
    }, 0);
  }

  /**
   * Disables user input
   */
  public void lock() {
    if (mainPanel != null) {
      mainPanel.setListeners(false);
    }
  }

  /**
   * Enables user input
   */
  public void unlock() {
    if (mainPanel != null) {
      mainPanel.setListeners(true);
    }
  }
}