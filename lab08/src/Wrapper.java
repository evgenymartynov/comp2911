import graph.Node;

class Wrapper<T> implements Comparable<Wrapper<T>> {
    public Wrapper(Node<T> node, int distance, int estimate) {
        this.node = node;
        this.distance = distance;
        this.estimate = estimate;
    }

    @Override
    public int compareTo(Wrapper<T> other) {
        return getPriority() - other.getPriority();
    }

    public Node<T> getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }

    public int getPriority() {
        return distance + estimate;
    }

    private Node<T> node;
    private int distance;
    private int estimate;
}