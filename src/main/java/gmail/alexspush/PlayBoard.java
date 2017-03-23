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

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int x = 0; x < 3; x++) {
            stringBuffer.append("[");
            for (int y = 0; y < 3; y++) {
                stringBuffer.append(" ");

                stringBuffer.append(playFields[x][y]);
            }
            stringBuffer.append(" ]\n");
        }
        return stringBuffer.toString();
    }
}
