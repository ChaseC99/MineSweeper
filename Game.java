/** MineSweeper
 *  @author Chase Carnaroli
 *
 *  Game class controls the gameflow of the MineSweeper game
 *  Implements Model-View-Controller architectural pattern
 *
 *  INSTANCE VARIABLES
 *      MineField board         // The model of the game
 *      UI display              // The View of the game
 *      boolean pressed         // Represents whether player has clicked to make a move
 *      Move pressedMove        // The last move made by the player
 *
 *  METHODS
 *      getMineField() -> MineField     // Returns MineField
 *      getBoardSize() -> int           // Returns boardSize
 *      setDisplay(UI)                  // Sets the UI
 *      playGame()                      // Controls the game and keeps playing until game is over
 *      processMove() -> Result         // waits for UI to indicate that a square was clicked (by detecting that pressed == true),
 *                                          then tells board to either record the move or indicate if it was an invalid move;
 *                                          if invalid, waits for another move; if valid, returns Result
 *      pressed(Move)                   // Indicates player has clicked and made a move
 *      resetGame()                     // Clears board for a new game
 *
 */
public class Game
{
    // instance variables
    private MineField board;
    private UI display;
    private boolean pressed;
    private Move pressedMove;

    /*
     *  Constructor for default class Game
     */
    public Game()
    {
        board = new MineField();        // create new board, 16x16 with 40 mines
        pressed = false;                // set pressed to false
    }

    /*
     *  Constructor for custom class Game
     */
    public Game(int boardSize, int mineCount)
    {
        board = new MineField(boardSize, mineCount);        // create new board with custom size and mineCount
        pressed = false;                                    // set pressed to false
    }

    /*
     *  post: return MineField
     */
    public MineField getMineField(){
        return board;
    }

    /*
     *  post: returns boardSize
     */
    public int getBoardSize(){
        return board.getBoardSize();
    }

    /*
     *  post: sets the UI data field
     */
    public void setDisplay(UI ui)
    {
        display = ui;   // sets UI to incoming parameter
    }

    /*
     *  Controls the game and keeps it playing.
     *  Will continue to play until the game is over
     */
    public void playGame()
    {
        Result result;
        boolean playAgain, gameOver;
        do{
            playAgain = false;
            gameOver = false;
            do{
                result = processMove();                     // gets result of the move; waits inside processMove method until tile is pressed
                Location loc = pressedMove.getLocation();

                switch(result){
                    case PLAYER_DIED:
                        gameOver = true;
                        display.showAllMines();
                        display.showWrongFlags();
                        display.showDetonatedMine(loc);
                        break;
                    case MINEFIELD_CLEARED:
                        gameOver = true;
                        display.flagAllMines();
                        break;
                    case TILE_FLAGGED:
                        display.flag(loc);
                        break;
                    case TILE_UNFLAGGED:
                        display.unFlag(loc);
                        break;
                    case TILE_QUESTIONED:
                        display.question(loc);
                        break;
                    case GAME_NOT_OVER:
                    default: display.updateDisplay();
                }
            } while(!gameOver);

            playAgain = display.displayWinner(result);  // displays result

            if(playAgain){
                resetGame();    // resets game to play again
            }
        }while(playAgain);

        display.endProgram();   // end game and close program
    }

    /*
     * pre: ‘pressed’ is false
     * post: waits for UI to indicate that a square was clicked (by detecting that pressed == true),
     *          then tells board to either record the move or indicate if it was an invalid move;
     *          if invalid, waits for another move; if valid, returns Result
     *          also resets ‘pressed’
     */
    private Result processMove()
    {
        Result result;                  // returned at end of move
        boolean validMove = false;      // sets it false to start
        do{
            pressed = false;    // sets pressed to false
            while(!pressed) // wait for user to press button
            {
                // waiting to get pressed
                try{
                    Thread.sleep(100);
                } catch (Exception e){}
            }

            result = board.recordTurn(pressedMove);
            if(result != Result.INVALID_LOCATION && result != Result.LOCATION_ALREADY_TURNED){
                validMove = true;
            }
        }while(!validMove);

        return result;
    }

    /*
     *  pre: called by UI to indicate that a square was clicked on; move =! null
     *  post: sets ‘pressed’ to true; sets ‘pressedMove’ to ‘move’
     */
    public void pressed(Move move)
    {
        pressedMove = move;
        pressed = true;
    }

    /*
     * post: clears board for a new game
     */
    public void resetGame()
    {
        display.clearDisplay();
        board = new MineField();
        pressed = false;
    }
}
