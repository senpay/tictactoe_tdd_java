package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayerMove;

import java.util.List;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 2.3.18
 */
public class MiniMaxComputerPlayer implements ComputerPlayer {

    @Override
    public PlayerMove getMove(final PlayBoard playBoard) {
        final List<PlayerMove> validMoves = playBoard.getValidMoves();
        final int index = 1;
        return validMoves.get(index);
    }

}
