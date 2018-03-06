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

    //It may be possible to use lamdba here, although I don't know if it going to make
    //thing more readable
    private static PlayerMove getMoveWithMaximumScore(final List<AnalyzedMove> analyzedMoves) {
        Optional<AnalyzedMove> bestMove =
                analyzedMoves.stream().max((x, y) -> (x.getWeight() > y.getWeight()) ? 1 : -1);

        if (!bestMove.isPresent()) {
            throw new IllegalStateException("seems that analyzedMoves list was empty, should never be the case");
        }

        return bestMove.get().getMove();
    }

    //TODO: Horrible method, needs refactoring
    private static void analyzeMoves(final AnalyzedMove moveUnderAnalyzis, final PlayBoard playBoardStatusToAnalyze,
                                     final Player playerThatShouldWin, final Player currentPlayer) {
        //Decrease weight for each iterationg for move
        moveUnderAnalyzis.decreaseWeight();
        final Set<PlayerMove> validMoves = playBoardStatusToAnalyze.getValidMoves();
        for (PlayerMove validMove : validMoves) {
            final PlayBoard hypotheticalPlayBoard = new PlayBoard(playBoardStatusToAnalyze);
            hypotheticalPlayBoard.setPlayField(validMove.x, validMove.y, currentPlayer.getPlayFieldValue());
            final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

            if(hypotheticalBoardStatus != BoardStatus.IN_PROGRESS) {
                if (hypotheticalBoardStatus.getWinner().isPresent() &&
                        playerThatShouldWin == hypotheticalBoardStatus.getWinner().get()) {
                    //We won
                    moveUnderAnalyzis.significantlyIncreaseWeight();
                } else if (hypotheticalBoardStatus.getWinner().isPresent() &&
                        playerThatShouldWin != hypotheticalBoardStatus.getWinner().get()) {
                    //We lost
                    moveUnderAnalyzis.significantlyDecreaseWeight();
                }
                //game is over, no need for further analysis
                return;
            }

            analyzeMoves(moveUnderAnalyzis, hypotheticalPlayBoard, playerThatShouldWin,
                    playerThatShouldWin.getRival());
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
            final PlayBoard hypotheticalPlayBoard = new PlayBoard(currentPlayBoard);
            final PlayerMove playerMove = moveToAnalyze.getMove();
            hypotheticalPlayBoard.setPlayField(playerMove.x, playerMove.y,
                    playerThatShouldWin.getPlayFieldValue());
            final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();

            if (hypotheticalBoardStatus.getWinner().isPresent() &&
                    playerThatShouldWin == hypotheticalBoardStatus.getWinner().get()) {
                //We won
                moveToAnalyze.significantlyIncreaseWeight();
                //game is over, no need for further analysis
                return;
            }

            //If we don't win in this turn - iterate on all possibilities
            analyzeMoves(moveToAnalyze, hypotheticalPlayBoard, playerThatShouldWin,
                    playerThatShouldWin.getRival());

        }
    }

}
