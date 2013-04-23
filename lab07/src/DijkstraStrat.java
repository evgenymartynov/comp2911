import graph.Node;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;


public class DijkstraStrat<T extends Comparable<?>> implements SearchStrat<T> {
    public DijkstraStrat() {
        reset();
    }

    @Override
    public boolean addNode(Node<T> node, int distance) {
        if (!visited.contains(node)) {
            visited.add(node);
            queue.add(new Wrapper<T>(node, distance));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public Node<T> popNode() {
        return queue.poll().getNode();
    }

    @Override
    public int getCost() {
        return queue.peek().getDistance();
    }

    @Override
    public void reset() {
        queue = new PriorityQueue<Wrapper<T>>();
        visited = new HashSet<Node<T>>();
    }

    private Queue<Wrapper<T>> queue;
    private HashSet<Node<T>> visited;
}
