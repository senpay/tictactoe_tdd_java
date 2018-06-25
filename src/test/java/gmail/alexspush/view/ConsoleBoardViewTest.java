package gmail.alexspush.view;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import gmail.alexspush.AssertUtils;
import gmail.alexspush.controller.GameController;
import gmail.alexspush.controller.QuitException;
import gmail.alexspush.model.PlayBoard;
import gmail.alexspush.model.PlayField;
import gmail.alexspush.model.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
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
        String actualBoard = testStream.getWrittenOutput();
        Assert.assertNotNull(actualBoard);
        Assert.assertEquals(expectedBoard, actualBoard);

    }

    @Test
    public void testAddUserInputListener() {
        ConsoleBoardView view = new ConsoleBoardView();
        UserActionListener listener = new UserActionListener() {
            @Override
            public void moveActionPerformed(final int x, final int y, final Player player) {
            }
            @Override
            public void newGameActionPerformed() {
            }
            @Override
            public void quitActionPerformed() {
            }
        };

        view.addUserInputListener(listener);
        Assert.assertEquals(listener, view.getInputListener());
    }

    @Test
    public void testUserMove() throws IOException {
        TestInputStream testInputStream = new TestInputStream("m12\n");
        ConsoleBoardView view = new ConsoleBoardView(testInputStream);
        PlayBoard playBoard = new PlayBoard();
        GameController controller = new GameController(playBoard, view);
        view.addUserInputListener(controller.getUserActionListener());
        view.render();
        Assert.assertEquals(PlayField.X, playBoard.getPlayField(1, 2));
    }

    @Test
    public void testIncorrectUserCommand() throws IOException {
        TestInputStream testInputStream = new TestInputStream("x12\n");
        ConsoleBoardView view = new ConsoleBoardView(testInputStream);
        PlayBoard playBoard = new PlayBoard();
        GameController controller = new GameController(playBoard, view);
        view.addUserInputListener(controller.getUserActionListener());
        view.render();
        AssertUtils.assertBoardEmpty(playBoard);
    }

    @Test
    public void testUserNew() throws IOException {
        TestInputStream testInputStream = new TestInputStream("n\n");
        ConsoleBoardView view = new ConsoleBoardView(testInputStream);
        PlayBoard playBoard = new PlayBoard();
        playBoard.setPlayField(1, 2, PlayField.O);
        GameController controller = new GameController(playBoard, view);
        view.addUserInputListener(controller.getUserActionListener());
        view.render();
        AssertUtils.assertBoardEmpty(playBoard);
    }

    @Test
    public void testShowMessage() throws IOException {
        TestOutputStream testStream = new TestOutputStream();
        ConsoleBoardView view = new ConsoleBoardView(testStream);
        Exception exception = new Exception();
        view.showMessage(exception.toString());
        String actualExceptionMessage = testStream.getWrittenOutput();
        Assert.assertNotNull(actualExceptionMessage);
        Assert.assertEquals(exception.toString(), actualExceptionMessage);
    }

    @Test(expected = QuitException.class)
    public void testUserQuit() throws IOException {
        TestInputStream testInputStream = new TestInputStream("q\n");
        ConsoleBoardView view = new ConsoleBoardView(testInputStream);
        PlayBoard playBoard = new PlayBoard();
        GameController controller = new GameController(playBoard, view);
        view.addUserInputListener(controller.getUserActionListener());
        view.render();
    }

    private class TestInputStream extends InputStream {

        private final char[] inputStrChars;
        private int counter;

        public TestInputStream(final String inputStr) {
            this.inputStrChars = inputStr.toCharArray();
            counter = 0;
        }

        @Override
        public int read() throws IOException {
            if(counter < inputStrChars.length) {
                return inputStrChars[counter++];
            } else {
                return -1;
            }
        }

        @Override
        public int available() throws IOException {
            return inputStrChars.length - counter;
        }
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
    }
}
