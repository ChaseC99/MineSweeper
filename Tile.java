/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *  
 *  Created based off of the PowaySoft TicTacToe Assignment
 */

public class Tile
{
    // instance variables
    private boolean mine;
    private boolean flagged;
    private Location loc;
    private int neighboorsWithMines;
    private boolean turned;
    private boolean questionMark;

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

    public Location getLocation(){
        return loc;
    }

    public void plantMine(){
        mine = true;
    }

    public void switchFlag(){
        if(flagged) flagged = false;
        else if(!flagged) flagged = true;

        questionMark = false;
    }

    public void setFlag(boolean flag){
        flagged = flag;
    }

    public boolean isFlagged(){
        return flagged;
    }

    public boolean switchQuestionMark(){
        if(questionMark){
            questionMark = false;
            return false;
        }
        else {
            questionMark = true;
            return true;
        }
    }

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

    public boolean isTurned(){
        return turned;
    }

    public boolean hasMine(){
        return mine;
    }

    public void setNum(int neighboors){
        neighboorsWithMines = neighboors;
    }

    public int getNum(){
        return neighboorsWithMines;
    }

    public String toString(){
        if(neighboorsWithMines == 0){
            return "";
        } else {
            return neighboorsWithMines + "";
        }
    }
}
