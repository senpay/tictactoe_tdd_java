package gmail.alexspush.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexander Pushkarev.
 * alexspush@gmail.com
 * 3.3.18
 */
public class PlayerTest {

    @Test
    public void shouldReturnPlayFieldOForPlayerO() {
        final Player player = Player.O;
        assertEquals(PlayField.O, player.getPlayFieldValue());
    }

    @Test
    public void shouldReturnPlayFieldXForPlayerX() {
        final Player player = Player.X;
        assertEquals(PlayField.X, player.getPlayFieldValue());
    }

    @Test
    public void shouldReturnRivalPlayFieldXForPlayerO() {
        final Player player = Player.O;
        assertEquals(PlayField.X, player.getRivalPlayFieldValue());
    }

    @Test
    public void shouldReturnRivalPlayFieldOForPlayerX() {
        final Player player = Player.X;
        assertEquals(PlayField.O, player.getRivalPlayFieldValue());
    }

}