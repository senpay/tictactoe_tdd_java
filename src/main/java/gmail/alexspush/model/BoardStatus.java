package gmail.alexspush.model;

import java.util.Optional;

/**
 * Created by senpay on 20.6.17.
 */
public enum BoardStatus {
    DRAW("Draw", Optional.empty()),
    XWINS("X wins", Optional.of(Player.X)),
    OWINS("O wins", Optional.of(Player.O)),
    IN_PROGRESS("In progress", Optional.empty());

    private final String statusText;
    private final Optional<Player> winner;

    BoardStatus(final String statusText, final Optional<Player> winner) {
        this.statusText = statusText;
        this.winner = winner;
    }

    public String getStatusText() {
        return statusText;
    }

    public boolean hasPlayerWon(final Player player) {
        return winner.isPresent() && winner.get().equals(player);
    }
}
