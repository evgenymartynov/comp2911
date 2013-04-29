import graph.Node;

import java.util.HashMap;

public class AStarStrat<T extends Comparable<?>> extends DijkstraStrat<T> {
    public AStarStrat(HashMap<String, Integer> heuristicEstimates) {
        super();
        this.estimates = heuristicEstimates;
    }

    @Override
    public boolean addNode(Node<T> node, int distance) {
        return super.addNode(node, distance + estimates.get(node.getLabel()));
    }

    @Override
    public Node<T> popNode() {
        Node<T> node = super.popNode();
        System.out.println("Expanding <" + node.getLabel() + ">");
        return node;
    }

    private HashMap<String, Integer> estimates;
}
