/** MineSweeper
 *  @author Chase Carnaroli
 *
 *  Controls the User Interface and communicates with the controller
 *  Created based off of the PowaySoft TicTacToe Assignment
 *
 *  INSTANCE VARIABLES
 *      Game myGame                 // Controller
 *      JButton[][] buttonGrid      // Grid of buttons representing the minefield
 *      int NUM_ROWS                // Number of rows on the board
 *      int NUM_COLS                // Number of columns on the board
 *      int boardSize               // Size of the board
 *      Dimension buttonSize        // Dimension of each button
 *      Icon flag                   // Image of a MineSweeper flag
 *
 *  METHODS
 *      updateDisplay()                         // Updates the display based off of the board stored in the model
 *      showAllMines()                          // Updates the display to show all mines except tiles that are flagged
 *      flagAllMines()                          // Updates display to flag all the mines
 *      showWrongFlags()                        // Updates the display to show each each tile with a flag && without a mine
 *      showDetonatedMine(Location)             // Turns detonated tile red
 *      flag(Location)                          // Flags tile at this location
 *      unFlag(Location)                        // Unflags tile at this location
 *      question(Location)                      // Question marks tile at this location
 *      displayWinner(Result) -> boolean        // Displays message with the end result; then asks if player wants to play again
 *                                                      returns true if user clicks ‘yes’ or ‘false’ if user clicks ‘no’
 *      clearDisplay()                          // Resets all of the JButtons on the grid
 *      endProgram()                            // Ends program and exits window
 */

