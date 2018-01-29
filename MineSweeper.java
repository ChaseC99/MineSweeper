/** MineSweeper
 *  @author Chase Carnaroli
 *  @period 6
 *
 *  Main code which runs the game
 */

// Import to support default look and feel
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

// main()
public class MineSweeper {
    public static void main(String[] args){

        // Set look and feel default
        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        Game mineSweeper = new Game();
        UI display = new UI(mineSweeper);
        mineSweeper.setDisplay(display);
        mineSweeper.playGame();
    }
}
