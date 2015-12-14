import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Traversals {
	/*
	 * public static FinalSol graphBFSByLevelList(Node source,GraphAdjacencyList
	 * graph,List<Node> targetNodes) { //if empty graph, then return. if (null
	 * == source) { return null; } Set<Node> frontier = new HashSet<Node>();
	 * Set<Node> explored = new HashSet<Node>(); //this will be useful to
	 * identify what we visited so far and also its level //if we dont need
	 * level, we could just use a Set or List
	 * 
	 * HashMap<Node, Integer> level = new HashMap<Node, Integer>(); int moves =
	 * 0; //add source to frontier. frontier.add(source);
	 * graph.Adjacency_List.get(source.label); level.put(source, moves);
	 * if(frontier.isEmpty()){ System.out.println("No path found"); return null;
	 * 
	 * } while (!frontier.isEmpty()) { Set<Node> next = new HashSet<Node>(); for
	 * (Node parent : frontier) { for (Node neighbor : parent.offTime) { if
	 * (!level.containsKey(neighbor)) { // visitNodeg(neighbor);
	 * level.put(neighbor, moves); next.add(neighbor); } //check if we reached
	 * out target Nodeg if (neighbor.equals(targetNodeg)) { // return; // we
	 * have found our target Nodeg V. } }//inner for }//outer for moves++;
	 * frontier = next; }//while }
	 * 
	 * // DFS traversal of a tree is performed by the dfs() function public void
	 * dfs() { // DFS uses Stack data structure Stack s = new Stack();
	 * s.push(this.rootNode); rootNode.visited = true; printNode(rootNode);
	 * while (!s.isEmpty()) { Node n = (Node) s.peek(); Node child =
	 * getUnvisitedChildNode(n); if (child != null) { child.visited = true;
	 * printNode(child); s.push(child); } else { // AI addd to open // check if
	 * its a goal // if goal quit and add length by traversing s.pop(); } } //
	 * Clear visited property of nodes clearNodes(); }
	 */
	public static FinalSol bfs(GraphAdjacencyList graph) {
		FinalSol solution = null;
		// BFS uses Queue data structure
		Queue frontier = new LinkedList();
		// Set<Node> explored = new HashSet<Node>();
		Hashtable<String, Node> exploredTable = new Hashtable<String, Node>();

		frontier.add(graph.startNode);
	//	int moves = 0;
		boolean goalFound = false;

		while (!frontier.isEmpty() && !goalFound) {
			Node n = (Node) frontier.remove();
			for (String goalTest : graph.endNodes) {
				if (n.label.equalsIgnoreCase(goalTest)) {
					System.out.println("Goal Found" + goalTest);
					goalFound = true;
					int cost = graph.startTime
							+ calculateCost(exploredTable, n);
				//	System.out.println("Cost:" + cost);
					solution = new FinalSol(cost % 24, goalTest);
					break;

				}
			}
			List<Node> children = null;

			children = getChildren(n, graph);

			for (Node child : children) {
			//	child.visited = true;
			//	child.depth = moves;
				if (null == existsExpored(child, exploredTable)
						&& null == existsFrontier(child, frontier)) {
					// AI check if goal

					frontier.add(child);

				}
			}
	//		moves++;
			exploredTable.put(n.label, n);

		}
		// Clear visited property of nodes
		return solution;
	}

	public static FinalSol ucs(GraphAdjacencyList graph) {
		FinalSol solution = null;

		// BFS uses Queue data structure
		Comparator costComparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				Node i1 = (Node) o1;
				Node i2 = (Node) o2;
				if ((i1.cost - i2.cost) == 0)
					return i1.label.compareTo(i2.label);

				else
					return i1.cost - i2.cost;
			}
		};

		Queue frontier = new PriorityQueue<Node>(graph.nodeList.size(),
				costComparator);
		// Queue frontier = new LinkedList<Node>();
		// Set<Node> explored = new HashSet<Node>();
		Hashtable<String, Node> exploredTable = new Hashtable<String, Node>();
		graph.startNode.cost = graph.startNode.cost + graph.startTime;

		frontier.add(graph.startNode);
		int moves = 0;
		int totalCost = 0;
		boolean goalFound = false;
		String finalGoal = null;
		// boolean active=false;
		while (!frontier.isEmpty() && !goalFound) {

			Node n = (Node) frontier.peek();
			int time = n.parentCost;
			if (n.isActive(time)) {
				// active = true;
				n = (Node) frontier.remove();
				for (String goalTest : graph.endNodes) {
					if (n.label.equalsIgnoreCase(goalTest)) {
						if (!goalFound) {
							System.out.println("Goal Found" + goalTest);
							goalFound = true;
							totalCost = n.cost;
							System.out.println("Cost" + n.cost);
							finalGoal = goalTest;
						} else if (n.cost < totalCost) {
							System.out.println("final goal");
							goalFound = true;
							totalCost = n.cost;
							finalGoal = goalTest;
						}
						// int cost = graph.startTime
						// + calculateCost(exploredTable, n);
						// System.out.println("Cost:" + cost);
						// break;

					}
				}
			} else {
				// frontier.remove();
				// frontier.add(n);
				continue;
			}

			List<Node> children = null;

			children = getChildren(n, graph);

			for (Node child : children) {
				child.parentCost = n.cost;
				child.visited = true;
				child.depth = moves;

				if (null == existsExpored(child, exploredTable)
						&& null == existsFrontier(child, frontier)) {
					// AI check if goal
					child.cost = child.cost + n.cost;
					time = child.parentCost;
					if (child.isActive(time)) {
						frontier.add(child);
					}

				} else if (null != existsFrontier(child, frontier)) {
					Node node = existsFrontier(child, frontier);
					child.cost = child.cost + n.cost;
					time = child.parentCost;
					if (child.cost < node.cost && child.isActive(time)) {
						frontier.remove(node);

						frontier.add(child);

					}
					/*
					 * else if(!n.isActive(n.parentCost)){
					 * frontier.remove(node); child.cost = child.cost +
					 * node.cost; time=child.cost;
					 * if(child.isActive(child.parentCost)){
					 * 
					 * frontier.add(child); }
					 * 
					 * 
					 * }
					 */
				}

				else if (null != existsExpored(child, exploredTable)) {
					Node node = existsExpored(child, exploredTable);
					child.cost = child.cost + n.cost;
					time = child.cost;

					if ((child.cost < node.cost) && child.isActive(time)) {
						exploredTable.remove(node.label);

						frontier.add(child);

					}
					/*
					 * else if(!n.isActive(n.parentCost)){
					 * exploredTable.remove(node.label); child.cost = child.cost
					 * + node.cost; time=child.parentCost;
					 * if(child.isActive(time)){
					 * 
					 * frontier.add(child);
					 * 
					 * }
					 * 
					 * }
					 */
				}

			}
			moves++;
			exploredTable.put(n.label, n);

		}
		if (!goalFound) {
			System.out.println("None");
		} else {
			solution = new FinalSol(totalCost % 24, finalGoal);

		}
		// Clear visited property of nodes
		return solution;

	}

	private static Node existsFrontier(Node child, Queue frontier) {
		// TODO Auto-generated method stub
		Iterator<Node> fNodes = frontier.iterator();

		while (fNodes.hasNext()) {
			Node n = fNodes.next();
			if (n.label.equalsIgnoreCase(child.label)) {
				return n;
			}

		}

		return null;
	}

	private static List<Node> getChildren(Node n, GraphAdjacencyList graph) {
		if (graph != null && graph.Adjacency_List != null) {
			List<Node> nodes = graph.Adjacency_List.get(n.label);
			Comparator comparator = new Comparator() {
				public int compare(Object o1, Object o2) {
					Node i1 = (Node) o1;
					Node i2 = (Node) o2;
					return i1.label.compareTo(i2.label);
				}
			};
			Comparator costComparator = new Comparator() {
				public int compare(Object o1, Object o2) {
					Node i1 = (Node) o1;
					Node i2 = (Node) o2;
					return i1.cost - i2.cost;
				}
			};
			if (null != nodes) {
				if (graph.searchMethod.equalsIgnoreCase("BFS")) {
					Collections.sort(nodes, comparator);
				} else if (graph.searchMethod.equalsIgnoreCase("DFS")) {

					Collections.sort(nodes,
							Collections.reverseOrder(comparator));

				} else if (graph.searchMethod.equalsIgnoreCase("UCS")) {
					Collections.sort(nodes, costComparator);
				}

				return nodes;
			}
		}
		return null;
		// TODO Auto-generated method stub
	}

	/*
	 * public class NodeComparator implements Comparator {
	 * 
	 * @Override public int compare(Object o1, Object o2) { Node i1 = (Node) o1;
	 * Node i2 = (Node) o2; return i1.label.compareTo(i2.label); } }
	 */
	class NodeCostComparator implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			Node i1 = (Node) o1;
			Node i2 = (Node) o2;
			return i1.cost - i2.cost;
		}
	}

	// DFS traversal of a tree is performed by the dfs() function
	public static FinalSol dfs(GraphAdjacencyList graph) {
		FinalSol solution = null;

		// DFS uses Stack data structure
		Stack s = new Stack();
		// Set<String> explored = new HashSet<String>();
		Hashtable<String, Node> exploredTable = new Hashtable<String, Node>();

		boolean goalFound = false;
		s.push(graph.startNode);
		int moves = 0;
		while (!s.isEmpty()) {
			Node n = (Node) s.peek();
			List<Node> children = null;
			children = getChildren(n, graph);
			// explored.add(n.label);
			exploredTable.put(n.label, n);
			Node checkNode = (Node) s.pop();
			if (null != children) {
				for (Node child : children) {
					if (null == existsExpored(child, exploredTable)) {
						if (!child.visited) {
							child.visited = true;
							child.depth = moves;

							s.push(child);
						}
					}
				}
			}
			moves++;
			for (String goalTest : graph.endNodes) {
				if (checkNode.label.equalsIgnoreCase(goalTest)) {
					System.out.println("DFS Goal Found" + goalTest);
					goalFound = true;
					int cost = graph.startTime
							+ calculateCost(exploredTable, checkNode);
					System.out.println("Cost:" + cost);
					solution = new FinalSol(cost % 24, goalTest);

					break;
				}

			}
			if (goalFound) {
				System.out.println("hi");
				break;
			}
		}
		return solution;

		// Clear visited property of nodes
	}

	private static int calculateCost(Hashtable<String, Node> exploredTable,
			Node checkNode) {
		// TODO Auto-generated method stub

		Node parent = exploredTable.get(checkNode.parentId);
		int cost = checkNode.cost;
		while (null != parent) {

			cost = cost + parent.cost;
			if (parent.parentId == null) {
				System.out.println("null" + parent.label);
				parent = null;
			} else {
				System.out.println("path" + parent.label);
				parent = exploredTable.get(parent.parentId);
			}
		}
		return cost;
	}

	private static Node existsExpored(Node child,
			Hashtable<String, Node> exploredTable) {
		// TODO Auto-generated method stub
		if (exploredTable.containsKey(child.label)) {
			return exploredTable.get(child.label);
		}

		return null;
	}
}
