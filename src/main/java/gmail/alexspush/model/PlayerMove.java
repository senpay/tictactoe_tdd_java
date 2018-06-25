package gmail.alexspush.model;

/**
 * Created by Alexander Pushkarev.
 * alexspush@gmail.com
 * 2.3.18
 */
public class PlayerMove {
    public int x;
    public int y;

    public PlayerMove(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayerMove)) return false;

        final PlayerMove that = (PlayerMove) o;

        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("x = %d, y = %d;", x, y);
    }
}
