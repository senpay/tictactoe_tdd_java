package gmail.alexspush.view;

import java.io.OutputStream;

public class ConsoleBoardView implements BoardView {

    private OutputStream outputStream = System.out;
    private UserActionListener userActionListener;

    @Override
    public void showBoard() {

    }

    @Override
    public void addUserInputListener(final UserActionListener userActionListener) {
        this.userActionListener = userActionListener;
    }

    OutputStream getOutputStream() {
        return outputStream;
    }


    UserActionListener getInputListener() {
        return userActionListener;
    }
}
