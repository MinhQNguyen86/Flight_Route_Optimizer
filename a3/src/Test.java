import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Test {
	public static AdjacencyListGraph<String, Integer> g;

	public static void main(String[] args) {

		g = new AdjacencyListGraph<String, Integer>(false);
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

		// g.removeEdge(e1);
		System.out.println(g.toString());
		System.out.println(g.areAdjacent(red, yellow));

		Scanner sc = new Scanner(System.in);

		String in;

		// add to cloud vertex from min-heap, u
		// relax on vertices adjacent to u

		while (!(in = sc.nextLine().toUpperCase()).equals("QUIT")) {

		}

		sc.close();

	}

	// G has no negative edges
	public static <V> Map<Vertex<V>, Integer> shortestPath(Graph<V, Integer> g, Vertex<V> src) {
		Map<Vertex<V>, Integer> d = new HashMap<Vertex<V>, Integer>();
		Map<Vertex<V>, Integer> cloud = new HashMap<Vertex<V>, Integer>();
		
		PriorityQueue<Vertex<V>> pq = new PriorityQueue<Vertex<V>>(new VertexComparator<V>());
		
		return null;
	}
	
	

}

class VertexComparator<V> implements Comparator<Vertex<V>> {

	@Override
	public int compare(Vertex<V> v1, Vertex<V> v2) {
		
		Test.shortestPath(Test.g, v1);
		
		return 0;
	}

}
