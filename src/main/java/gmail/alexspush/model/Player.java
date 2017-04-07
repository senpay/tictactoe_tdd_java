package gmail.alexspush.model;

public enum Player {
    X(PlayField.X),
    O(PlayField.O);

    private PlayField playFieldValue;

    Player(final PlayField playFieldValue) {
        this.playFieldValue = playFieldValue;
    }

    public PlayField getPlayFieldValue() {
        return playFieldValue;
    }
}
