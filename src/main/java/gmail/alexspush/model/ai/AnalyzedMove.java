package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayerMove;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 6.3.18
 */
public class AnalyzedMove {
    private int weight = 0;
    private final PlayerMove move;

    public AnalyzedMove(final PlayerMove move) {
        this.move = move;
    }

    public int getWeight() {
        return weight;
    }

    public void decreaseWeight() {
        weight--;
    }

    public void significantlyDecreaseWeight() {
        weight -= 100;
    }

    public void significantlyIncreaseWeight() {
        weight += 100;
    }

    public PlayerMove getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "AnalyzedMove{" +
                "weight=" + weight +
                ", move=" + move +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalyzedMove)) return false;

        final AnalyzedMove that = (AnalyzedMove) o;

        if (getWeight() != that.getWeight()) return false;
        return getMove() != null ? getMove().equals(that.getMove()) : that.getMove() == null;
    }

    @Override
    public int hashCode() {
        int result = getWeight();
        result = 31 * result + (getMove() != null ? getMove().hashCode() : 0);
        return result;
    }


}
