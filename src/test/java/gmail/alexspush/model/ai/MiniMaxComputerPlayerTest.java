package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
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
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        //creating list of valid moves...
        final Set<PlayerMove> validMoves = playBoard.getValidMoves();
        assertTrue(validMoves.contains(playerMove));
    }

    @Test
    public void shouldReturnMoveThatLeadsToVictory() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . o x ]
        //[ . x o ]
        //[ . x . ]
        //Player X should go now
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        assertEquals(new PlayerMove(2, 0), playerMove);
    }

    @Test
    public void shouldReturnAnyMoveThatLeadsToVictorIfManyOptionsExist() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . o x ]
        //[ . x o ]
        //[ . x x ]
        //Player X should go now
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);
        playBoard.setPlayField(2, 2, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        List<PlayerMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new PlayerMove(0, 0));
        possibleMoves.add(new PlayerMove(2, 0));
        assertTrue(possibleMoves.contains(playerMove));
    }

    @Test
    public void shouldReturnMoveThatAvoidsFailure() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ o o . ]
        //[ . x o ]
        //[ . x . ]
        //Player X should go now
        playBoard.setPlayField(0, 0, PlayField.O);
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        assertEquals(new PlayerMove(0, 2), playerMove);
    }

    @Test
    public void shouldReturnMoveThatLeadsToVictoryFaster() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . o . ]
        //[ . x o ]
        //[ . x . ]
        //Player X should go now
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        List<PlayerMove> possibleMoves = new ArrayList<>();
        possibleMoves.add(new PlayerMove(2, 2));
        possibleMoves.add(new PlayerMove(2, 0));
        assertTrue(possibleMoves.contains(playerMove));
    }

    @Test
    public void shouldReturnMoveThatLeadsToVictoryFasterAgain() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ x o x ]
        //[ . . o ]
        //[ . . x ]
        //Player X should go now
        playBoard.setPlayField(0, 0, PlayField.X);
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 2, PlayField.X);        

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
        assertEquals(new PlayerMove(1, 1), playerMove);
    }

    @Test
    public void shouldReturnSomeMoveForStartedBoard() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . . . ]
        //[ . x . ]
        //[ . . . ]
        //Player X should go now
        playBoard.setPlayField(1, 1, PlayField.X);

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.O);

        assertNotNull(playerMove);
    }

    @Test
    public void shouldReturnSomeMoveForEmptyBoard() {
        final ComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ . . . ]
        //[ . . . ]
        //[ . . . ]
        //Player X should go now

        final PlayerMove playerMove = computerPlayer.getMove(playBoard, Player.X);

        assertNotNull(playerMove);
    }

    @Test
    public void getMovesToAnalyzeShouldReturnAllAvailableMoves() {
        final MiniMaxComputerPlayer computerPlayer = new MiniMaxComputerPlayer();
        final PlayBoard playBoard = new PlayBoard();
        //[ o . x ]
        //[ . x . ]
        //[ . o . ]
        //Player O should go now
        playBoard.setPlayField(0, 0, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(2, 1, PlayField.O);

        final List<AnalyzedMove> analyzedMoves = computerPlayer.getMovesToAnalyze(playBoard);

        final List<AnalyzedMove> expectedMoves = new ArrayList<>();
        final Set<PlayerMove> validMoves = playBoard.getValidMoves();
        for(PlayerMove validMove : validMoves) {
            final AnalyzedMove analyzedMove = new AnalyzedMove(validMove);
            expectedMoves.add(analyzedMove);
        }

        assertEquals(expectedMoves, analyzedMoves);
    }

}
