package gmail.alexspush.model;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import gmail.alexspush.AssertUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class PlayBoardTest {

    private PlayBoard playBoard;

    @DataProvider
    public static Object[][] updateBoardPointsAndValues() {
        return new Object[][]{
                {0, 0, PlayField.O},
                {1, 1, PlayField.X},
                {2, 2, PlayField.O}
        };
    }

    @DataProvider
    public static Object[][] fromZeroToTwo() {
        return new Object[][]{
                {0},
                {1},
                {2}
        };
    }

    @Before
    public void setUp() {
        playBoard = new PlayBoard();
    }

    @Test
    public void testConstructor() {
        AssertUtils.assertBoardEmpty(playBoard);
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            for (int y = 0; y < PlayBoard.SIZE; y++) {
                PlayField playField = playBoard.getPlayField(x, y);
                Assert.assertNotNull(playField);
                Assert.assertEquals(PlayField.EMPTY, playField);
            }
        }
    }

    @Test
    public void testBoardClean() {
        playBoard.setPlayField(1, 2, PlayField.X);
        playBoard.clean();
        AssertUtils.assertBoardEmpty(playBoard);
    }

    @Test
    @UseDataProvider("fromZeroToTwo")
    public void testBoardStatusXWinsVertical(final int x) {
        //Set up X to win the game (horizontal)
        for (int y = 0; y < PlayBoard.SIZE; y++) {
            playBoard.setPlayField(x, y, PlayField.X);
        }
        Assert.assertEquals(BoardStatus.XWINS, playBoard.getStatus());
    }

    @Test
    @UseDataProvider("fromZeroToTwo")
    public void testBoardStatusOWinsHorizontal(final int y) {
        //Set up O to win the game (vertical)
        for (int x = 0; x < PlayBoard.SIZE; x++) {
            playBoard.setPlayField(x, y, PlayField.O);
        }
        Assert.assertEquals(BoardStatus.OWINS, playBoard.getStatus());
    }


    @Test
    public void testBoardStatusOWinsDiagonalTopLeft() {
        //Set up O to win the game
        //[ o . . ]
        //[ . o . ]
        //[ . . o ]
        playBoard.setPlayField(0, 0, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.O);
        playBoard.setPlayField(2, 2, PlayField.O);
        Assert.assertEquals(BoardStatus.OWINS, playBoard.getStatus());
    }

    @Test
    public void testBoardStatusXWinsDiagonalTopRight() {
        //Set up O to win the game
        //[ . . x ]
        //[ . x . ]
        //[ x . . ]
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(2, 0, PlayField.X);
        Assert.assertEquals(BoardStatus.XWINS, playBoard.getStatus());
    }


    @Test
    public void testBoardStatusDraw() {
        //Set up board filled but nobody won
        //[ x o x ]
        //[ o x o ]
        //[ o x o ]
        playBoard.setPlayField(0, 0, PlayField.X);
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 0, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);
        playBoard.setPlayField(2, 2, PlayField.O);
        Assert.assertEquals(BoardStatus.DRAW, playBoard.getStatus());
    }

    @Test
    public void testBoardStatusInProgressTwoTwoEmpty() {
        //Set up game in progress
        //Set up board filled but nobody won
        //[ x o x ]
        //[ o x o ]
        //[ o x . ]
        playBoard.setPlayField(0, 0, PlayField.X);
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 0, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 2, PlayField.O);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);
        Assert.assertEquals(BoardStatus.IN_PROGRESS, playBoard.getStatus());
    }

    @Test
    public void testBoardStatusInProgressOneOneEmpty() {
        //Set up game in progress
        //Set up board filled but nobody won
        //[ x o x ]
        //[ o . o ]
        //[ o x o ]
        playBoard.setPlayField(0, 0, PlayField.X);
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 0, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);
        playBoard.setPlayField(2, 2, PlayField.O);
        Assert.assertEquals(BoardStatus.IN_PROGRESS, playBoard.getStatus());
    }

    @Test
    public void testBoardStatusInProgressZeroZeroEmpty() {
        //Set up game in progress
        //Set up board filled but nobody won
        //[ . o x ]
        //[ o x o ]
        //[ o x o ]
        playBoard.setPlayField(0, 1, PlayField.O);
        playBoard.setPlayField(0, 2, PlayField.X);
        playBoard.setPlayField(1, 0, PlayField.O);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(1, 1, PlayField.X);
        playBoard.setPlayField(2, 0, PlayField.O);
        playBoard.setPlayField(2, 1, PlayField.X);
        playBoard.setPlayField(2, 2, PlayField.O);
        Assert.assertEquals(BoardStatus.IN_PROGRESS, playBoard.getStatus());
    }

    @Test
    @UseDataProvider("updateBoardPointsAndValues")
    public void testUpdateBoard(final int x, final int y, final PlayField newFieldValue) {
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
        final int x = 1;
        final int y = 1;
        playBoard.setPlayField(x, y, PlayField.X);
        Assert.assertEquals(PlayField.X, playBoard.getPlayField(x, y));
        playBoard.setPlayField(x, y, PlayField.EMPTY);
        Assert.assertEquals(PlayField.EMPTY, playBoard.getPlayField(x, y));
    }

    @Test
    public void testEmptyBoardToString() {
        final String expectedBoard = "[ . . . ]\n[ . . . ]\n[ . . . ]\n";
        final String actualBoard = playBoard.toString();
        Assert.assertNotNull(actualBoard);
        Assert.assertEquals(expectedBoard, actualBoard);
    }

}

