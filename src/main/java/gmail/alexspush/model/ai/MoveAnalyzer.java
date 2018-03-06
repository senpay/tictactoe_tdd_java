package gmail.alexspush.model.ai;

import gmail.alexspush.model.BoardStatus;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 6.3.18
 */
public class MoveAnalyzer {

    private static final int WEIGHT = 10;
    private Player playerThatShouldWin;

    public MoveAnalyzer(final Player playerThatShouldWin) {
        this.playerThatShouldWin = playerThatShouldWin;
    }

    public int getMoveWeight(final PlayerMove moveToAnalyze, final PlayBoard currentPlayBoard) {
        final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(currentPlayBoard, playerThatShouldWin,
                moveToAnalyze);
        final BoardStatus hypotheticalBoardStatus = hypotheticalPlayBoard.getStatus();
        System.out.println("currentPlayBoard");
        System.out.println(currentPlayBoard);
        System.out.println("hypotheticalPlayBoard");
        System.out.println(hypotheticalPlayBoard);

        if (hypotheticalBoardStatus != BoardStatus.IN_PROGRESS) {
            final int weight = assessMoveWeight(hypotheticalBoardStatus, 0);
            System.out.println(weight);
            return weight;
        }

        //If we don't win in this turn - iterate on all possibilities
        return analyzeMoves(hypotheticalPlayBoard, playerThatShouldWin.getRival(), 1);
    }

    private int assessMoveWeight(final BoardStatus boardStatus, int depth) {
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

    private static PlayBoard getHypatheticalPlayBoard(final PlayBoard currentPlayBoard, final Player playerToMakeMove,
                                                      final PlayerMove nextMove) {
        final PlayBoard hypotheticalPlayBoard = new PlayBoard(currentPlayBoard);
        hypotheticalPlayBoard.setPlayField(nextMove.x, nextMove.y, playerToMakeMove.getPlayFieldValue());
        return hypotheticalPlayBoard;
    }

    private int analyzeMoves(final PlayBoard playBoardStatusToAnalyze, final Player currentPlayer, final int depth) {
        System.out.println("currentPlayBoard");
        System.out.println(playBoardStatusToAnalyze);
        for (PlayerMove validMove : playBoardStatusToAnalyze.getValidMoves()) {
            final PlayBoard hypotheticalPlayBoard = getHypatheticalPlayBoard(playBoardStatusToAnalyze, currentPlayer,
                    validMove);
            final BoardStatus playBoardStatus = hypotheticalPlayBoard.getStatus();
            System.out.println("hypotheticalPlayBoard");
            System.out.println(hypotheticalPlayBoard);
            if(playBoardStatus != BoardStatus.IN_PROGRESS) {
                final int weight = assessMoveWeight(playBoardStatus, depth);
                System.out.println(weight);
                return weight;
            }

            return analyzeMoves(hypotheticalPlayBoard, currentPlayer.getRival(), depth + 1);
        }
        throw new IllegalStateException("We really should not end up here");
    }
}
