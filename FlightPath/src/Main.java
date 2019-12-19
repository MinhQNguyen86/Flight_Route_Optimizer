import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Main {

	public static void main(String[] args) throws IOException {
		// Airport names cannot have spaces
		
		// Graph
		AdjacencyListGraph<String, Integer> g = new AdjacencyListGraph<>(false);
		// Holds a Map with keys being name of airports and value being the Vertex of each airport
		Map<String, Vertex<String>> airports = new HashMap<String, Vertex<String>>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String in = "";
		
		while ( (in = br.readLine()) != null && !(in.trim().equalsIgnoreCase("QUIT")) ) {

			String[] arr = in.split(" ");
			
			Vertex<String> origin;
			Vertex<String> destination;
			Edge<Integer> edge;

			switch (arr.length) {
			case 1:
				// ? list all connections in memory (lines in the format YYZ JFK 120 plane)
				if (arr[0].equals("?")) {
					for (Edge<Integer> e : g.edges())
						System.out.println(g.pathBetweenVertex(e));
				} else
					System.out.println("Commands with 1 argument must start with '?'");
				break;
			case 2:
				// ? YYZ list all connections
				// - YYZ delete airport vertex
				if (arr[0].equals("?")) {
					// Vertex doesn't exist
					if (airports.get(arr[1]) == null)
						System.out.println("Airport doesn't exist");
					else {
						origin = airports.get(arr[1]);
						for (Edge<Integer> e : g.outgoingEdges(origin))
							System.out.println(g.pathBetweenVertex(e));
					}
				} else if (arr[0].equals("-")) {
					// try to remove vertex from map and from graph
					if (airports.get(arr[1]) != null)
						g.removeVertex(airports.remove(arr[1]));
					else
						System.out.println("Airport doesn't exist");
				} else
					System.out.println("Commands with 2 arguments must start with '-/?'");
				break;
			case 3:
				// ? YYZ LAX quickest route
				if (arr[0].equals("?") && airports.get(arr[1]) != null && airports.get(arr[2]) != null) {
					int total;
					int counter = 0;
					origin = airports.get(arr[1]);
					destination = airports.get(arr[2]);
					
					Map<Vertex<String>, Integer> shortestValue = shortestPath(g, origin);
					Map<Vertex<String>, Edge<Integer>> shortest = spTree(g, origin, shortestValue);
					
					// Total Distance (if total = Integer.MAX_VALUE then graph unconnected
					total = shortestValue.get(destination);
					System.out.println(total);

					Stack<String> a = new Stack<String>();
					
					// Goes through shortest path
					while (counter < total) {
						edge = shortest.get(destination);
						if (edge == null) {
							System.out.println("Graph is unconnected");
							break;
						}
						counter += edge.getElement();
						a.add(g.pathBetweenVertex(edge));
						destination = g.opposite(destination, edge);
					}

					while (!a.isEmpty())
						System.out.println(a.pop());

				} else
					System.out.println("Commands with 3 arguments must start with '?' and airports must already exist");
				break;
			case 5:
				// +/- YYZ JFK 120 plane (add/remove edge)
				if (arr[0].equals("+") && arr[3].matches("[0-9]+")) {
					// First vertex already exist in graph, but second doesn't
					if (airports.containsKey(arr[1]) && !airports.containsKey(arr[2])) {
						origin = airports.get(arr[1]);
						destination = g.insertVertex(arr[2]);
						// Second vertex already exist in graph, but first doesn't
					} else if (!airports.containsKey(arr[1]) && airports.containsKey(arr[2])) {
						origin = g.insertVertex(arr[1]);
						destination = airports.get(arr[2]);
						// Both vertex do not exist in graph
					} else if (!airports.containsKey(arr[1]) && !airports.containsKey(arr[2])) {
						origin = g.insertVertex(arr[1]);
						destination = g.insertVertex(arr[2]);
						// Both vertex already exist in graph
					} else {
						origin = airports.get(arr[1]);
						destination = airports.get(arr[2]);
					}
					airports.put(arr[1], origin);
					airports.put(arr[2], destination);
					edge = g.insertEdge(origin, destination, Integer.parseInt(arr[3]), arr[4]);
				} else if (arr[0].equals("-") && arr[3].matches("[0-9]+")) {
					// Does not contain one or more key
					if (!airports.containsKey(arr[1]) || !airports.containsKey(arr[2]))
						System.out.println("One or more airport(s) does not exist");
					else {
						origin = airports.get(arr[1]);
						destination = airports.get(arr[2]);
						edge = g.getEdge(origin, destination);

						String vehicle = g.pathBetweenVertex(origin, destination).split(" ")[3];

						// check if edge is valid
						if (g.areAdjacent(origin, destination) && edge.getElement() == Integer.parseInt(arr[3])
								&& vehicle.equals(arr[4]))
							g.removeEdge(edge);
						else
							System.out.println("Edge is invalid (i.e. doesn't exist, wrong distance, or wrong vehicle");
					}
				} else 
					System.out.println("Commands with 5 arguments must start with '+/-' and 4th argument must be an integer");
				break;
			default:
				System.out.println("Unknown command");
				break;
			}
		}
		
	}

	// G has no negative edges
	/**
	 * Returns a map with keys of vertices and values of the distance from
	 * src to that vertex.
	 * 
	 * @param <V> Generic type of Vertex
	 * @param g Graph of type V and Integers
	 * @param src Starting vertex
	 * @return Map of vertex and Integer
	 */
	public static <V> Map<Vertex<V>, Integer> shortestPath(Graph<V, Integer> g, Vertex<V> src) {
		// Distance for shortest path
		Map<Vertex<V>, Integer> d = new HashMap<Vertex<V>, Integer>();
		Map<Vertex<V>, Integer> cloud = new HashMap<Vertex<V>, Integer>();
		
		PriorityQueue<Vertex<V>> pq = new PriorityQueue<Vertex<V>>(new Comparator<Vertex<V>>() {
			@Override
			public int compare(Vertex<V> v1, Vertex<V> v2) {
				int edgeValue1 = d.get(v1);
				int edgeValue2 = d.get(v2);
				return edgeValue1 - edgeValue2;
			}
		});

		// INIT-SINGLE-SOURCE
		for (Vertex<V> v : g.vertices()) {
			if (v == src)
				d.put(v, 0);
			else
				d.put(v, Integer.MAX_VALUE);
			pq.add(v);
		}
		// priority queue not empty, extact-min
		while (!pq.isEmpty()) {
			Vertex<V> u = pq.poll();
			cloud.put(u, d.get(u));

			// for each outgoing edge perform relaxation
			for (Edge<Integer> e : g.outgoingEdges(u)) {
				Vertex<V> v = g.opposite(u, e);
				// perform relaxation if vertex v isn't already in cloud
				if (cloud.get(v) == null) {
					int weight = e.getElement(); // assume weight is element of edge
					if (d.get(v) > d.get(u) + weight) {
						d.put(v, d.get(u) + weight);
						pq.remove(v);
						pq.add(v);
					}
				}
			}
		}
		return cloud;
	}

	/**
	 * Returns a shortest path tree.
	 * 
	 * @param <V> Generic type of Vertex
	 * @param g Graph
	 * @param src Starting Vertex
	 * @param d Distance tree for shortest path
	 * @return Map representation of a shortest path tree
	 */
	public static <V> Map<Vertex<V>, Edge<Integer>> spTree(Graph<V, Integer> g, Vertex<V> src,
			Map<Vertex<V>, Integer> d) {
		Map<Vertex<V>, Edge<Integer>> tree = new HashMap<Vertex<V>, Edge<Integer>>();
		for (Vertex<V> v : d.keySet()) {
			if (v != src) {
				for (Edge<Integer> e : g.incomingEdges(v)) {
					Vertex<V> u = g.opposite(v, e);
					int weight = e.getElement();
					if (d.get(v) == d.get(u) + weight) {
						tree.put(v, e);
					}
				}
			}
		}
		return tree;
	}
	
}
