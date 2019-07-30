import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Test {

	public static void main(String[] args) {

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
//
		// airport names cannot have spaces
		Map<String, Vertex<String>> airports = new HashMap<String, Vertex<String>>();
		Scanner sc = new Scanner(System.in);
		String in = "";
		
		while (!(in = sc.nextLine().toUpperCase().trim()).equals("QUIT")) {
			
			String[] arr = in.split(" ");
			
			switch (arr.length) {
				case 1:
					// ? list all connections in memory (lines in the format YYZ JFK 120 plane)
					if (arr[0].equals("?")) {
						
						break;
					}
						
				case 2:
					// ? YYZ or - YYZ list all connections from 
					if (arr[0].equals("?")) {
						
					} else if (arr[0].equals("-")) {
						// try to remove vertex from map and from graph
						if (airports.remove(arr[1]) != null)
							g.removeVertex(airports.get(arr[1]));
						break;
					}
				case 3:
					// ? YYZ LAX
				case 5:
					// +/- YYZ JFK 120 plane
					if (arr[0].equals("+")) {
						//Vertex<String> vertexA = g.insertEdge(u, v, element)
					} else if (arr[1].equals("-")) {
						
					}
				default:
					System.out.println("Unknown command");
					break;
			}
			
			
			// + STRING STRING INT plane (add vertex + edge)
			
			// - STRING (remove vertex)
		 
			// - STRING STRING INT plane (remove edge)
			
			// ? STRING (list edges + opposite)
		
			// ? STRING STRING (quickest route; print total duration and list each connection
		}

		
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
	public static <V> Map<Vertex<V>, Integer> shortestPath(Graph<V, Integer> g, Vertex<V> src) {
		// predecessor/distance for shortest path
		Map<Vertex<V>, Integer> d = new HashMap<Vertex<V>, Integer>();
		Map<Vertex<V>, Vertex<V>> predecessor = new HashMap<Vertex<V>, Vertex<V>>();

		Map<Vertex<V>, Integer> cloud = new HashMap<Vertex<V>, Integer>();
		
		//AdjacencyListGraph<Vertex<V>, Edge<E>> cloud = new AdjacencyListGraph<Vertex<V>, Edge<E>>(false);
		
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
			predecessor.put(v, null);
			pq.add(v);
		}
		/*
		d.put(src, 0);
		pq.remove(src);
		pq.add(src);
		*/
		
		// priority queue not empty, extact-min
		while(!pq.isEmpty()) {
			Vertex<V> entry = pq.poll(); //get min
			cloud.put(entry, d.get(entry));
			
			// for each outgoing edge perform relaxation
			for (Edge<Integer> e : g.outgoingEdges(entry)) {
				Vertex<V> v = g.opposite(entry, e);
				// perform relaxation if vertex v isn't already in cloud
				if (cloud.get(v) == null) {
					int weight = e.getElement(); // assume weight is element of edge
					if (d.get(v) > d.get(entry) + weight) {
						d.put(v, d.get(entry) + weight);
						pq.remove(v);
						pq.add(v);
					}
				}
			}
		}
		// https://github.com/mjwestcott/Goodrich/blob/master/ch14/shortest_paths.py
		return cloud;
	}
	
	
	

}

