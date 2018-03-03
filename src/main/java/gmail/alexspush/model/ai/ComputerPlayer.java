package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayerMove;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 2.3.18
 */
public interface ComputerPlayer {

    PlayerMove getMove(final PlayBoard playBoard);

}
