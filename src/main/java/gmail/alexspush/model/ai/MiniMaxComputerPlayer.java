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
 * alexspush@gmail.com
 * 2.3.18
 */
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
        analyzeMoves(currentPlayBoard, playerThatShouldWin, analyzedMoves, 10);
        return getMoveWithMaximumScore(analyzedMoves);
    }

    private void analyzeMoves(final PlayBoard currentPlayBoard, final Player playerThatShouldWin,
        final List<AnalyzedMove> analyzedMoves, final int coefficent) {
        for (AnalyzedMove analyzedMove : analyzedMoves) {
            analyzeMove(currentPlayBoard, playerThatShouldWin, analyzedMove, analyzedMove, coefficent);
        }
    }

    private void analyzeMoves(final PlayBoard currentPlayBoard, final Player playerThatShouldWin,
        final List<AnalyzedMove> analyzedMoves, final AnalyzedMove originalMove, final int coefficent) {
        for (AnalyzedMove analyzedMove : analyzedMoves) {
            analyzeMove(currentPlayBoard, playerThatShouldWin, analyzedMove, originalMove, coefficent);
        }
    }

    private void analyzeMove(final PlayBoard currentPlayBoard, final Player playerThatShouldWin,
        final AnalyzedMove analyzedMove, final AnalyzedMove originalMove,
        final int coefficent) {
        final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard, playerThatShouldWin,
                analyzedMove.getMove());
        final int score = analyzeMove(hypotheticalPlayBoard, playerThatShouldWin, coefficent);
        if (score != 0) {
            originalMove.setWeight(score);
        } else {
            for (PlayBoard hypotheticalPlayBoardAfterRivalMove : getHypotheticalPlayBoards(hypotheticalPlayBoard,
                    playerThatShouldWin.getRival())) {
                final List<AnalyzedMove> hypotheticalMovesToAnalyze = getMovesToAnalyze(
                        hypotheticalPlayBoardAfterRivalMove);
                analyzeMoves(hypotheticalPlayBoardAfterRivalMove, playerThatShouldWin, hypotheticalMovesToAnalyze,
                        originalMove, coefficent - 1);
            }
        }
    }

    public int analyzeMove(final PlayBoard hypotheticalPlayBoard, final Player playerThatShouldWin,
            final int coefficent) {
        final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

        if (hypotheticalBoardStatus != BoardStatus.IN_PROGRESS
                && hypotheticalBoardStatus.hasPlayerWon(playerThatShouldWin)) {
            return coefficent;
        } else {
            if (isNextRivalMoveLeadsToFailure(hypotheticalPlayBoard, playerThatShouldWin.getRival())) {
                return -coefficent;
            }
        }
        return 0;
    }

    private boolean isNextRivalMoveLeadsToFailure(final PlayBoard currentPlayBoard, final Player rival) {
         for (PlayBoard hypotheticalRivalPlayBoard : getHypotheticalPlayBoards(currentPlayBoard, rival)) {
            final BoardStatus hypotheticalRivalPlayBoardStatus = hypotheticalRivalPlayBoard.getStatus();

            if (hypotheticalRivalPlayBoardStatus != BoardStatus.IN_PROGRESS &&
                    hypotheticalRivalPlayBoardStatus.hasPlayerWon(rival)) {
                return true;
            }
        }
        return false;
    }

    private List<PlayBoard> getHypotheticalPlayBoards(final PlayBoard currentPlayBoard, final Player player) {
        final List<PlayBoard> hypotheticalPlayBoards = new ArrayList<>();
        for (PlayerMove playerMove : currentPlayBoard.getValidMoves()) {
            final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard,
                    player, playerMove);
                    hypotheticalPlayBoards.add(hypotheticalPlayBoard);
        }
        return hypotheticalPlayBoards;
    }


}
