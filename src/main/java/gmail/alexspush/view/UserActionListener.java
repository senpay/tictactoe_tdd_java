package gmail.alexspush.view;

import gmail.alexspush.model.Player;

public interface UserActionListener {
    void moveActionPerformed(int x, int y, Player player);
    void newGameActionPerformed();
    void quitActionPerformed();
}
