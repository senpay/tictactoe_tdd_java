package gmail.alexspush.controller;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.view.BoardView;
import gmail.alexspush.view.UserActionListener;

public class GameController {

    private PlayBoard playBoard;
    private BoardView boardView;
    private UserActionListener userActionListener;

    public GameController(final PlayBoard playBoard, final BoardView boardView) {
        this.playBoard = playBoard;
        this.boardView = boardView;
        this.userActionListener = new ControllerUserActionListener();
        this.boardView.addUserInputListener(this.userActionListener);
    }

    UserActionListener getUserActionListener() {
        return userActionListener;
    }


    PlayBoard getPlayBoard() {
        return playBoard;
    }

    BoardView getBoardView() {
        return boardView;
    }

    public class ControllerUserActionListener implements UserActionListener{
        @Override
        public void moveActionPerformed(final int x, final int y, final Player player) {
            playBoard.setPlayField(x, y, player.getPlayFieldValue());
        }
    }
}
