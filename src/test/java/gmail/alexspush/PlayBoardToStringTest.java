package gmail.alexspush;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class PlayBoardToStringTest {

    private static PlayBoard playBoard;

    @BeforeClass
    public static void setUp() {
        playBoard = new PlayBoard();
    }

    @DataProvider
    public static Object[][] movesAndBoardStates() {
        return new Object[][]{
                {1, 1, PlayField.X, "[ . . . ]\n[ . x . ]\n[ . . . ]\n"},
                {0, 1, PlayField.O, "[ . o . ]\n[ . x . ]\n[ . . . ]\n"},
                {2, 2, PlayField.X, "[ . o . ]\n[ . x . ]\n[ . . x ]\n"},
                {0, 2, PlayField.O, "[ . o o ]\n[ . x . ]\n[ . . x ]\n"},
                {0, 0, PlayField.X, "[ x o o ]\n[ . x . ]\n[ . . x ]\n"},
        };
    }

    @Test
    @UseDataProvider("movesAndBoardStates")
    public void testBoardToString(final int x, final int y, final PlayField newFieldValue, final String expectedBoard) {
        playBoard.setPlayField(x, y, newFieldValue);
        final String actualBoard = playBoard.toString();
        Assert.assertNotNull(actualBoard);
        Assert.assertEquals(expectedBoard, actualBoard);
    }
}

