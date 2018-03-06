package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 2.3.18
 */

//TODO: implementation is wrong, need to reimplement (see https://www.neverstopbuilding.com/blog/2013/12/13/tic-tac-toe-understanding-the-minimax-algorithm13)

public class MiniMaxComputerPlayer implements ComputerPlayer {

    protected static List<AnalyzedMove> getMovesToAnalyze(final PlayBoard playBoard) {
        final Set<PlayerMove> validMoves = playBoard.getValidMoves();
        List<AnalyzedMove> movesToAnalyze = new ArrayList<>();

        for (PlayerMove validMove : validMoves) {
            final AnalyzedMove moveToAnalyze = new AnalyzedMove(validMove);
            movesToAnalyze.add(moveToAnalyze);
        }

        return movesToAnalyze;
    }

    private static PlayerMove getMoveWithMaximumScore(final List<AnalyzedMove> analyzedMoves) {
        Optional<AnalyzedMove> bestMove =
                analyzedMoves.stream().max((x, y) -> (x.getWeight() > y.getWeight()) ? 1 : -1);

        if (!bestMove.isPresent()) {
            throw new IllegalStateException("seems that analyzedMoves list was empty, should never be the case");
        }

        return bestMove.get().getMove();
    }

    private static List<AnalyzedMove> analyzeMoves(final PlayBoard currentPlayBoard, final Player playerThatShouldWin) {
        List<AnalyzedMove> movesToAnalyze = getMovesToAnalyze(currentPlayBoard);
        final MoveAnalyzer moveAnalyzer = new MoveAnalyzer(playerThatShouldWin);
        for (AnalyzedMove moveToAnalyze : movesToAnalyze) {
            final int weight = moveAnalyzer.getMoveWeight(moveToAnalyze.getMove(), currentPlayBoard);
            moveToAnalyze.setWeight(weight);
        }
        return movesToAnalyze;
    }

    @Override
    public PlayerMove getMove(final PlayBoard currentPlayBoard, final Player playerThatShouldWin) {
        List<AnalyzedMove> analyzedMoves = analyzeMoves(currentPlayBoard, playerThatShouldWin);
        return getMoveWithMaximumScore(analyzedMoves);
    }

}
