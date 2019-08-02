import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Test {

	public static void main(String[] args) throws IOException {

		AdjacencyListGraph<String, Integer> g = new AdjacencyListGraph<>(false);
//		Vertex<String> red = g.insertVertex("Red");
//		Vertex<String> blue = g.insertVertex("Blue");
//		Vertex<String> green = g.insertVertex("Green");
//		Vertex<String> yellow = g.insertVertex("Yellow");
//		Vertex<String> orange = g.insertVertex("Orange");
//
//		Edge<Integer> e1 = g.insertEdge(red, blue, 1);
//		Edge<Integer> e2 = g.insertEdge(red, green, 2);
//		Edge<Integer> e3 = g.insertEdge(red, orange, 3);
//		Edge<Integer> e4 = g.insertEdge(blue, green, 4);
//		Edge<Integer> e5 = g.insertEdge(blue, yellow, 5);
//		Edge<Integer> e6 = g.insertEdge(blue, orange, 6);

//		// g.removeEdge(e1);
//		System.out.println(g.toString());
//		System.out.println(g.areAdjacent(red, yellow));

		
		// Airport names cannot have spaces
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
					break;
				}
			case 2:
				// ? YYZ list all connections
				// - YYZ delete airport vertex

				// TODO: case where YYZ is different from yyz
				if (arr[0].equals("?")) {
					// Vertex doesn't exist
					if (airports.get(arr[1]) == null)
						System.out.println("Airport doesn't exist");
					else {
						origin = airports.get(arr[1]);
						for (Edge<Integer> e : g.outgoingEdges(origin))
							System.out.println(g.pathBetweenVertex(e));
					}
					break;
				} else if (arr[0].equals("-")) {
					// try to remove vertex from map and from graph
					if (airports.get(arr[1]) != null)
						g.removeVertex(airports.remove(arr[1]));
					else
						System.out.println("Airport doesn't exist");
					break;
				}
			case 3:
				// ? YYZ LAX quickest route
				if (arr[0].equals("?") && airports.get(arr[1]) != null && airports.get(arr[2]) != null) {
					int total;
					origin = airports.get(arr[1]);
					destination = airports.get(arr[2]);
					
					Map<Vertex<String>, Integer> shortestValue = shortestPath(g, origin);
				
					Map<Vertex<String>, Edge<Integer>> shortest = spTree(g, origin, shortestValue);
					
					// Total Distance
					total = shortestValue.get(destination);
					System.out.println("Total Distance: " + total);
					
					//System.out.println(shortest.size());
					for (int i = 0; i < shortest.size(); i++) {
						edge = shortest.get(destination);
						System.out.println(g.pathBetweenVertex(edge));
						destination = g.opposite(destination, edge);
					}
					
					break;
				}

			case 5:
				// +/- YYZ JFK 120 plane (add/remove edge)
				if (arr[0].equals("+") && arr[3].matches("(0|[1-9]\\d+)")) {
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
					break;
				} else if (arr[0].equals("-") && arr[3].matches("(0|[1-9]\\d+)")) {
					// Does not contain one or more key
					if (!airports.containsKey(arr[1]) || !airports.containsKey(arr[2]))
						System.out.println("One or more airport(s) does not exist");
					else {
						origin = airports.get(arr[1]);
						destination = airports.get(arr[2]);
						edge = g.getEdge(origin, destination);

						String vehicle = g.pathBetweenVertex(origin, destination).split(" ")[4];

						// check if edge is valid
						if (g.areAdjacent(origin, destination) && edge.getElement() == Integer.parseInt(arr[3])
								&& vehicle.equals(arr[4]))
							g.removeEdge(edge);
						else
							System.out.println("Edge is invalid (i.e. doesn't exist, wrong distance, or wrong vehicle");
					}
					break;
				} else {
					// TODO: Never reaches default
					System.out.println("Command must start with + or - and distance must be an integer");
					break;
				}
			default:
				System.out.println("Unknown command");
				break;
			}
			// + STRING STRING INT plane (add vertex + edge)

			// - STRING (remove vertex)

			// - STRING STRING INT plane (remove edge)

			// ? STRING (list edges + opposite)

			// ? STRING STRING (quickest route; print total duration and list each
			// connection
		}
		//sc.close();

		Vertex<String> s = g.insertVertex("S");
		Vertex<String> t = g.insertVertex("T");
		Vertex<String> y = g.insertVertex("Y");
		Vertex<String> x = g.insertVertex("X");
		Vertex<String> z = g.insertVertex("Z");

		Edge<Integer> s_t = g.insertEdge(s, t, 10);
		Edge<Integer> s_y = g.insertEdge(s, y, 5);
		Edge<Integer> y_t = g.insertEdge(y, t, 3);
		Edge<Integer> y_x = g.insertEdge(y, x, 9);
		Edge<Integer> y_z = g.insertEdge(y, z, 2);
		Edge<Integer> t_x = g.insertEdge(t, x, 1);
		Edge<Integer> t_y = g.insertEdge(t, y, 2);
		Edge<Integer> z_x = g.insertEdge(z, x, 6);
		Edge<Integer> x_z = g.insertEdge(x, z, 4);
		Edge<Integer> z_s = g.insertEdge(z, s, 7);

		System.out.println(g.toString());

		System.out.println(shortestPath(g, x));

	}

	// G has no negative edges
	/**
	 * 
	 * @param <V> Generic type of Vertex
	 * @param g
	 * @param src
	 * @return
	 */
	public static <V> Map<Vertex<V>, Integer> shortestPath(Graph<V, Integer> g, Vertex<V> src) {
		// predecessor/distance for shortest path
		Map<Vertex<V>, Integer> d = new HashMap<Vertex<V>, Integer>();
		Map<Vertex<V>, Integer> cloud = new HashMap<Vertex<V>, Integer>();
		//Map<Vertex<V>, Vertex<V>> predecessor = new HashMap<Vertex<V>, Vertex<V>>();
		

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
			//predecessor.put(v, null);
			pq.add(v);
		}
		// priority queue not empty, extact-min
		while (!pq.isEmpty()) {
			Vertex<V> u = pq.poll(); // get min
			cloud.put(u, d.get(u));

			// for each outgoing edge perform relaxation
			for (Edge<Integer> e : g.outgoingEdges(u)) {
				Vertex<V> v = g.opposite(u, e);
				// perform relaxation if vertex v isn't already in cloud
				if (cloud.get(v) == null) {
					int weight = e.getElement(); // assume weight is element of edge
					if (d.get(v) > d.get(u) + weight) {
						d.put(v, d.get(u) + weight);
						//predecessor.put(v, u);
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
