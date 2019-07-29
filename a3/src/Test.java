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
		Vertex<String> red = g.insertVertex("Red");
		Vertex<String> blue = g.insertVertex("Blue");
		Vertex<String> green = g.insertVertex("Green");
		Vertex<String> yellow = g.insertVertex("Yellow");
		Vertex<String> orange = g.insertVertex("Orange");

		Edge<Integer> e1 = g.insertEdge(red, blue, 1);
		Edge<Integer> e2 = g.insertEdge(red, green, 2);
		Edge<Integer> e3 = g.insertEdge(red, orange, 3);
		Edge<Integer> e4 = g.insertEdge(blue, green, 4);
		Edge<Integer> e5 = g.insertEdge(blue, yellow, 5);
		Edge<Integer> e6 = g.insertEdge(blue, orange, 6);

//		// g.removeEdge(e1);
//		System.out.println(g.toString());
//		System.out.println(g.areAdjacent(red, yellow));
//
//		Scanner sc = new Scanner(System.in);
//
//		String in;
//
//		// add to cloud vertex from min-heap, u
//		// relax on vertices adjacent to u
//
//		while (!(in = sc.nextLine().toUpperCase()).equals("QUIT")) {
//
//		}
//
//		sc.close();

	}

	// G has no negative edges
//	public static <V> Map<Vertex<V>, Integer> shortestPath(Graph<V, Integer> g, Vertex<V> src) {
//		// predecessor/distance for shortest path
//		Map<Vertex<V>, Integer> d = new HashMap<Vertex<V>, Integer>();
//		Map<Vertex<V>, Vertex<V>> predecessor = new HashMap<Vertex<V>, Vertex<V>>();
//
//		//Map<Vertex<V>, Integer> cloud = new HashMap<Vertex<V>, Integer>();
//		
//		AdjacencyListGraph<Vertex<V>, Edge<E>> cloud = new AdjacencyListGraph<Vertex<V>, Edge<E>>(false);
//		
//		PriorityQueue<Vertex<V>> pq = new PriorityQueue<Vertex<V>>(new Comparator<Vertex<V>>() {
//			@Override
//			public int compare(Vertex<V> v1, Vertex<V> v2) {
//				int edgeValue1 = d.get(v1);
//				int edgeValue2 = d.get(v2);
//				
//				return edgeValue1 - edgeValue2;
//			}
//		});
//		
//		// INIT-SINGLE-SOURCE
//		for (Vertex<V> v : g.vertices()) {
//			d.put(v, Integer.MAX_VALUE);
//			predecessor.put(v, null);
//			pq.add(v);
//			/*
//			if (v == src)
//				d.put(v, 0);
//			else
//				d.put(v, Integer.MAX_VALUE);
//			*/
//		}
//		d.put(src, 0);
//		
//		// priority queue not empty, extact-min
//		while(!pq.isEmpty()) {
//			Vertex<V> entry = pq.poll(); //get min
//			cloud.put(entry, entry);
//			for (Edge<Integer> e : g.outgoingEdges(entry)) {
//				Vertex<V> v = g.opposite(entry, e);
//				// perform relaxation if vertex v isn't already in cloud
//				if (cloud.get(v) == null) {
//					int weight = e.getElement(); // assume weight is element of edge
//					if (d.get(v) > d.get(entry) + weight) {
//						d.put(v, d.get(entry) + weight);
//						
//					}
//				}
//			}
//		}
//		
//		return cloud;
//	}
	
	

}