import java.awt.*;                      // import Container
import java.awt.image.BufferedImage;
import java.util.*;                     // import ArrayList
import javax.swing.*;                   // import JFrame
import java.awt.event.*;                // import event listener
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UI extends JFrame
{
    // instance variables
    private Game myGame;
    private JButton[][] buttonGrid;
    private int NUM_ROWS, NUM_COLS, boardSize;
    private Dimension buttonSize;
    private StretchIcon flag, mine;

    // menu options
    JMenuBar menuBar;
    JMenu options, help;
    JMenuItem pauseItem, restartItem, quitItem;

    // status bar
    JPanel statusBar;

    // post: constructor – constructs window with a GridLayout and
    //  a 3 x 3 grid of JButton components and a ButtonHandler; initializes
    //  reference to ‘game’ object
    public UI(Game game)
    {
        myGame = game;
        boardSize = game.getBoardSize();
        NUM_ROWS = boardSize;
        NUM_COLS = boardSize;

        // Set up window
        Container container = getContentPane();
        container.setLayout( new GridLayout(NUM_ROWS, NUM_COLS) );

        // Set up buttons
        buttonGrid = new JButton[boardSize][boardSize];
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                buttonGrid[r][c] = new JButton();

                container.add(buttonGrid[r][c]);
                buttonGrid[r][c].addMouseListener(new MouseHandler(r,c));
                buttonGrid[r][c].setFocusPainted(false);  // gets rid of box outlining text of selected box
            }
        }

        buttonSize = buttonGrid[0][0].getSize();

        // Menus
        // Menu Bar
        menuBar = new JMenuBar();

        // Option menu
        options = new JMenu("Game");
        options.setMnemonic(KeyEvent.VK_A);
        menuBar.add(options);

        // Sub Option Items
        //Pause
        pauseItem = new JMenuItem("Pause", KeyEvent.VK_T);
        options.add(pauseItem);

        // Restart
        restartItem = new JMenuItem("Reset", KeyEvent.VK_T);
        //restartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        //restartItem.set
        options.add(restartItem);

        // Quit
        quitItem = new JMenuItem("Quit", KeyEvent.VK_T);
        options.add(quitItem);

        // Adds menu bar to screen
        this.setJMenuBar(menuBar);

        // code for the icons
        flag = new StretchIcon("MineSweeperFlag.gif");  // flag icon
        mine = new StretchIcon("Mine.png");             // mine icon

        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
        // be able to close your application!!!
        addWindowListener(new java.awt.event.WindowAdapter()
            {
                public void windowClosing(WindowEvent evt) {
                    System.exit(0);
                }
            }
        );

        // Set window size and show window
        Dimension screenSize = new Dimension(750,750);
        setMinimumSize(screenSize);     // width=700, height=700
        setVisible(true);
    }

    /**
     *  Updates the display based off of the board stored in the model
     *  post: displays ‘player’ char in JButton specified by ‘loc’
     */
    public void updateDisplay ()
    {
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                Location loc = new Location(r,c);
                Tile pos = myGame.getMineField().getTile(loc);
                JButton button = buttonGrid[r][c];

                if(pos.isTurned()){
                    button.setText(pos.toString());
                    button.setForeground(pos.getColor());
                    button.getModel().setPressed(true);
                    button.setEnabled(false);
                }
            }
        }
    }

    /**
     *  Updates the display to show all mines except tiles that are flagged
     *  post: displays each mine on the board except tiles that are flagged
     */
    public void showAllMines(){
      for(int r = 0; r < boardSize; r++){
          for(int c = 0; c < boardSize; c++){
              Location loc = new Location(r,c);
              Tile tile = myGame.getMineField().getTile(loc);

              if(tile.hasMine() && !tile.isFlagged()){
                buttonGrid[r][c].setIcon(mine);
              }
          }
      }
    }

    /**
     *  Updates the display to show each each tile with a flag && without a mine
     *  post: displays a red "X" on each tile with a flag && without a mine
     */
    public void showWrongFlags(){
      for(int r = 0; r < boardSize; r++){
          for(int c = 0; c < boardSize; c++){
              Location loc = new Location(r,c);
              Tile tile = myGame.getMineField().getTile(loc);

              if(tile.isFlagged() && !tile.hasMine()){
                unFlag(loc);
                buttonGrid[r][c].setForeground(Color.red);
                buttonGrid[r][c].setText("X");
              }
          }
      }
    }

    /**
     *  Turns detonated tile red
     *  post: tile with detonated mine gets background turned red
     */
    public void showDetonatedMine(Location loc){
      buttonGrid[loc.getRow()][loc.getCol()].setBackground(Color.red);  // sets background to red
      buttonGrid[loc.getRow()][loc.getCol()].setBorderPainted(false);   // gets rid of button border
    }

    /**
     *  Updates display to flag all the mines
     *  post: all mines on the board are flagged
     */
    public void flagAllMines(){
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                Location loc = new Location(r,c);
                if(myGame.getMineField().getTile(loc).hasMine()){
                    flag(loc);
                }
            }
        }
    }

    // Flags tile at this location
    public void flag(Location loc){
        buttonGrid[loc.getRow()][loc.getCol()].setText("");
        buttonGrid[loc.getRow()][loc.getCol()].setIcon(flag);
    }

    // Unflags tile at this location
    public void unFlag(Location loc){
        buttonGrid[loc.getRow()][loc.getCol()].setIcon(null);
        buttonGrid[loc.getRow()][loc.getCol()].setText("");
    }

    // Question marks tile at this location
    public void question(Location loc){
        buttonGrid[loc.getRow()][loc.getCol()].setIcon(null);
        buttonGrid[loc.getRow()][loc.getCol()].setText("?");
    }

    // post: displays message with the given result; then displays question
    //  “Do you want to play again?” and returns true if user clicks ‘yes’
    //  or ‘false’ if user clicks ‘no’
    public boolean displayWinner (Result result){
        updateDisplay();

        int response = JOptionPane.showConfirmDialog(this,result.toString() + "\nDo you want to play again?", "Game Over", JOptionPane.YES_NO_OPTION);
        if(response == JOptionPane.YES_OPTION){
            return true;
        } else {
            return false;
        }
    }

    // post: resets all of the JButtons on the grid
    public void clearDisplay()
    {
        // resets grid to null for every position
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                buttonGrid[r][c].setIcon(null);
                buttonGrid[r][c].setText(null);
                buttonGrid[r][c].getModel().setPressed(false);
                buttonGrid[r][c].setEnabled(true);
                buttonGrid[r][c].setBackground(null);
                buttonGrid[r][c].setBorderPainted(true);
            }
        }
    }

    // post: ends program and exits window
    public void endProgram()
    {
        System.exit(0);
    }

    // Handles mouse clicks
    private class MouseHandler extends MouseAdapter
    {
        // Instance Variables
        public int row, col;

        // Constructor
        public MouseHandler(int r, int c){
            row = r;
            col = c;
        }

        public void mouseClicked(MouseEvent event){
            Click clickType = Click.NA;

            if(event.getButton() == MouseEvent.BUTTON1){
                clickType = Click.TURN_TILE;
            }

            if(event.getButton() == MouseEvent.BUTTON3){
                clickType = Click.FLAG;
            }

            myGame.pressed(new Move(new Location(row,col), clickType));
        }
    }
}
