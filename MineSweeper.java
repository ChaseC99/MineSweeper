/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *  
 *  Created based off of the PowaySoft TicTacToe Assignment
 */

// main() 
public class MineSweeper {
    public static void main(String[] args){ 
        Game mineSweeper = new Game();
        UI display = new UI(mineSweeper);
        mineSweeper.setDisplay(display);
        mineSweeper.playGame();
    }
}
