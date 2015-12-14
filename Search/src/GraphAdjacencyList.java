import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GraphAdjacencyList {
	/* Makes use of Map collection to store the adjacency list for each vertex. */
	Map<String, List<Node>> Adjacency_List;
	String searchMethod;
	Node startNode;
	List<String> endNodes;
	int startTime;
	List<String> nodeList;

	/*
	 * Initializes the map to with size equal to number of vertices in a graph
	 * Maps each vertex to a given List Object
	 */
	public GraphAdjacencyList(List<String> nodeList) {
		Adjacency_List = new HashMap<String, List<Node>>();
		for (int i = 0; i < nodeList.size(); i++) {
			List<Node> x = new ArrayList<Node>();
			Adjacency_List.put(nodeList.get(i), x);
		}
		this.nodeList = nodeList;
	}

	/* Adds nodes in the Adjacency list for the corresponding vertex */
	public void setEdge(Node source, Node destination) {

		if (Adjacency_List.containsKey(source.label)) {
			List<Node> nodes = Adjacency_List.get(source.label);
			nodes.add(destination);
			Adjacency_List.put(source.label, nodes);
		} else {
			List<Node> nodes = new ArrayList<Node>();
			nodes.add(destination);
			Adjacency_List.put(source.label, nodes);
		}
		// List<Node> slist = Adjacency_List.get(source.label);
		// /slist.add(destination);
		// List<Node> dlist = Adjacency_List.get(destination.label);
		// dlist.add(source);
	}

	/* Returns the List containing the vertex joining the source vertex */
	public List<Node> getEdge(String source) {
		// if (source > Adjacency_List.size()) {
		// System.out.println("the vertex entered is not present");
		// return null;
		// }
		return Adjacency_List.get(source);
	}

	public FinalSol graphSolve() {

		return null;
	}

	/*
	 * Main Function reads the number of vertices and edges in a graph. then
	 * creates a Adjacency List for the graph and prints it
	 */
	public static void main(String... arg) {
		String source, destination;
		int number_of_edges, number_of_vertices;
		int count = 1;
		Scanner scan = new Scanner(System.in);
		try {
			/* Read the number of vertices and edges in graph */
			System.out
					.println("Enter the number of vertices and edges in graph");
			number_of_vertices = scan.nextInt();
			number_of_edges = scan.nextInt();
			List<String> nodeList = new ArrayList<String>();
			GraphAdjacencyList adjacencyList = new GraphAdjacencyList(nodeList);

			/* Reads the edges present in the graph */
			System.out
					.println("Enter the edges in graph Format : <source index> <destination index>");
			while (count <= number_of_edges) {
				// Node source1 = new Node("s", 0, 0, 0, 0, null);
				// Node destination1 = new Node("s", 0, 0, 0, 0, null);
				// source = scan.next();
				// destination = scan.next();
				// adjacencyList.setEdge(source1, destination1);
				count++;
			}

			/* Prints the adjacency List representing the graph. */
			System.out.println("the given Adjacency List for the graph \n");
			for (int i = 1; i <= number_of_vertices; i++) {
				System.out.print(i + "->");
				List<Node> edgeList = adjacencyList.getEdge("x");
				for (int j = 1;; j++) {
					if (j != edgeList.size()) {
						System.out.print(edgeList.get(j - 1) + "->");
					} else {
						System.out.print(edgeList.get(j - 1));
						break;
					}
				}
				System.out.println();
			}
		} catch (InputMismatchException inputMismatch) {
			System.out
					.println("Error in Input Format. \nFormat : <source index> <destination index>");
		}
		scan.close();
	}
}