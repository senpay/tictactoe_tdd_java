package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;

import java.io.IOException;

public interface BoardView {
    void showBoard(PlayBoard board) throws IOException;
    void addUserInputListener(UserActionListener listener);
}
