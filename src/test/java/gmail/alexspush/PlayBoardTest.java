package gmail.alexspush;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class PlayBoardTest {

    @DataProvider
    public static Object[][] updateBoardPointsAndValues() {
        return new Object[][]{
                {0, 0, PlayField.O},
                {1, 1, PlayField.X},
                {2, 2, PlayField.O}
        };
    }

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

    @Test
    @UseDataProvider("updateBoardPointsAndValues")
    public void testUpdateBoard(final int x, final int y, final PlayField newFieldValue) {
        PlayBoard playBoard = new PlayBoard();
        //if testConstructor passes we will have all fields empty at this stage, so just updating field
        playBoard.setPlayField(x, y, newFieldValue);
        Assert.assertEquals(newFieldValue, playBoard.getPlayField(x, y));
        //testing that other values didn't change. Looks cumbersome, but still workable.
        for (int xx = 0; xx < PlayBoard.SIZE; xx++) {
            for (int yy = 0; yy < PlayBoard.SIZE; yy++) {
                if (xx == x && yy == y) {
                    continue;
                }
                PlayField playField = playBoard.getPlayField(xx, yy);
                Assert.assertEquals("is not empty field for x = " + xx + " y = " + yy, PlayField.EMPTY, playField);
            }
        }
    }

    @Test
    public void testUpdateUpdatedBoard() {
        PlayBoard playBoard = new PlayBoard();
        final int x = 1;
        final int y = 1;
        playBoard.setPlayField(x, y, PlayField.X);
        Assert.assertEquals(PlayField.X, playBoard.getPlayField(x, y));
        playBoard.setPlayField(x, y, PlayField.EMPTY);
        Assert.assertEquals(PlayField.EMPTY, playBoard.getPlayField(x, y));
    }
}

