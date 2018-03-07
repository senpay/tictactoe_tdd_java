package gmail.alexspush.model.ai;

import gmail.alexspush.model.BoardStatus;
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

    private static PlayBoard getHypatheticalPlayBoard(final PlayBoard currentPlayBoard, final Player playerToMakeMove,
                                                      final PlayerMove nextMove) {
        final PlayBoard hypotheticalPlayBoard = new PlayBoard(currentPlayBoard);
        hypotheticalPlayBoard.setPlayField(nextMove.x, nextMove.y, playerToMakeMove.getPlayFieldValue());
        return hypotheticalPlayBoard;
    }

    @Override
    public PlayerMove getMove(final PlayBoard currentPlayBoard, final Player playerThatShouldWin) {
        final List<AnalyzedMove> analyzedMoves = getMovesToAnalyze(currentPlayBoard);
        for (AnalyzedMove analyzedMove : analyzedMoves) {
            final PlayerMove move = analyzedMove.getMove();
            final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard, playerThatShouldWin,
                    move);
            final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

            if (hypotheticalBoardStatus != BoardStatus.IN_PROGRESS &&
                    hypotheticalBoardStatus.hasPlayerWon(playerThatShouldWin)) {
                analyzedMove.setWeight(10);
                return move;
            } else {
                if (isNextRivalMoveLeadsToFailure(hypotheticalPlayBoard, playerThatShouldWin.getRival())) {
                    analyzedMove.setWeight(-10);
                }
            }
        }
        return getMoveWithMaximumScore(analyzedMoves);
    }

    private boolean isNextRivalMoveLeadsToFailure(final PlayBoard currentPlayBoard, final Player rival) {
        for (PlayerMove rivalMove : currentPlayBoard.getValidMoves()) {
            final PlayBoard hypotheticalRivalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard,
                    rival, rivalMove);
            final BoardStatus hypotheticalRivalPlayBoardStatus = hypotheticalRivalPlayBoard.getStatus();

            if (hypotheticalRivalPlayBoardStatus != BoardStatus.IN_PROGRESS &&
                    hypotheticalRivalPlayBoardStatus.hasPlayerWon(rival)) {
                return true;
            }
        }
        return false;
    }


}
