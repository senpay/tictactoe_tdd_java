package gmail.alexspush.view;

import org.junit.Assert;
import org.junit.Test;

public class ConsoleBoardViewTest {

    @Test
    public void testConstructor() {
        ConsoleBoardView view = new ConsoleBoardView();
        Assert.assertTrue(view instanceof BoardView);
        Assert.assertEquals(System.out, view.getOutputStream());
    }

    @Test
    public void testAddUserInputListener() {
        ConsoleBoardView view = new ConsoleBoardView();
        UserActionListener listener = () -> {};
        view.addUsertInputListener(listener);
        Assert.assertEquals(listener, view.getInputListener());
    }


}
