package gmail.alexspush;

public class PlayBoard {

    public static final int SIZE = 3;

    private PlayField[][] playFields = new PlayField[SIZE][SIZE];

    public PlayBoard() {
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                playFields[x][y] = PlayField.EMPTY;
            }
        }
    }

    public PlayField getPlayField(final int x, final int y) {
        return playFields[x][y];
    }

    public void setPlayField(final int x, final int y, final PlayField newPlayField) {
        playFields[x][y] = newPlayField;
    }
}
