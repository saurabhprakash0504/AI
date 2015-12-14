import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class waterFlow {

	private static final FinalSol none = new FinalSol(0, "None");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// System.out.println("himica");
		BufferedReader br = null;
		ArrayList<FinalSol> solutions = new ArrayList<FinalSol>();

		try {
			br = new BufferedReader(
					new FileReader(
						"C:\\ALL_WORK_SPACE\\AI\\Search\\Search_BFS\\src\\sampleInput.txt"));
		//	String input1=args[0];
		//	String input2=args[1];
		//	System.out.println(input1+"ddD"+input2);

		//	br = new BufferedReader(
		//			new FileReader(
			//				args[1]));
			// StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			int cases = 0, startTime = 0;
			// List<GraphAdjacencyList> adjacencyList = new
			// ArrayList<GraphAdjacencyList>();
			String method = null;
			Node source = null;
			GraphAdjacencyList adjList = null;
			List<String> nodeList = null;
			List<String> endList = null;
			int nodeId = 0, pipes = 0;
			while (line != null) {
				if (line.trim().matches("[0-9]+")) {

					cases = Integer.parseInt(line);
				}
				if (cases > 0) {
					for (int ij = 0; ij < cases; ij++) {
						adjList = null;
						nodeList = null;
						nodeList = new ArrayList<String>();

						line = br.readLine();
						method = line;
						line = br.readLine();
						source = new Node(line, null, 0, 0, nodeId, 0, null);
						nodeId++;
						nodeList.add(line);
						line = br.readLine();
						endList = new ArrayList<String>();
						String[] tokens = line.split(" ");

						System.out.println("Nodes:" + line);
						endList.addAll(Arrays.asList(tokens));
						nodeList.addAll(Arrays.asList(tokens));
						line = br.readLine();
						String[] nodetokens = line.split(" ");

						System.out.println("Nodes:" + line);

						// nodeList()
						// System.out.println("");
						nodeList.addAll(Arrays.asList(nodetokens));
						adjList = new GraphAdjacencyList(nodeList);
						adjList.searchMethod = method;
						adjList.startNode = source;
						adjList.endNodes = endList;
						line = br.readLine();
						pipes = Integer.parseInt(line.trim());
						for (int i = 0; i < pipes; i++) {
							line = br.readLine();

							List<Intervals> allintervals = new ArrayList<Intervals>();
							System.out.println("Pipe" + (i + 1) + "-" + line);

							String[] stokens = line.split(" ");
							if (null != stokens && stokens.length > 3) {
								String vertex1 = stokens[0];
								String vertex2 = stokens[1];
								int cost = 1;
								if (adjList.searchMethod
										.equalsIgnoreCase("UCS")) {
									cost = Integer.parseInt(stokens[2]);

								}
								int offPeriods = Integer.parseInt(stokens[3]);
								if (offPeriods > 0) {
									for (int j = 4; j < stokens.length; j++) {

										String[] intervaltokens = stokens[j]
												.split("-");
										if (intervaltokens.length == 2) {
											// List<Node> vList =
											// adjList.getEdge(vertex1);

											Intervals offInternal = new Intervals(
													Integer.parseInt(intervaltokens[0]),
													Integer.parseInt(intervaltokens[1]));
											allintervals.add(offInternal);

										}

									}
								}

								List<Intervals> offintervals = Solution
										.merge(allintervals);
								Node fromVertex = new Node(vertex1, null, 0, 0,
										nodeId, 0, null);
								nodeId++;
								Node toVertex = new Node(vertex2, vertex1, 0,
										cost, nodeId, nodeId - 1, offintervals);

								adjList.setEdge(fromVertex, toVertex);
							}

						
						}
						line = br.readLine();

						startTime = Integer.parseInt(line);
						adjList.startTime = startTime;
						nodeId = 0;
						pipes = 0;
						if (adjList.searchMethod.equalsIgnoreCase("DFS")) {
							FinalSol s = Traversals.dfs(adjList);
							if (null != s) {
								solutions.add(s);

							} else
								solutions.add(none);

						} else if (adjList.searchMethod.equalsIgnoreCase("BFS")) {
							FinalSol s = Traversals.bfs(adjList);
							if (null != s) {
								solutions.add(s);

							} else
								solutions.add(none);

						} else {
							FinalSol s = Traversals.ucs(adjList);
							if (null != s) {
								solutions.add(s);

							} else
								solutions.add(none);

						}
				
							line = br.readLine();

						
					}

				}

			}
			// String everything = sb.toString();
			System.out.println("Total Cases" + cases);
			// System.out.println(everything);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
						System.err.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
						System.err.println(e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
							System.err.println(e);
			}
		}

		PrintWriter writer;
		try {
			writer = new PrintWriter("output.txt", "UTF-8");
			for (FinalSol s : solutions) {
				if (!s.label.equalsIgnoreCase("None")) {
					writer.println(s.label + " " + s.time);
				} else {
					writer.println("None");

				}
			}
			writer.close();
		//	System.out.println(75%24);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
						System.err.println(e);
			System.err.println(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
						System.err.println(e);
		}

	}
}
