import graph.Node;

import java.util.HashSet;
import java.util.Stack;

public class DFSStrat<T> implements SearchStrat<T> {
    public DFSStrat() {
        reset();
    }

    @Override
    public boolean addNode(Node<T> node, int distance) {
        if (!visited.contains(node)) {
            visited.add(node);
            stack.push(new Wrapper<T>(node, distance, 0));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return stack.empty();
    }

    @Override
    public Node<T> popNode() {
        return stack.pop().getNode();
    }

    @Override
    public int getCost() {
        return stack.peek().getDistance();
    }

    @Override
    public void reset() {
        stack = new Stack<Wrapper<T>>();
        visited = new HashSet<Node<T>>();
    }

    private Stack<Wrapper<T>> stack;
    private HashSet<Node<T>> visited;
}
