package gmail.alexspush.controller;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.view.BoardView;
import gmail.alexspush.view.ConsoleBoardView;
import org.junit.Assert;
import org.junit.Test;

public class GameControllerTest {

    @Test
    public void testConstructor() {
        PlayBoard playBoard = new PlayBoard();
        BoardView boardView = new ConsoleBoardView();
        GameController controller = new GameController(playBoard, boardView);
        Assert.assertSame(boardView, controller.getBoardView());
        Assert.assertSame(playBoard, controller.getPlayBoard());
        Assert.assertNotNull(controller.getUserActionListener());
        Assert.assertSame(controller.getUserActionListener(), controller.getUserActionListener());
    }

}
