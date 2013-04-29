import graph.Node;

import java.util.HashMap;

public class AStarStrat<T> extends DijkstraStrat<T> {
    public AStarStrat(HashMap<String, Integer> heuristicEstimates) {
        super();
        this.estimates = heuristicEstimates;
    }

    @Override
    protected Wrapper<T> fabricateNode(Node<T> node, int distance) {
        return new Wrapper<T>(node, distance, estimates.get(node.getLabel()));
    }

    @Override
    public Node<T> popNode() {
        Node<T> node = super.popNode();
        System.out.println("Expanding <" + node.getLabel() + ">");
        return node;
    }

    private HashMap<String, Integer> estimates;
}
