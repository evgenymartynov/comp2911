package graph;

import java.util.LinkedList;


public class Node<T> {
	public Node(T label) {
		this.label = label;
		this.edges = new LinkedList<Edge>();
	}

	public void addEdge(Node<?> to, int weight) {
		edges.add(new Edge(to, weight));
	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public T getLabel() {
		return label;
	}

	T label;
	LinkedList<Edge> edges;
}
