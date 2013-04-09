/**
 * Internal representation of an edge for Graph implementations.
 *
 */
public class Edge {
	private Node<?> to;
	private int weight;

	/**
	 * Creates a directed edge to a given node nodes with a given weight.
	 *
	 * @param to
	 *            Target node.
	 * @param weight
	 *            Weight of the edge.
	 */
	public Edge(Node<?> to, int weight) {
		this.to = to;
		this.weight = weight;
	}

	public Node<?> getTo() {
		return to;
	}

	public int getWeight() {
		return weight;
	}
}
