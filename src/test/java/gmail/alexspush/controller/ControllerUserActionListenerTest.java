package gmail.alexspush.controller;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.view.BoardView;
import gmail.alexspush.view.ConsoleBoardView;
import org.junit.Assert;
import org.junit.Test;

public class ControllerUserActionListenerTest {

    @Test
    public void testActionPerformed() {
        PlayBoard playBoard = new PlayBoard();
        BoardView boardView = new ConsoleBoardView();
        GameController controller = new GameController(playBoard, boardView);
        Player player = Player.X;
        controller.getUserActionListener().moveActionPerformed(2, 2, player);
        Assert.assertEquals(player.getPlayFieldValue(), playBoard.getPlayField(2, 2));
    }
}
