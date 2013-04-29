import graph.Edge;
import graph.Graph;
import graph.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GraphSearcher<T> {
    public GraphSearcher(Graph<T> graph, SearchStrat<T> nodeSelector) {
        this.graph = graph;
        this.nodeSelector = nodeSelector;
    }

    public List<T> findPath(T from, T to) {
        nodeSelector.reset();
        nodeSelector.addNode(graph.getNodeFor(from), 0);

        HashMap<Node<T>, Node<T>> predecessor = new HashMap<Node<T>, Node<T>>();

        while (!nodeSelector.isEmpty()) {
            int cost = nodeSelector.getCost();
            Node<T> node = nodeSelector.popNode();

//            System.out.println(node.getLabel());

            if (node.getLabel().equals(to)) {
                System.out.println("Cost to desination " + cost);
                break;
            }

            for (Edge edge : node.getEdges()) {
                Node<T> next = (Node<T>) edge.getTo();
                if (nodeSelector.addNode(next, cost + edge.getWeight())) {
                    predecessor.put(next, node);
                }
            }
        }

        LinkedList<T> path = new LinkedList<T>();
        Node<T> startNode = graph.getNodeFor(from), at = graph.getNodeFor(to);

        do {
            Node<T> previous = predecessor.get(at);
            path.addFirst(at.getLabel());
            if (previous == null) {
                // Couldn't find a path.
                return null;
            }

            at = previous;
        } while(at != startNode);

        path.addFirst(startNode.getLabel());

        return path;
    }

    private Graph<T> graph;
    private SearchStrat<T> nodeSelector;
}
