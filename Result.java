/** MineField
 *  @author Chase Carnaroli
 *
 *  Result enum represents all possible results of a move
 *
 *  The different results are:
 *      INVALID_LOCATION            // Location selected in not on the grid or some other critical error
 *      LOCATION_ALREADY_TURNED     // Location has already been turned over
 *      GAME_NOT_OVER               // Game is not over, continue on
 *      PLAYER_DIED                 // Player picked a tile with a mine. Game is over.
 *      MINEFIELD_CLEARED           // Player has cleared the minefield. Game is over.
 *      TILE_FLAGGED                // Tile is flagged
 *      TILE_UNFLAGGED              // Tile is unflagged
 *      TILE_QUESTIONED             // Tile is questioned
 */

public enum Result {
    INVALID_LOCATION, LOCATION_ALREADY_TURNED, GAME_NOT_OVER, PLAYER_DIED, MINEFIELD_CLEARED, TILE_FLAGGED, TILE_UNFLAGGED, TILE_QUESTIONED
}
