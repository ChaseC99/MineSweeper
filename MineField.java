/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *  
 *  Created based off of the PowaySoft TicTacToe Assignment
 */

import java.util.*;
public class MineField
{
    // instance variables
    public int boardSize;
    private int mineCount;
    private Tile[][] board;
    private int tilesLeft;

    /**
     * Constructor for class Board with default boardSize and mineCount
     * 16x16 with 40 mines
     */
    public MineField()
    {
        // Set up minefield
        this.boardSize = 16;
        this.mineCount = 20;
        board = new Tile[boardSize][boardSize];
        tilesLeft = boardSize * boardSize - mineCount;

        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new Tile(new Location(x,y));
            }
        }

        assignMines();

        // Assign nums to each tile
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y].setNum(getNumNeighboorMines(new Location(x,y)));
            }
        }
    }
    
    /**
     * Constructor for class Board with custom boardSize and mineCount
     */
    public MineField(int boardSize, int mineCount)
    {
        // Set up minefield
        this.boardSize = boardSize;
        this.mineCount = mineCount;
        board = new Tile[boardSize][boardSize];
        tilesLeft = boardSize * boardSize - mineCount;

        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y] = new Tile(new Location(x,y));
            }
        }

        assignMines();

        // Assign nums to each tile
        for(int x = 0; x < boardSize; x++){
            for(int y = 0; y < boardSize; y++){
                board[x][y].setNum(getNumNeighboorMines(new Location(x,y)));
            }
        }
    }

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

    public Tile[][] getBoard(){
        return board;
    }

    // pre: ‘player’ is ‘X’ or ‘O’; ‘loc’ is a valid location
    // post: if loc is not empty (or invalid), returns that Result; else loc is empty -> updates 
    //  that location with ‘player’, increments numMoves, checks if win or tie, and returns
    //  result
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
        
        // right click to flag tile
        if(clickType == Click.FLAG){
            Tile pos = getTile(loc);
            
            if(pos.isFlagged()){
                pos.setFlag(false);
                return Result.TILE_UNFLAGGED;
            } else {
                pos.setFlag(true);
                return Result.TILE_FLAGGED;
            }
        }
        
        // left click to turn over tile
        if(clickType == Click.TURN_TILE){
            if(getTile(loc).isFlagged()){
                if(getTile(loc).switchQuestionMark()){
                    return Result.TILE_QUESTIONED;
                } else {
                    return Result.TILE_FLAGGED;
                }
            }
            
            // records turn and checks for gameover
            if(mineAt(loc)){
                return Result.PLAYER_DIED;
            } else {
                turnOverTile(loc);

                if(tilesLeft == 0){
                    return Result.MINEFIELD_CLEARED;
                } else {
                    return Result.GAME_NOT_OVER;
                }
            }
        }
        
        return Result.GAME_NOT_OVER;
    }

    // post: turns over all tiles surrounding a 0 tile
    public void turnAdjacentZeros(Location loc){
        int row = loc.getRow();
        int col = loc.getCol();

        // Create new locations around loc
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

        int mines = 0;

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
        if(isCovered(loc)){
            Tile tileToTurn = getTile(loc);

            // turnTile() will turn the tile over if it wasnt before
            // returns true if it wasnt turned before
            if(tileToTurn.turnTile()){
                tilesLeft--;   // since it gets turned, tilesLeft decrements
            }

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

    // post: return player position
    public boolean mineAt (Location loc)
    {
        if(isValid(loc)){
            if(board[loc.getRow()][loc.getCol()].hasMine()){
                return true;
            } else return false;
        } else return false;
    }

    // post: returns true if loc has row and col ranges 0-2
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

    // post: returns true if loc has empty String “” or Symbol.EMPTY
    private boolean isCovered(Location loc)
    {
        if(isValid(loc)){
            if(board[loc.getRow()][loc.getCol()].isTurned()){
                return false;
            } else return true;
        } else return false;
    }

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
