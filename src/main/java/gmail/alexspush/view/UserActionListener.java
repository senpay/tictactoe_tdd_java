package gmail.alexspush.view;

import gmail.alexspush.model.Player;

public abstract class UserActionListener {
    public void moveActionPerformed(int x, int y, Player player) {}
    public void newGameActionPerformed() {}
    public void quitActionPerformed() {}
}
