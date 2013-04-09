import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphTester {

	@Test
	public void testAdjacency() {
		StringGraph g = new StringGraph();
		g.addNode("First node");
		g.addNode("Second node");
		g.addNode("Third node");

		// No nodes are adjacent at the beginning.
		assertFalse(g.adjacent("First node", "First node"));
		assertFalse(g.adjacent("First node", "Second node"));
		assertFalse(g.adjacent("Second node", "First node"));

		// Add a single link.
		g.addEdge("First node", "Second node", 1);
		assertTrue(g.adjacent("First node", "Second node"));
		assertFalse(g.adjacent("Second node", "First node"));
		assertFalse(g.adjacent("First node", "Third node"));

		// Add a loop.
		g.addEdge("Second node", "Second node", 2);
		assertTrue(g.adjacent("Second node", "Second node"));

		// One more edge.
		g.addEdge("First node", "Third node", -1);

		// Assert all n^2 possibilities.
		assertEquals(g.adjacent("First node", "First node"), false);
		assertEquals(g.adjacent("First node", "Second node"), true);
		assertEquals(g.adjacent("First node", "Third node"), true);

		assertEquals(g.adjacent("Second node", "First node"), false);
		assertEquals(g.adjacent("Second node", "Second node"), true);
		assertEquals(g.adjacent("Second node", "Third node"), false);

		assertEquals(g.adjacent("Third node", "First node"), false);
		assertEquals(g.adjacent("Third node", "Second node"), false);
		assertEquals(g.adjacent("Third node", "Third node"), false);
	}

	@Test
	public void testConnectivity() {
		Graph<Integer> g = new AdjacencyListGraph<Integer>();
		assertTrue(g.isStronglyConnected());

		for (int i = 0; i < 3; i++) {
			g.addNode(i);

			if (i == 0)
				assertTrue(g.isStronglyConnected());
			else
				assertFalse(g.isStronglyConnected());
		}

		for (int i = 0; i < 3; i++)
			for (int k = 0; k < 3; k++)
				g.addEdge(i, k, 1);
		assertTrue(g.isStronglyConnected());
	}

	@Test
	public void testShortestPathNoNegativeCycle() {
		Graph<Integer> g = new AdjacencyListGraph<Integer>();
		for (int i = 0; i < 5; i++)
			g.addNode(i);

		g.addEdge(0, 1, -1);
		g.addEdge(0, 2, 2);
		g.addEdge(0, 3, 4);

		g.addEdge(1, 0, 2);
		g.addEdge(1, 2, 3);

		g.addEdge(2, 3, 5);
		g.addEdge(2, 4, 3);

		g.addEdge(3, 2, -1);

		g.addEdge(4, 1, -3);
		g.addEdge(4, 3, 4);

		assertEquals(5, g.shortestPathBetween(0, 4));
	}

	@Test
	public void testShortestPathWithNegativeCycle() {
		Graph<Integer> g = new AdjacencyListGraph<Integer>();
		for (int i = 0; i < 5; i++)
			g.addNode(i);

		g.addEdge(0, 1, -1);
		g.addEdge(0, 2, 2);
		g.addEdge(0, 3, 4);

		g.addEdge(1, 0, 2);
		g.addEdge(1, 2, 1);

		g.addEdge(2, 3, 5);
		g.addEdge(2, 4, 1);

		g.addEdge(3, 2, -1);

		g.addEdge(4, 1, -3);
		g.addEdge(4, 3, 4);

		assertEquals(-2, g.shortestPathBetween(0, 4));
	}

	@Test
	public void testShortestPathOnDisconnectedGraph() {
		Graph<Integer> g = new AdjacencyListGraph<Integer>();
		for (int i = 0; i < 5; i++)
			g.addNode(i);

		g.addEdge(0, 2, 2);
		g.addEdge(2, 1, 1);
		g.addEdge(1, 0, -1);
		g.addEdge(3, 4, 1);

		assertEquals(-1, g.shortestPathBetween(1, 3));
	}
}
