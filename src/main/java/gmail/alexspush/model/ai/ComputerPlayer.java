package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;

/**
 * Created by Alexander Pushkarev.
 * alexspush@gmail.com
 * 2.3.18
 */
public interface ComputerPlayer {

    PlayerMove getMove(final PlayBoard playBoard, final Player player);

}
