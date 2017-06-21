package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;

import java.io.*;

public class ConsoleBoardView implements BoardView {

    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedReader inputStreamReader;
    private UserActionListener userActionListener;

    ConsoleBoardView(final OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    ConsoleBoardView(final InputStream inputStream) {
        this.inputStream = inputStream;
        inputStreamReader = getBufferedReader(inputStream);
    }

    public ConsoleBoardView() {
        outputStream = System.out;
        inputStream = System.in;
        inputStreamReader = getBufferedReader(inputStream);
    }

    @Override
    public void showBoard(PlayBoard board) throws IOException {
        writeString(board.toString());
    }

    @Override
    public void render() throws IOException {
        String command = inputStreamReader.readLine();
        if (command.startsWith("m")) {
            int x = Integer.valueOf(command.substring(1, 2));
            int y = Integer.valueOf(command.substring(2, 3));
            userActionListener.moveActionPerformed(x, y, Player.X);
        } else if (command.startsWith("n")) {
            userActionListener.newGameActionPerformed();
        } else if (command.startsWith("q")) {
            userActionListener.quitActionPerformed();
        }
    }

    @Override
    public void showMessage(final String message) throws IOException {
        writeString(message);
    }

    private void writeString(final String str) throws IOException {
        outputStream.write(str.getBytes());
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

    private BufferedReader getBufferedReader(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
