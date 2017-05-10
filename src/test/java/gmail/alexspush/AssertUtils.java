package gmail.alexspush;

import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import org.junit.Assert;

public abstract class AssertUtils {

    public static void assertBoardEmpty(final PlayBoard board) {
        for (int xx = 0; xx < PlayBoard.SIZE; xx++) {
            for (int yy = 0; yy < PlayBoard.SIZE; yy++) {
                PlayField playField = board.getPlayField(xx, yy);
                Assert.assertEquals("is not empty field for x = " + xx + " y = " + yy, PlayField.EMPTY, playField);
            }
        }
    }
}
