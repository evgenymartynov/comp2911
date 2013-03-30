import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Implementation of the Graph interface using adjacency list for graph
 * representation.
 */
public class AdjacencyListGraph implements Graph {
	public AdjacencyListGraph() {
		nodeToIntegerMap = new ArrayList<Object>();
		adjacencyList = new ArrayList<LinkedList<Edge>>();
	}

	@Override
	public void addNode(Object newNode) {
		nodeToIntegerMap.add(newNode);
		adjacencyList.add(new LinkedList<Edge>());
	}

	@Override
	public void addEdge(Object from, Object to, int weight) {
		int fromNum = lookupNode(from);
		int toNum = lookupNode(to);

		adjacencyList.get(fromNum).add(new Edge(fromNum, toNum, weight));
	}

	@Override
	public boolean adjacent(Object from, Object to) {
		int fromNum = lookupNode(from);
		int toNum = lookupNode(to);

		for (Edge edge : adjacencyList.get(fromNum))
			if (edge.getTo() == toNum)
				return true;
		return false;
	}

	@Override
	public int shortestPathBetween(Object from, Object to) {
		int fromNum = lookupNode(from);
		int toNum = lookupNode(to);

		// We pull out all edges into a single list.
		ArrayList<Edge> allEdges = new ArrayList<Edge>();
		for (LinkedList<Edge> list : adjacencyList)
			allEdges.addAll(list);

		// We want to see the distances after Bellman-Ford is finished, so
		// allocate the array here and pass it in.
		int numNodes = adjacencyList.size();
		int distance[] = new int[numNodes];

		boolean success = runBellmanFord(distance, allEdges, fromNum);

		// Return an appropriate result.
		if (!success)
			return -2;
		if (distance[toNum] >= INFINITY)
			return -1;
		return distance[toNum];
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
	private boolean runBellmanFord(int[] distance, ArrayList<Edge> edges,
			int from) {
		final int numNodes = distance.length;

		// Prepare.
		for (int i = 0; i < numNodes; i++)
			distance[i] = (i == from ? 0 : INFINITY);

		// Run the rounds.
		boolean stillGoing = false;

		for (int round = 0; round < numNodes; round++) {
			stillGoing = false;

			for (Edge edge : edges) {
				// Skip over unexpanded nodes.
				if (distance[edge.getFrom()] >= INFINITY)
					continue;

				int newLength = distance[edge.getFrom()] + edge.getWeight();
				if (distance[edge.getTo()] > newLength) {
					distance[edge.getTo()] = newLength;
					stillGoing = true;
				}
			}
		}

		// Return true if we found some shortest path.
		// Clearly false in case if negative cycles.
		return !stillGoing;
	}

	/**
	 * Converts a given node into a node number internal to this implementation.
	 * 
	 * @throws IllegalArgumentException
	 *             If such node is not found in the graph.
	 * 
	 * @param node
	 *            Node to convert.
	 * @return Index of the node.
	 */
	private int lookupNode(Object node) {
		int index = nodeToIntegerMap.indexOf(node);
		if (index < 0)
			throw new IllegalArgumentException(
					"Trying to manipulate node that has not been added to the graph");
		return index;
	}

	// Allows conversion between external node types and our internal integer
	// mapping.
	private ArrayList<Object> nodeToIntegerMap;

	// Adjacency list, using our internal representation of nodes and edges.
	private ArrayList<LinkedList<Edge>> adjacencyList;

	// One billion ought to be enough for anybody.
	final private int INFINITY = (int) 1e9;
}
