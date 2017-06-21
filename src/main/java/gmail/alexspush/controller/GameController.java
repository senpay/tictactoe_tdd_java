package gmail.alexspush.controller;

import gmail.alexspush.model.BoardStatus;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.view.BoardView;
import gmail.alexspush.view.UserActionListener;

import java.io.IOException;

public class GameController {

    private final PlayBoard playBoard;
    private final BoardView boardView;
    private final UserActionListener userActionListener;

    public GameController(final PlayBoard playBoard, final BoardView boardView) {
        this.playBoard = playBoard;
        this.boardView = boardView;
        this.userActionListener = new ControllerUserActionListener();
        this.boardView.addUserInputListener(this.userActionListener);
    }

    public UserActionListener getUserActionListener() {
        return userActionListener;
    }

    public void start() throws IOException {
        try {
            while (true) {
                if (playBoard.getStatus() == BoardStatus.IN_PROGRESS) {
                    boardView.render();
                    boardView.showBoard(playBoard);
                } else {
                    boardView.showMessage("Game over: " + playBoard.getStatus().getStatusText() +
                            " . Restarting game\n");
                    playBoard.clean();
                }
            }
        } catch (Exception exception) {
            boardView.showMessage(exception.toString());
            System.out.println(exception);
            System.exit(0);
        }

    }


    PlayBoard getPlayBoard() {
        return playBoard;
    }

    BoardView getBoardView() {
        return boardView;
    }

    public class ControllerUserActionListener implements UserActionListener {
        @Override
        public void moveActionPerformed(final int x, final int y, final Player player) {
            playBoard.setPlayField(x, y, player.getPlayFieldValue());
        }

        @Override
        public void newGameActionPerformed() {
            playBoard.clean();
        }

        @Override
        public void quitActionPerformed() {
            throw new QuitException("exiting from game");
        }
    }
}
