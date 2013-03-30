/**
 * Internal representation of an edge for Graph implementations.
 * 
 */
public class Edge {
	private int from, to;
	private int weight;

	/**
	 * Creates a directed edge between the given pair of nodes with a given
	 * weight.
	 * 
	 * @param from
	 *            Origin node.
	 * @param to
	 *            Target node.
	 * @param weight
	 *            Weight of the edge.
	 */
	public Edge(int from, int to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public int getWeight() {
		return weight;
	}
}
