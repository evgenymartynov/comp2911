import graph.Node;

public interface SearchStrat<T> {
    public boolean addNode(Node<T> node, int distance);

    public Node<T> popNode();

    public int getCost();

    public boolean isEmpty();
    public void reset();
}
