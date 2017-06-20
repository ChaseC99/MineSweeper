/** MineSweeper
 *  @author Chase Carnaroli
 * 
 *  Each location on the grid has a Tile class
 *  
 *  INSTANCE VARIABLES
 *      boolean mine                // represents whether or not the tile has a mine
 *      boolean flagged             // represents whether or not the tile is flagged
 *      Location loc                // the location of the tile on the grid
 *      int neighboorsWithMines     // number of neighboors with mines
 *      boolean turned              // represents whether or not the tile has been turned
 *      boolean questionMark        // represents whether or not the tile is question marked
 *
 *  METHODS
 *      getLocation() -> Location   // returns the location of the tile
 *      plantMine()                 // plants a mine on this tile 
                                    // post: mine set to true
 *      setFlag(boolean)            // set whether or not the tile is flagged
                                    // post: flagged is set to the boolean passed through the method
 *      isFlagged() -> boolean      // returns true is tile is flagged, false if not
 *      setQuestionMark(boolean)    // set whether or not the tile is question marked
                                    // post: questionMark is set to the boolean passed through the method
 *      turnTile() -> boolean       // post: if tile wasn't turned already, it sets turn to true and returns true, else returns false
 *      isTurned() -> boolean       // returns true if tile is turned, false if not                               
 *      hasMine() -> boolean        // returns true if tile has mine, false if not
 *      setNum()                    // post: sets neighboorsWithMines to the int passed through
 *      getNum() -> int             // returns number of neighboors with mines
 *      toString() -> String        // returns string of the int value of number of neighboors. If value == 0, returns and empty string
 */

public class Tile
{
    // instance variables
    private boolean mine;               // represents whether or not the tile has a mine
    private boolean flagged;            // represents whether or not the tile is flagged
    private Location loc;               // the location of the tile on the grid
    private int neighboorsWithMines;    // number of neighboors with mines
    private boolean turned;             // represents whether or not the tile has been turned
    private boolean questionMark;       // represents whether or not the tile is question marked

    /**
     * Constructor for objects of class Tile
     */
    public Tile(Location location)
    {
        // initialise instance variables
        loc = location;
        mine = false;
        flagged = false;
        turned = false;
        questionMark = false;
    }

    // returns the location of the tile
    public Location getLocation(){
        return loc;
    }

    // plants a mine on this tile
    // post: mine set to true
    public void plantMine(){
        mine = true;
    }

    // set whether or not the tile is flagged
    // post: flagged is set to the boolean passed through the method
    public void setFlag(boolean flag){
        flagged = flag;
    }

    // returns true is tile is flagged, false if not
    public boolean isFlagged(){
        return flagged;
    }

    // set whether or not the tile is question marked
    // post: questionMark is set to the boolean passed through the method
    public void setQuestionMark(boolean question){
        questionMark = question;
    }

    // post: if tile wasn't turned already, it sets turn to true and returns true, else returns false
    public boolean turnTile(){
        if(!turned){
            turned = true;
            return true;
        } else {
            return false;
        }
    }

    // returns true if tile is turned, false if not
    public boolean isTurned(){
        return turned;
    }

    // returns true if tile has mine, false if not
    public boolean hasMine(){
        return mine;
    }

    // post: sets neighboorsWithMines to the int passed through
    public void setNum(int neighboors){
        neighboorsWithMines = neighboors;
    }

    // returns number of neighboors with mines
    public int getNum(){
        return neighboorsWithMines;
    }

    // returns string of the int value of number of neighboors. If value == 0, returns and empty string
    public String toString(){
        if(neighboorsWithMines == 0){
            return "";
        } else {
            return neighboorsWithMines + "";
        }
    }
}
