/** MineSweeper
 *  @author Chase Carnaroli
 *
 *  MineField class is the model for the Minesweeper game
 *  It stores all the information about the board
 *  Created based off of the PowaySoft TicTacToe Assignment
 *
 *  INSTANCE VARIABLES
 *      int boardSize       // Width and height of the board
 *      int mineCount       // Number of mines on the board
 *      int tilesLeft       // Number of tiles (without mines) that haven't been turned
 *      Tile[] board        // 2 dimmensional array of tiles
 *
 *  METHODS
 *      assignMines()                               // Randomly assigns mines to some tiles on the grid
 *      getBoard() -> Tile[]                        // Returns the board
 *      recordTurn(Move) -> Result                  // Processes the move and returns the appropriate result
 *      turnAdjacentZeros(Location)                 // Turns over all tiles surrounding the tile
 *      getNumNeighboorMines(Location) -> int       // Returns number of mines around the selected location
 *      turnOverTile(Location)                      // Turns tile at this location
 *      getBoardSize() -> int                       // Returns boardSize
 *      getTile(Location) -> Tile                   // Returns tile at this location
 *      mineAt(Location) -> boolean                 // Returns true if there is a mine at the location
 *      isValid(Location) -> boolean                // Makes sure a location is inside the grid
 *      isChecked(Location) -> boolean              // Checks to see if location has been turned
 *      printMineField()                            // Prints board to console
 *
 */

import java.util.*;
public class MineField
{
    // instance variables
    public int boardSize;
    private int mineCount;
    private int tilesLeft;
    private Tile[][] board;

