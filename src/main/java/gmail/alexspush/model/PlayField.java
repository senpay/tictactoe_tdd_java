package gmail.alexspush.model;

public enum PlayField {
    EMPTY("."),
    X("x"),
    O("o");

    private String strValue;

    PlayField(final String strValue) {
        this.strValue = strValue;
    }

    @Override
    public String toString() {
        return strValue;
    }
}
