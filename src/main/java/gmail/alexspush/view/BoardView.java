package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;

import java.io.IOException;

public interface BoardView {
    void showBoard(PlayBoard board) throws IOException;
    void addUserInputListener(UserActionListener listener);
    void render() throws IOException;
    void showMessage(String message) throws IOException;
}
