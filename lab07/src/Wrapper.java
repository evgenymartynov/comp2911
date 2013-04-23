import graph.Node;

class Wrapper<T> implements Comparable<Wrapper<T>> {
    public Wrapper(Node<T> node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Wrapper<T> other) {
        return distance - other.distance;
    }

    public Node<T> getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }

    private Node<T> node;
    private int distance;
}