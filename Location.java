/** MineSweeper
 *  @author Chase Carnaroli
 *  
 *  The Location class stores a row and column, which correlates to a position on the grid.  
 *
 *  Based off of the PowaySoft TicTacToe Assignment
 */

import java.util.*;
public class Location { 
    // Instance Variables
    int row, col;   // position of the location on the grid

    // Constructor
    public Location(int row, int col) {
        this.row = row; 
        this.col = col;
    }

    // returns row
    public int getRow(){ 
        return row; 
    }

    // returns col
    public int getCol(){
        return col; 
    }
    
    public String toString() {
        return "Location: " + row + " " + col;
    }
}
