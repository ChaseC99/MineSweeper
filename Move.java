/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *  
 */
public class Move
{
    // instance variables - replace the example below with your own
    private Location loc;
    private Click move;
    
    /**
     * Constructor for objects of class Move
     */
    public Move(Location loc, Click move)
    {
        this.loc = loc;
        this.move = move;
    }

    public Location getLocation(){
        return loc;
    }
    
    public Click getClickType(){
        return move;
    }
}
