/** MineSweeper
 *  @author Chase Carnaroli
 *  
 *  The Click enum is used to determine what type of click is desired by the user.
 *  The different types are: 
 *      FLAG - flag or unflag a tile
 *      TURN_TILE - turn over a tile or switch from flag to question mark
 *      NA - used when a space is already turned
 */
public enum Click
{
    FLAG, TURN_TILE, NA
}
