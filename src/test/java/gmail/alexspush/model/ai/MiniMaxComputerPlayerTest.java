package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import gmail.alexspush.model.PlayerMove;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 2.3.18
 */
public class MiniMaxComputerPlayerTest {

    @Test
    public void shouldReturnValidPlayerMove() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . o x ]
        //[ . x o ]
        //[ o x . ]
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.X);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard);

        assertNotNull(playerMove);
        //creating list of valid moves...
        final List<PlayerMove> validMoves = playBoard.getValidMoves();
        assertTrue(validMoves.contains(playerMove));
    }

}
