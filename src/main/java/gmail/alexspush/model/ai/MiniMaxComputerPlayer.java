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
public class MiniMaxComputerPlayer implements ComputerPlayer {

    private static final int WEIGHT = 10;
    private List<AnalyzedMove> movesToAnalyze;

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

    private static void analyzeMoves(final AnalyzedMove moveUnderAnalyzis, final PlayBoard playBoardStatusToAnalyze,
                                     final Player playerThatShouldWin, final Player currentPlayer, int depth) {
        for (PlayerMove validMove : playBoardStatusToAnalyze.getValidMoves()) {
            final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(playBoardStatusToAnalyze, currentPlayer,
                    validMove);
            final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

            if(hypotheticalBoardStatus != BoardStatus.IN_PROGRESS) {
                final int weight = assessMoveWeight(hypotheticalBoardStatus, playerThatShouldWin, depth);
                moveUnderAnalyzis.setWeight(weight);
                //game is over, no need for further analysis
                return;
            }

            analyzeMoves(moveUnderAnalyzis, hypotheticalPlayBoard, playerThatShouldWin,
                    currentPlayer.getRival(), depth++);
        }
    }

    @Override
    public PlayerMove getMove(final PlayBoard currentPlayBoard, final Player playerThatShouldWin) {
        movesToAnalyze = getMovesToAnalyze(currentPlayBoard);
        analyzeMoves(currentPlayBoard, playerThatShouldWin);
        return getMoveWithMaximumScore(movesToAnalyze);
    }

    private void analyzeMoves(final PlayBoard currentPlayBoard, final Player playerThatShouldWin) {
        for (AnalyzedMove moveToAnalyze : movesToAnalyze) {
            final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard, playerThatShouldWin,
                    moveToAnalyze.getMove());
            final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

            if (hypotheticalBoardStatus != BoardStatus.IN_PROGRESS) {
                final int weight = assessMoveWeight(hypotheticalBoardStatus, playerThatShouldWin);
                moveToAnalyze.setWeight(weight);
                //game is over, no need for further analysis
                return;
            }

            //If we don't win in this turn - iterate on all possibilities
            analyzeMoves(moveToAnalyze, hypotheticalPlayBoard, playerThatShouldWin,
                    playerThatShouldWin.getRival(), 1);

        }
    }

    private static int assessMoveWeight(final BoardStatus boardStatus,
                                  final Player playerThatShouldWin, int depth) {
        if (boardStatus.hasPlayerWon(playerThatShouldWin)) {
            //We won
            return (WEIGHT - depth);
        } else if (boardStatus.hasPlayerWon(playerThatShouldWin.getRival())) {
            //We lost
            return (depth - WEIGHT);
        }
        //draw
        return 0;
    }

    private static int assessMoveWeight(final BoardStatus boardStatus, final Player playerThatShouldWin) {
        return assessMoveWeight(boardStatus, playerThatShouldWin, 0);
    }

    private static PlayBoard getHypatheticalPlayBoard(final PlayBoard currentPlayBoard, final Player playerToMakeMove,
                                               final PlayerMove nextMove) {
        final PlayBoard hypotheticalPlayBoard = new PlayBoard(currentPlayBoard);
        hypotheticalPlayBoard.setPlayField(nextMove.x, nextMove.y, playerToMakeMove.getPlayFieldValue());
        return hypotheticalPlayBoard;
    }

}
