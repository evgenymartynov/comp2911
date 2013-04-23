import graph.AdjacencyListGraph;
import graph.Graph;

import java.util.List;

public class Main {
    private static Graph<String> generateGraph() {
        Graph<String> graph = new AdjacencyListGraph<String>();

        // Nodes
        graph.addNode("Arad");
        graph.addNode("Bucharest");
        graph.addNode("Cralova");
        graph.addNode("Dobreta");
        graph.addNode("Eforle");
        graph.addNode("Fagaras");
        graph.addNode("Glurglu");
        graph.addNode("Hirsova");
        graph.addNode("Iasl");
        graph.addNode("Lugoj");
        graph.addNode("Mehadia");
        graph.addNode("Neamt");
        graph.addNode("Oradea");
        graph.addNode("Pitesti");
        graph.addNode("Rimnicu Vilcea");
        graph.addNode("Sibiu");
        graph.addNode("Timisoara");
        graph.addNode("Urziceni");
        graph.addNode("Vaslui");
        graph.addNode("Zerlnd");

        // Links
        graph.addEdge("Arad", "Zerlnd", 75);
        graph.addEdge("Arad", "Timisoara", 118);
        graph.addEdge("Arad", "Sibiu", 140);
        graph.addEdge("Bucharest", "Pitesti", 101);
        graph.addEdge("Bucharest", "Fagaras", 211);
        graph.addEdge("Bucharest", "Urziceni", 85);
        graph.addEdge("Bucharest", "Glurglu", 90);
        graph.addEdge("Cralova", "Pitesti", 138);
        graph.addEdge("Cralova", "Rimnicu Vilcea", 146);
        graph.addEdge("Cralova", "Dobreta", 120);
        graph.addEdge("Dobreta", "Mehadia", 75);
        graph.addEdge("Eforle", "Hirsova", 86);
        graph.addEdge("Fagaras", "Sibiu", 99);
        graph.addEdge("Hirsova", "Urziceni", 98);
        graph.addEdge("Iasl", "Neamt", 87);
        graph.addEdge("Iasl", "Vaslui", 92);
        graph.addEdge("Lugoj", "Mehadia", 70);
        graph.addEdge("Lugoj", "Timisoara", 111);
        graph.addEdge("Oradea", "Sibiu", 151);
        graph.addEdge("Oradea", "Zerlnd", 71);
        graph.addEdge("Pitesti", "Rimnicu Vilcea", 97);
        graph.addEdge("Rimnicu Vilcea", "Sibiu", 80);
        graph.addEdge("Urziceni", "Vaslui", 142);

        return graph;
    }

    public static void main(String[] args) {
        Graph<String> graph = generateGraph();
        List<String> path;
        GraphSearcher<String> dfs = new GraphSearcher<String>(graph,
                new DFSStrat<String>());
        GraphSearcher<String> bfs = new GraphSearcher<String>(graph,
                new BFSStrat<String>());
        GraphSearcher<String> dijkstra = new GraphSearcher<String>(graph,
                new DijkstraStrat<String>());

        path = dfs.findPath("Arad", "Bucharest");
        System.out.println(path);

        path = dfs.findPath("Dobreta", "Neamt");
        System.out.println(path);

        path = bfs.findPath("Arad", "Bucharest");
        System.out.println(path);

        path = bfs.findPath("Dobreta", "Neamt");
        System.out.println(path);

        path = dijkstra.findPath("Arad", "Dobreta");
        System.out.println(path);

        path = dfs.findPath("Arad", "Dobreta");
        System.out.println(path);
    }
}
