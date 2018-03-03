package gmail.alexspush.view;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.Player;
import gmail.alexspush.model.PlayerMove;

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

    //This one of the methods that wasn't actually created using vanilla TDD...
    @Override
    public void render() throws IOException {
        String command = inputStreamReader.readLine();
        if (command.startsWith("m")) {
            final PlayerMove humanPlayerMove = getPlayerMoveFromCommand(command);
            userActionListener.moveActionPerformed(humanPlayerMove.x, humanPlayerMove.y,
                    Player.X);
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

    private PlayerMove getPlayerMoveFromCommand(final String commandStr) {
        final short x = Short.valueOf(commandStr.substring(1, 2));
        final short y = Short.valueOf(commandStr.substring(2, 3));
        return new PlayerMove(x, y);
    }
}
