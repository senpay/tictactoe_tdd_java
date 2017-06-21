package gmail.alexspush;

import gmail.alexspush.controller.GameController;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.view.ConsoleBoardView;

import java.io.IOException;

/**
 * Created by senpay on 19.6.17.
 */
public class Main {

    public static void main(String... ags) throws IOException {
        ConsoleBoardView view = new ConsoleBoardView();
        PlayBoard board = new PlayBoard();
        GameController controller = new GameController(board, view);
        controller.start();
    }
}
