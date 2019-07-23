import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Minh Nguyen
 * @author Christopher Nguyen
 */
public class AdjacencyMapGraph<V, E> implements Graph<V, E> {

	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices;
	private PositionalList<Edge<E>> edges;

	/**
	 * A vertex of an adjacency map graph representation.
	 */
	private class InnerVertex<V> implements Vertex<V> {

		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;

		/**
		 * Construct a new innerVertex instance storing {@code element}.
		 * 
		 * @param element    Value of Vertex instance
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
		
		/**
		 * Set element of Vertex.
		 * 
		 * @param element Element to be set to
		 */
		public void setElement(V element) {
			this.element = element;
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
		
		/**
		 * Validates this Vertex belongs to current graph.
		 * 
		 * @param graph Graph to be checked against
		 * @return true if belongs, false otherwise
		 */
		private boolean validate(Graph<V,E> graph) {
			return (pos != null && graph == AdjacencyMapGraph.this);
		}

	} // InnerVertex

	/**
	 * An edge of an adjacency map graph representation.
	 */
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;

		@SuppressWarnings("unchecked")
		public InnerEdge(Vertex<V> u, Vertex<V> v, E element) {
			this.element = element;
			endpoints = (Vertex<V>[]) new Vertex[] { u, v };
		}

		@Override
		public E getElement() {
			return element;
		}
		
		/**
		 * Set element of Edge.
		 * 
		 * @param element Element to be set to
		 */
		public void setElement(E element) {
			this.element = element;
		}

		public Position<Edge<E>> getPos() {
			return pos;
		}

		public void setPos(Position<Edge<E>> pos) {
			this.pos = pos;
		}

		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}
		
		/**
		 * Validates this Edge belongs to current graph.
		 * 
		 * @param graph Graph to be checked against
		 * @return true if belongs, false otherwise
		 */
		private boolean validate(Graph<V,E> graph) {
			return (pos != null && graph == AdjacencyMapGraph.this);
		}

	} // InnerEdge

	/**
	 * Constructs an empty graph (assume undirected).
	 */
	public AdjacencyMapGraph() {
		this(false);
	}

	/**
	 * Constructs an empty graph.
	 * 
	 * @param isDirected Determine if graph is directed or not
	 */
	public AdjacencyMapGraph(boolean isDirected) {
		this.isDirected = isDirected;
		vertices = new NodePositionalList<Vertex<V>>();
		edges = new NodePositionalList<Edge<E>>();
	}
	
	/**
	 * Checks if v is a valid InnerVertex of this graph.
	 * 
	 * @param v Vertex to be checked
	 * @return An InnerVertex object if v is valid
	 * @throws IllegalArgumentException if v is not a valid InnerVertex
	 */
	private InnerVertex<V> validate(Vertex<V> v) throws IllegalArgumentException {
		if (!(v instanceof InnerVertex))
			throw new IllegalArgumentException("Not a InnerVertex");
		InnerVertex<V> vertex = (InnerVertex<V>) v;
		// Check if Vertex is a part of this graph
		if (!vertex.validate(this))
			throw new IllegalArgumentException("Invalid Vertex");
		return vertex;
	}
	
	/**
	 * Checks if e is a valid InnerEdge of this graph.
	 * 
	 * @param e Edge to be checked
	 * @return An InnerVertex object if e is valid
	 * @throws IllegalArgumentException if e is not a valid InnerEdge
	 */
	private InnerEdge<E> validate(Edge<E> e) throws IllegalArgumentException {
		if (!(e instanceof InnerEdge))
			throw new IllegalArgumentException("Not a InnerEdge");
		InnerEdge<E> edge = (InnerEdge<E>) e;
		// Check if Vertex is a part of this graph
		if (!edge.validate(this))
			throw new IllegalArgumentException("Invalid Edge");
		return edge;
	}
	
	@Override
	public int numVertices() {
		return vertices.size();
	}

	@Override
	public int numEdges() {
		return edges.size();
	}

	@Override
	public Iterable<Vertex<V>> vertices() {
		return (NodePositionalList<Vertex<V>>) vertices;
	}

	@Override
	public Iterable<Edge<E>> edges() {
		return (NodePositionalList<Edge<E>>) edges;
	}

	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v);
	}

	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		return vertex.getIncoming().size();
	}

	@Override
	public int OutDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		return vertex.getOutgoing().size();
	}

	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		return vertex.getIncoming().values();
	}

	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		return vertex.getOutgoing().values();
	}
	
	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws IllegalArgumentException {
		return outgoingEdges(v);
	}

	@Override
	public Vertex<V>[] endVerticles(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}

	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] varr = edge.getEndpoints();
		// Check if v is incident to e
		if (varr[0] != vertex || varr[1] != vertex)
			throw new IllegalArgumentException("v is not incident to this edge");
		return (varr[0] == vertex ? varr[1] : varr[0]);
	}

	@Override
	public Vertex<V> insertVertex(V element) {
		InnerVertex<V> vertex = new InnerVertex<V>(element, isDirected);
		// Add new vertex to end of vertices List
		vertex.setPos(vertices.addLast(vertex));
		return vertex;
	}

	@Override
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
		InnerVertex<V> vertexA = validate(u);
		InnerVertex<V> vertexB = validate(v);
		// Check if u and v already has an edge between
		if (getEdge(vertexA, vertexB) != null)
			throw new IllegalArgumentException("Vertex u and v already contains an edge");
		// Create edge, add to end of edges List
		InnerEdge<E> edge = new InnerEdge<E>(u, v, element);
		edge.setPos(edges.addLast(edge));
		// Add entry to Incident Out and Incident In Maps
		vertexA.getOutgoing().put(vertexB, edge);
		vertexB.getIncoming().put(vertexA, edge);
		return edge;
	}

	@Override
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		// Remove edges
		for (Edge<E> incidentEdge : vertex.getOutgoing().values())
			removeEdge(incidentEdge);
		for (Edge<E> incidentEdge : vertex.getIncoming().values())
			removeEdge(incidentEdge);
		// Remove vertex
		vertices.remove(vertex.getPos());
	}

	@Override
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] varr = edge.getEndpoints();
		InnerVertex<V> vertexA = validate(varr[0]);
		InnerVertex<V> vertexB = validate(varr[1]);
		vertexA.getOutgoing().remove(vertexB, edge);
		vertexB.getIncoming().remove(vertexA, edge);
		
	}

	@Override
	public boolean isDirected(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return isDirected;
	}

	@Override
	public Edge<E> insertDirectedEdge(Vertex<V> v, Vertex<V> w, E o) throws IllegalArgumentException {
		return insertEdge(v, w, o);
	}

	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws IllegalArgumentException {
		InnerVertex<V> vertexA = validate(v);
		InnerVertex<V> vertexB = validate(w);
		return (getEdge(vertexA, vertexB) != null);
	}

	@Override
	public V replace(Vertex<V> v, V element) throws IllegalArgumentException {
		InnerVertex<V> vertex = validate(v);
		V old = vertex.getElement();
		vertex.setElement(element);
		return old;
	}

	@Override
	public E replace(Edge<E> e, E element) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		E old = edge.getElement();
		edge.setElement(element);
		return old;
	}

}
