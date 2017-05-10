package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;

import java.io.IOException;
import java.io.OutputStream;

public class ConsoleBoardView implements BoardView {

    private OutputStream outputStream = System.out;
    private UserActionListener userActionListener;

    ConsoleBoardView(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public ConsoleBoardView() {
    }

    @Override
    public void showBoard(PlayBoard board) throws IOException {
        outputStream.write(board.toString().getBytes());
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
