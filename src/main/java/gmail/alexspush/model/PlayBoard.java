package gmail.alexspush.model;

import java.util.HashSet;
import java.util.Set;

public class PlayBoard {

    public static final int SIZE = 3;

    private final PlayField[][] playFields = new PlayField[SIZE][SIZE];

    public PlayBoard() {
        clean();
    }

    public PlayBoard(final PlayBoard playBoard) {
        for (int i = 0; i < playBoard.SIZE; i++) {
            for (int j = 0; j < playBoard.SIZE; j++) {
                this.playFields[i][j] = playBoard.getPlayField(i, j);
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

    public BoardStatus getStatus() {
        if (hasPlayerWon(Player.X)) {
            return BoardStatus.XWINS;
        } else if (hasPlayerWon(Player.O)) {
            return BoardStatus.OWINS;
        } else if (isDraw()) {
            return BoardStatus.DRAW;
        } else {
            return BoardStatus.IN_PROGRESS;
        }
    }

    private boolean isDraw() {
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                PlayField currentPlayField = getPlayField(x, y);
                if (currentPlayField == PlayField.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<PlayerMove> getValidMoves() {
        Set<PlayerMove> validMoves = new HashSet<>();
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                if (getPlayField(x, y) == PlayField.EMPTY) {
                    validMoves.add(new PlayerMove(x, y));
                }
            }
        }
        return validMoves;
    }

    private boolean hasPlayerWon(final Player player) {
        return hasPlayerFilledAnyRow(player) || hasPlayerFilledAnyColumn(player) || hasPlayerFilledAnyDiagonal(player);
    }

    private boolean hasPlayerFilledAnyRow(final Player player) {
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            boolean rawFilled = true;

            for (int y = 0; y < PlayBoard.SIZE; y++) {
                PlayField currentPlayField = getPlayField(x, y);
                if (currentPlayField != player.getPlayFieldValue()) {
                    rawFilled = false;
                }
            }

            //If rawFilled is still true - that means raw contained only player's signs and we can return true
            if (rawFilled) {
                return true;
            }
        }
        //If we haven't yet returned - then non row was filled with player's signs
        return false;
    }

    //TODO that method should be identical to rows but have different dimensions, right?
    private boolean hasPlayerFilledAnyColumn(final Player player) {
        for (int y = 0; y < PlayBoard.SIZE; y++) {
            boolean columnFilled = true;

            for (int x = 0; x < PlayBoard.SIZE; x++) {
                PlayField currentPlayField = getPlayField(x, y);
                if (currentPlayField != player.getPlayFieldValue()) {
                    columnFilled = false;
                }
            }

            //If columnFilled is still true - that means column contained only player's signs and we can return true
            if (columnFilled) {
                return true;
            }
        }
        //If we haven't yet returned - then non column was filled with player's signs
        return false;
    }

    private boolean hasPlayerFilledAnyDiagonal(final Player player) {
        boolean diagonalFilled = true;
        for (int xy = 0; xy < PlayBoard.SIZE; xy++) {
            PlayField currentPlayField = getPlayField(xy, xy);
            if (currentPlayField != player.getPlayFieldValue()) {
                diagonalFilled = false;
            }
        }

        //If this is still true - than diagonal was filled, returning true
        if (diagonalFilled) {
            return true;
        }

        //If not returned yet - checking second diagonal
        diagonalFilled = true;
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            int y = (PlayBoard.SIZE - 1) - x;
            PlayField currentPlayField = getPlayField(x, y);
            if (currentPlayField != player.getPlayFieldValue()) {
                diagonalFilled = false;
            }
        }

        //If this is still true - than diagonal was filled, returning true
        if (diagonalFilled) {
            return true;
        }

        //If we haven't returned yet - than non diagonal was filled, returing false
        return false;
    }

}
