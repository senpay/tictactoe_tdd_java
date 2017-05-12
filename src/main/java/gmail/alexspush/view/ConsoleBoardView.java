package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConsoleBoardView implements BoardView {

    private OutputStream outputStream = System.out;
    private InputStream inputStream = System.in;
    private UserActionListener userActionListener;

    ConsoleBoardView(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    ConsoleBoardView(final InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public ConsoleBoardView() {
    }

    @Override
    public void showBoard(PlayBoard board) throws IOException {
        outputStream.write(board.toString().getBytes());
    }

    @Override
    public void render() throws IOException {

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
