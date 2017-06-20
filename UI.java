/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *  
 *  Created based off of the PowaySoft TicTacToe Assignment
 */

import java.awt.*;          // import Container
import java.awt.image.BufferedImage;
import java.util.*;         // import ArrayList
import javax.swing.*;       // import JFrame
import java.awt.event.*;    // import event listener
import java.io.File;
import javax.imageio.ImageIO;

public class UI extends JFrame
{
    // instance variables
    private Game myGame;
    private JButton[][] buttonGrid;
    private int NUM_ROWS, NUM_COLS, boardSize;
    private Dimension buttonSize;
    private StretchIcon flag;

    // menu options
    JMenuBar menuBar;
    JMenu options, help;
    JMenuItem restartItem;

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
            }
        }

        buttonSize = buttonGrid[0][0].getSize();

        // Menus
        // Menu Bar
        menuBar = new JMenuBar();

        // Option menu
        options = new JMenu("Game Options");
        options.setMnemonic(KeyEvent.VK_A);
        menuBar.add(options);

        // Sub Option Items
        restartItem = new JMenuItem("Reset Game", KeyEvent.VK_T);
        restartItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        //restartItem.set
        //options.add(restartItem);

        // code for the icons
        // flag icon
        flag = new StretchIcon("E:\\School\\Senior\\AP Computer Science\\MineSweeper\\MineSweeperFlag.gif");

        this.setJMenuBar(menuBar);
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
        setSize(700, 700);     // width=700, height=700
        setVisible(true);
    }

    // pre: player is Symbol.PLAYER_X or Symbol.PLAYER_O; ‘loc’ is a valid location
    // post: displays ‘player’ char in JButton specified by ‘loc’
    public void updateDisplay ()
    {        
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                Location loc = new Location(r,c);
                Tile pos = myGame.getMineField().getTile(loc);
                JButton button = buttonGrid[r][c];

                if(pos.isTurned()){
                    button.setText(pos.toString());
                    button.getModel().setPressed(true);
                    button.setEnabled(false);
                }
            }
        }
    }

    public void showAllMines(){
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                Location loc = new Location(r,c);
                if(myGame.getMineField().getTile(loc).hasMine()){
                    buttonGrid[r][c].setText("X");
                }
            }
        }
    }

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

    public void flag(Location loc){
        buttonGrid[loc.getRow()][loc.getCol()].setText("");
        buttonGrid[loc.getRow()][loc.getCol()].setIcon(flag);
    }

    public void unFlag(Location loc){
        buttonGrid[loc.getRow()][loc.getCol()].setIcon(null);
        buttonGrid[loc.getRow()][loc.getCol()].setText("");
    }

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

    // post: clears all JButtons (so text is neither ‘X’ nor ‘O’)
    public void clearDisplay()
    {
        // resets grid to null for every position
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                buttonGrid[r][c].setText("" + ' ');
                buttonGrid[r][c].getModel().setPressed(false);
                buttonGrid[r][c].setEnabled(true);
            }
        }
    }

    // post: ends program
    public void endProgram()
    {
        System.exit(0);
    }

    private class MouseHandler extends MouseAdapter
    {
        public int row, col;
        // constructor
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

