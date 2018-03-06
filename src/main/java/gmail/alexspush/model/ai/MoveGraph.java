package gmail.alexspush.model.ai;

import gmail.alexspush.model.PlayerMove;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alexander Pushkarev.
 * apushkarev@workfusion.com
 * 6.3.18
 */
public class MoveGraph {
    private PlayerMove node;
    private Set<MoveGraph> childNodes = new HashSet<>() ;

    public MoveGraph(PlayerMove node) {
        this.node = node;
    }

    public PlayerMove getNode() {
        return node;
    }

    public void setNode(final PlayerMove node) {
        this.node = node;
    }

    public Collection<MoveGraph> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(final MoveGraph childNode) {
        this.childNodes.add(childNode);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MoveGraph)) return false;

        final MoveGraph moveGraph = (MoveGraph) o;

        if (getNode() != null ? !getNode().equals(moveGraph.getNode()) : moveGraph.getNode() != null) return false;
        return getChildNodes() != null ? getChildNodes().equals(moveGraph.getChildNodes()) : moveGraph.getChildNodes() == null;
    }

    @Override
    public int hashCode() {
        int result = getNode() != null ? getNode().hashCode() : 0;
        result = 31 * result + (getChildNodes() != null ? getChildNodes().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MoveGraph{" +
                "node=" + node +
                ", childNodes=" + childNodes +
                '}';
    }
}
