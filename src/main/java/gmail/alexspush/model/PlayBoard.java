package gmail.alexspush.model;

public class PlayBoard {

    public static final int SIZE = 3;

    private final PlayField[][] playFields = new PlayField[SIZE][SIZE];

    public PlayBoard() {
        clean();
    }

    public PlayField getPlayField(final int x, final int y) {
        return playFields[x][y];
    }

    public void setPlayField(final int x, final int y, final PlayField newPlayField) {
        playFields[x][y] = newPlayField;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int x = 0; x < 3; x++) {
            stringBuilder.append("[");
            for (int y = 0; y < 3; y++) {
                stringBuilder.append(" ");

                stringBuilder.append(playFields[x][y]);
            }
            stringBuilder.append(" ]\n");
        }
        return stringBuilder.toString();
    }

    public void clean() {
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                playFields[x][y] = PlayField.EMPTY;
            }
        }
    }
}
