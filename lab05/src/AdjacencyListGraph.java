import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementation of the Graph interface using adjacency lists for graph
 * representation.
 */
public class AdjacencyListGraph<T> implements Graph<T> {
	public AdjacencyListGraph() {
		nodes = new ArrayList<Node<T>>();
	}

	@Override
	public void addNode(T label) {
		nodes.add(new Node<T>(label));
	}

	@Override
	public void addEdge(T fromLabel, T toLabel, int weight) {
		Node<?> from = lookupNode(fromLabel);
		Node<?> to = lookupNode(toLabel);

		from.addEdge(to, weight);
	}

	@Override
	public boolean adjacent(T fromLabel, T toLabel) {
		Node<?> from = lookupNode(fromLabel);
		Node<?> to = lookupNode(toLabel);

		for (Edge edge : from.getEdges())
			if (edge.getTo() == to)
				return true;
		return false;
	}

	@Override
	public boolean isStronglyConnected() {
		// An empty graph is strongly connected.
		if (nodes.size() == 0)
			return true;

		// We run Bellman-Ford from any node and see if we manage to hit
		// everything.
		HashMap<Node<?>, Integer> distance = new HashMap<Node<?>, Integer>();
		runBellmanFord(distance, nodes.get(0));
		for (Integer d : distance.values())
			if (d >= INFINITY)
				return false;
		return true;
	}

	@Override
	public int shortestPathBetween(T fromLabel, T toLabel) {
		Node<?> from = lookupNode(fromLabel);
		Node<?> to = lookupNode(toLabel);

		// We want to see the distances after Bellman-Ford is finished, so
		// allocate this cache here and pass it in.
		HashMap<Node<?>, Integer> distance = new HashMap<Node<?>, Integer>();
		boolean success = runBellmanFord(distance, from);

		// Return an appropriate result.
		if (!success)
			return -2;
		if (distance.get(to) >= INFINITY)
			return -1;
		return distance.get(to);
	}

	/**
	 * Runs the Bellman-Ford algorithm on the given data.
	 *
	 * @param distance
	 *            Output parameter: gets populated with best distance to each
	 *            node.
	 * @param edges
	 *            Edges which are considered in the calculations.
	 * @param from
	 *            Starting node.
	 * @return Whether or not the calculation succeeded. It will only fail in
	 *         case of negative cycles.
	 */
	private boolean runBellmanFord(HashMap<Node<?>, Integer> distance, Node<?> from) {
		// Prepare.
		for (Node<?> node : nodes)
			distance.put(node, (from.equals(node) ? 0 : INFINITY));

		// Run the rounds.
		final int numRounds = nodes.size();
		boolean stillGoing = false;

		for (int round = 0; round < numRounds; round++) {
			stillGoing = false;

			for (Node<?> expandingNode : nodes) {
				// Skip over unexpanded nodes.
				if (distance.get(expandingNode) >= INFINITY)
					continue;

				for (Edge edge : expandingNode.getEdges()) {
					int newLength = distance.get(expandingNode)
							+ edge.getWeight();
					if (distance.get(edge.getTo()) > newLength) {
						distance.put(edge.getTo(), newLength);
						stillGoing = true;
					}
				}
			}
		}

		// Return true if we found some shortest path.
		// Clearly false in case if negative cycles.
		return !stillGoing;
	}

	/**
	 * Gets a node with a given node label.
	 *
	 * @throws IllegalArgumentException
	 *             If such node is not found in the graph.
	 *
	 * @param label
	 *            Label to look up.
	 * @return Node with the given label.
	 */
	private Node<?> lookupNode(T label) {
		for (Node<?> node : nodes)
			if (node.getLabel().equals(label))
				return node;

		throw new IllegalArgumentException(
				"Trying to manipulate node that has not been added to the graph");
	}

	// Allows conversion between external node types and our internal integer
	// mapping.
	private ArrayList<Node<T>> nodes;

	// One billion ought to be enough for anybody.
	final private int INFINITY = (int) 1e9;
}
