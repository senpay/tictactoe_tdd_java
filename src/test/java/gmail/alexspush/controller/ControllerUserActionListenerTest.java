package gmail.alexspush.controller;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import gmail.alexspush.AssertUtils;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;
import gmail.alexspush.view.BoardView;
import gmail.alexspush.view.ConsoleBoardView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class ControllerUserActionListenerTest {

    private PlayBoard playBoard;
    private BoardView boardView;
    private GameController controller;

    @DataProvider
    public static Object[][] movesAndBoardStates() {
        return new Object[][]{
                {1, 1, Player.X},
                {0, 1, Player.O},
                {2, 2, Player.X},
                };
    }

    @Before
    public void setUp(){
        playBoard = new PlayBoard();
        boardView = new ConsoleBoardView();
        controller = new GameController(playBoard, boardView);
    }

    @Test
    public void testNewGameActionPerformed() {
        controller.getUserActionListener().moveActionPerformed(1, 2, Player.X);
        controller.getUserActionListener().newGameActionPerformed();
        AssertUtils.assertBoardEmpty(playBoard);
    }

    @Test
    @UseDataProvider("movesAndBoardStates")
    public void testMoveActionPerformed(final int x, final int y, final Player player) {
        controller.getUserActionListener().moveActionPerformed(x, y, player);
        assertEquals(player.getPlayFieldValue(), playBoard.getPlayField(x, y));
    }

}
