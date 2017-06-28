/** MineSweeper
 *  @author Chase Carnaroli
 *
 *  Move class represents the move made by the player
 *
 *  INSTANCE VARIABLES
 *      Location loc    // location of the click
 *      Click move      // type of click made by the user
 *
 *  METHODS
 *      getLocation() -> Location   // returns location
 *      getClickType() -> Click     // returns click type
 */
public class Move
{
    // instance variables
    private Location loc;   // location of the click
    private Click move;     // type of click made by the user

    /**
     * Constructor for objects of class Move
     */
    public Move(Location loc, Click move)
    {
        this.loc = loc;
        this.move = move;
    }

    // returns location
    public Location getLocation(){
        return loc;
    }

    // returns click type
    public Click getClickType(){
        return move;
    }
}
