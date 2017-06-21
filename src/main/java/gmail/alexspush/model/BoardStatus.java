package gmail.alexspush.model;

/**
 * Created by senpay on 20.6.17.
 */
public enum BoardStatus {
    DRAW("Draw"),
    XWINS("X wins"),
    OWINS("O wins"),
    IN_PROGRESS("In progress");

    private final String statusText;

    BoardStatus(final String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }
}