    /**
     * Constructor for class Board with default boardSize and mineCount
     * 16x16 with 40 mines
     *
     * First sets up a 2-dimensional array of tiles
     * Then fills the array with tiles
     * Then runs assignMines() to randomly assign mines to some tiles
     * Lastly, runs getNumNeighboorMines(Location) for each tile so each knows how many of their neighboors have mines
     */
    public MineField()
    {
        // Set up minefield
        this.boardSize = 16;
        this.mineCount = 20;
        board = new Tile[boardSize][boardSize];
        tilesLeft = boardSize * boardSize - mineCount;

        // Puts a tile on each location of the grid
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new Tile(new Location(x,y));
            }
        }

        // Assigns mines to some tiles randomly
        assignMines();

        // Runs getNumNeighboorMines(Location) for each tile in the grid
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y].setNum(getNumNeighboorMines(new Location(x,y)));
            }
        }
    }

    /**
     * Constructor for class Board with custom boardSize and mineCount
     *
     * First sets up a 2-dimensional array of tiles
     * Then fills the array with tiles
     * Then runs assignMines() to randomly assign mines to some tiles
     * Lastly, runs getNumNeighboorMines(Location) for each tile so each knows how many of their neighboors have mines
     */
    public MineField(int boardSize, int mineCount)
    {
        // Set up minefield
        this.boardSize = boardSize;
        this.mineCount = mineCount;
        board = new Tile[boardSize][boardSize];
        tilesLeft = boardSize * boardSize - mineCount;

        // Puts a tile on each location of the grid
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new Tile(new Location(x,y));
            }
        }

        // Assigns mines to some tiles randomly
        assignMines();

        // Runs getNumNeighboorMines(Location) for each tile in the grid
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y].setNum(getNumNeighboorMines(new Location(x,y)));
            }
        }
    }

    /**
     * Randomly assigns mines to some tiles on the grid
     *
     * post: 'mineCount' number of mines are randomly assigned to random tiles in the grid
     */
    public void assignMines(){
        int counter = 0;

        while(counter < mineCount){
            int randomRow = (int)(Math.random() * boardSize);
            int randomCol = (int)(Math.random() * boardSize);
            Location randomLoc = new Location(randomRow, randomCol);

            if(!mineAt(randomLoc)){
                getTile(randomLoc).plantMine();
                counter++;
            }
        }
    }

    /**
     * post: returns the board (Tile[][])
     */
    public Tile[][] getBoard(){
        return board;
    }

    /**
     *
     * Processes the move and returns the appropriate result
     *
     * pre: ‘player’ is ‘X’ or ‘O’; ‘loc’ is a valid location
     * post: if loc is not empty (or invalid), returns that Result; else loc is empty -> updates
     *  that location with ‘player’, increments numMoves, checks if win or tie, and returns
     *  result
     *
     */
    public Result recordTurn(Move move)
    {
        Location loc = move.getLocation();
        Click clickType = move.getClickType();

        // checks for invalid locations
        if(!isValid(loc)){
            return Result.INVALID_LOCATION;
        }
        else if(!isCovered(loc)){
            return Result.LOCATION_ALREADY_TURNED;
        }

        // If it was a right click to flag tile, this code runs
        if(clickType == Click.FLAG){
            Tile pos = getTile(loc);

            // checks to see if tile is already flagged
            if(pos.isFlagged()){
                // if it is flagged, the tile is unflagged
                pos.setFlag(false);
                return Result.TILE_UNFLAGGED;
            } else {
                // else, the tile is flagged
                pos.setFlag(true);
                return Result.TILE_FLAGGED;
            }
        }

        // If it was a left click to turn over tile, this code runs
        if(clickType == Click.TURN_TILE){
            Tile pos = getTile(loc);

            // checks to see if the tile was flagged
            // if so, the tile is question marked
            // important-note: When a tile is question marked, it is not unflagged
            //      It becomes question marked AND flagged
            if(pos.isFlagged()){
                // checks to see if tile is already question marked
                if(pos.isQuestionMarked()){
                    // if it is, the tile is unquestioned
                    pos.setQuestionMark(false);
                    return Result.TILE_FLAGGED;
                } else {
                    // else, the tile is questioned
                    pos.setQuestionMark(true);
                    return Result.TILE_QUESTIONED;
                }
            }

            // records turn and checks for gameover
            if(mineAt(loc)){
                return Result.PLAYER_DIED;
            } else {
                turnOverTile(loc);

                // checks to see if all non-mine tiles have been turned
                if(tilesLeft == 0){
                    return Result.MINEFIELD_CLEARED;
                } else {
                    return Result.GAME_NOT_OVER;
                }
            }
        }

        return Result.GAME_NOT_OVER;
    }

    // post: turns over all tiles surrounding the tile
    public void turnAdjacentZeros(Location loc){
        int row = loc.getRow();
        int col = loc.getCol();

        // Creates variables for all locations around loc
        Location topLeft = new Location(row-1,col-1);
        Location topMiddle = new Location(row-1,col);
        Location topRight = new Location(row-1,col+1);
        Location left = new Location(row,col-1);
        Location right = new Location(row,col+1);
        Location bottomLeft = new Location(row+1,col-1);
        Location bottomMiddle= new Location(row+1,col);
        Location bottomRight = new Location(row+1,col+1);

        // turn tiles at these locations
        turnOverTile(topLeft);
        turnOverTile(topMiddle);
        turnOverTile(topRight);
        turnOverTile(left);
        turnOverTile(right);
        turnOverTile(bottomLeft);
        turnOverTile(bottomMiddle);
        turnOverTile(bottomRight);
    }

    // post: return num of mines around the selected location
    public int getNumNeighboorMines(Location loc){
        int row = loc.getRow();
        int col = loc.getCol();

        int mines = 0;  // counter

        if(mineAt(new Location(row-1, col-1))) mines++;    // Top left cell
        if(mineAt(new Location(row-1, col))) mines++;      // Top middle cell
        if(mineAt(new Location(row-1, col+1))) mines++;    // Top right cell
        if(mineAt(new Location(row, col-1))) mines++;      // Left cell
        if(mineAt(new Location(row, col+1))) mines++;      // Right cell
        if(mineAt(new Location(row+1, col-1))) mines++;    // Bottom left cell
        if(mineAt(new Location(row+1, col))) mines++;      // Bottom middle cell
        if(mineAt(new Location(row+1, col+1))) mines++;    // Bottom right cell

        return mines;
    }

    // post: turns tile at this location
    public void turnOverTile(Location loc){
        // checks to make sure tile hasn't already been turned
        if(isCovered(loc)){
            Tile tileToTurn = getTile(loc);

            // turnTile() will turn the tile over if it wasnt before
            // returns true if it wasnt turned before
            if(tileToTurn.turnTile()){
                tilesLeft--;   // since it gets turned, tilesLeft decrements
            }

            // if the tile was a 0, it runs turnAdjacentZeros to turn its neighboors
            if(tileToTurn.getNum() == 0){
                turnAdjacentZeros(loc);
            }
        }
    }

    // post: returns boardSize
    public int getBoardSize(){
        return boardSize;
    }

    // post: returns tile at this location
    public Tile getTile(Location loc){
        return board[loc.getRow()][loc.getCol()];
    }

    // post: returns true if there is a mine at the location
    public boolean mineAt(Location loc)
    {
        if(isValid(loc)){
            if(board[loc.getRow()][loc.getCol()].hasMine()){
                return true;
            } else return false;
        } else return false;
    }

    /**
     * Makes sure a location is inside the grid
     * post: returns true if loc has row and col ranges [0,boardSize]
     */
    private boolean isValid(Location loc)
    {
        int row = loc.getRow();
        int col = loc.getCol();
        if(row >= boardSize || col >= boardSize || row < 0 || col < 0){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks to see if location has been turned
     * post: returns true if loc is not turned
     */
    private boolean isCovered(Location loc)
    {
        if(isValid(loc)){
            if(board[loc.getRow()][loc.getCol()].isTurned()){
                return false;
            } else return true;
        } else return false;
    }

    /**
     * Prints the grid to the console
     * Used for debugging and testing
     *
     * post: prints the grid to the conosole
     *          'x' represents mine
     *          number represents number of tiles around it with mines
     */
    public void printMineField(){
        for(int r = 0; r < boardSize; r++){
            for(int c = 0; c < boardSize; c++){
                if(board[r][c].hasMine()){
                    System.out.print("x");
                } else {
                    System.out.print(board[r][c].getNum() + "");
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.print(mineCount);
    }
}
