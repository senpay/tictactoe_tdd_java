package gmail.alexspush;

import org.junit.Assert;
import org.junit.Test;

public class PlayBoardTest {

    @Test
    public void testConstructor() {
        PlayBoard playBoard = new PlayBoard();
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                PlayField playField = playBoard.getPlayField(x, y);
                Assert.assertNotNull(playField);
                Assert.assertEquals(PlayField.EMPTY, playField);
            }
        }
    }

}

