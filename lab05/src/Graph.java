/**
 * Interface for directed graph implementations.
 *
 * Nodes are described by arbitrary Objects that must have a suitable .equals
 * method.
 *
 * Edges are allowed only between existing nodes in the graph. Edges have
 * weights and are directed.
 */
public interface Graph {
	/**
	 * Adds a new node to the graph.
	 *
	 * @param newNode
	 *            Node to add.
	 */
	public void addNode(Object newNode);

	/**
	 * Adds edge between two pre-existing nodes with a certain weight.
	 *
	 * @param from
	 *            Origin node.
	 * @param to
	 *            Target node.
	 * @param weight
	 *            Weight of the edge.
	 */
	public void addEdge(Object from, Object to, int weight);

	/**
	 * Checks if two nodes are directly connected. Direction of the edge is
	 * taken into account.
	 *
	 * Node is not adjacent to itself unless it has a loop.
	 *
	 * @param from
	 *            Origin node.
	 * @param to
	 *            Target node.
	 * @return Whether or not there exists an edge directly connecting origin
	 *         node to target node.
	 */
	public boolean adjacent(Object from, Object to);

	/**
	 * Calculates the length of shortest path between two given nodes. These
	 * must have been added to the graph.
	 *
	 * @param from
	 *            Start node.
	 * @param to
	 *            Target node.
	 * @return Length of shortest path as defined by edge weights if it exists,
	 *         otherwise -1 if the given nodes are disjoint, and number smaller
	 *         than -1 if there is a negative cycle.
	 */
	public int shortestPathBetween(Object from, Object to);
}
