package gmail.alexspush.controller;

public class QuitException extends RuntimeException {

    static final long serialVersionUID = 1L;

    public QuitException(String message) {
        super(message);
    }
}
