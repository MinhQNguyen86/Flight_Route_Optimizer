import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Minh Nguyen
 * @author Christopher Nguyen
 */
public class AdjacencyMapGraph<V, E> implements Graph<V, E> {
	
	/**
	 * A vertex of an adjacency map graph representation.
	 * 
	 * @param <V>
	 */
	private class InnerVertex<V> implements Vertex<V> {
		
		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;
		
		/**
		 * Construct a new innerVertex instance storing {@code element}.
		 * 
		 * @param element Value of Vertex instance
		 * @param isDirected true if directed, false if undirected
		 */
		public InnerVertex(V element, boolean isDirected) {
			this.element = element;
			outgoing = new HashMap<Vertex<V>, Edge<E>>();
			// if graph is directed, maintain two map instances
			// outgoing: collection of outgoing incident edges
			// incoming: collection of incoming incident edges
			if (isDirected) {
				incoming = new HashMap<Vertex<V>, Edge<E>>();
			} else {
				incoming = outgoing;
			}
		}
		
		@Override
		public V getElement() {
			return element;
		}

		public Position<Vertex<V>> getPos() {
			return pos;
		}

		public void setPos(Position<Vertex<V>> pos) {
			this.pos = pos;
		}

		public Map<Vertex<V>, Edge<E>> getOutgoing() {
			return outgoing;
		}

		public Map<Vertex<V>, Edge<E>> getIncoming() {
			return incoming;
		}
		
		
		
	}
	
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;
		
		public InnerEdge() {
			
		}

		@Override
		public E getElement() {
			// TODO Auto-generated method stub
			return null;
		}
		
		
	}
	
	
	@Override
	public int numVertices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numEdges() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int OutDegree(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V>[] endVerticles(Edge<E> e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vertex<V> insertVertex(V element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDirected(Edge<E> e) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Edge<E> insertDirectedEdge(Vertex<V> v, Vertex<V> w, E o) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E replace(Vertex<V> v, E element) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E replace(Edge<E> e, E element) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
