package gmail.alexspush.view;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.OutputStream;

@RunWith(DataProviderRunner.class)
public class ConsoleBoardViewTest {

    @DataProvider
    public static Object[][] movesAndBoardStates() {
        return new Object[][]{
                {1, 1, PlayField.X, "[ . . . ]\n[ . x . ]\n[ . . . ]\n"},
                {0, 1, PlayField.O, "[ . o . ]\n[ . . . ]\n[ . . . ]\n"},
                {2, 2, PlayField.X, "[ . . . ]\n[ . . . ]\n[ . . x ]\n"},
                };
    }

    @Test
    public void testConstructor() {
        ConsoleBoardView view = new ConsoleBoardView();
        Assert.assertTrue(view instanceof BoardView);
        Assert.assertEquals(System.out, view.getOutputStream());
    }


    @Test
    @UseDataProvider("movesAndBoardStates")
    public void testShowBoard(final int x, final int y, final PlayField newFieldValue,
                              final String expectedBoard) throws IOException {
        PlayBoard playBoard = new PlayBoard();
        playBoard.setPlayField(x, y, newFieldValue);
        TestOutputStream testStream = new TestOutputStream();
        ConsoleBoardView view = new ConsoleBoardView(testStream);
        view.showBoard(playBoard);
        String actualBoard = playBoard.toString();
        Assert.assertNotNull(testStream.getWrittenOutput());
        Assert.assertEquals(expectedBoard, actualBoard);

    }

    @Test
    public void testAddUserInputListener() {
        ConsoleBoardView view = new ConsoleBoardView();
        UserActionListener listener = new UserActionListener() {};
        view.addUserInputListener(listener);
        Assert.assertEquals(listener, view.getInputListener());
    }

    private class TestOutputStream extends OutputStream {
        private StringBuilder builder = new StringBuilder();

        @Override
        public void write(final int b) throws IOException {
            builder.append((char) b);
        }

        public String getWrittenOutput() {
            return builder.toString();
        }

        public void clearOutput() {
            builder = new StringBuilder();
        }
    }
}