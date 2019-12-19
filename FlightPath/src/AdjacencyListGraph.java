/**
 * @author Minh Nguyen
 */
public class AdjacencyListGraph<V, E> implements Graph<V, E> {

	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices;
	private PositionalList<Edge<E>> edges;

	/**
	 * A vertex of an adjacency map graph representation.
	 */
	private class InnerVertex<C> implements Vertex<V> {

		private V element;
		private Position<Vertex<V>> pos;
		private PositionalList<Edge<E>> incidentOut, incidentIn;

		/**
		 * Construct a new innerVertex instance storing {@code element}.
		 * 
		 * @param element    Value of Vertex instance
		 * @param isDirected true if directed, false if undirected
		 */
		public InnerVertex(V element, boolean isDirected) {
			this.element = element;
			incidentOut = new NodePositionalList<Edge<E>>();
			// if graph is directed, maintain two list instances
			// outgoing: collection of outgoing incident edges
			// incoming: collection of incoming incident edges
			if (isDirected)
				incidentIn = new NodePositionalList<Edge<E>>();
			else
				incidentIn = incidentOut;
		}
		
		/**
		 * Return element of vertex
		 * 
		 * @return element of vertex
		 */
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
		
		/**
		 * Returns position of this vertex in vertex list
		 * 
		 * @return Position of vertex in vertices
		 */
		public Position<Vertex<V>> getPos() {
			return pos;
		}
		
		/**
		 * Set the position of this vertex
		 * 
		 * @param pos Position in vertices of this vertex
		 */
		public void setPos(Position<Vertex<V>> pos) {
			this.pos = pos;
		}
		
		/**
		 * Get the list of outgoing edges
		 * 
		 * @return PositionalList of outgoing edges
		 */
		public PositionalList<Edge<E>> getOutgoing() {
			return incidentOut;
		}
		
		/**
		 * Get the list of incoming edges
		 * 
		 * @return PositionalList of incoming edges
		 */
		public PositionalList<Edge<E>> getIncoming() {
			return incidentIn;
		}

		/**
		 * Validates this Vertex belongs to current graph.
		 * 
		 * @param graph Graph to be checked against
		 * @return true if belongs, false otherwise
		 */
		private boolean validate(Graph<V, E> graph) {
			return (pos != null && graph == AdjacencyListGraph.this);
		}
		
		/**
		 * Return string representation of this vertex
		 * 
		 * @return String reprsentation of vertex
		 */
		public String toString() {
			return (String) element;
		}

	} // InnerVertex

	/**
	 * An edge of an adjacency map graph representation.
	 */
	private class InnerEdge<D> implements Edge<E> {
		private E element;
		private String modeOfTransportation;
		private Vertex<V>[] endpoints;
		private Position<Edge<E>> pos;
		private Position<Edge<E>> originIncident;
		private Position<Edge<E>> destinationIncident;

		@SuppressWarnings("unchecked")
		public InnerEdge(Vertex<V> u, Vertex<V> v, E element) {
			this.element = element;
			endpoints = (Vertex<V>[]) new Vertex[] { u, v };
			InnerVertex<V> vertexOrigin = validateV(u);
			InnerVertex<V> vertexDestin = validateV(v);

			originIncident = vertexOrigin.getOutgoing().addLast(this);
			destinationIncident = vertexDestin.getIncoming().addLast(this);
		}
		
		/**
		 * Return element of edge
		 * 
		 * @return element of edge
		 */
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
		
		/**
		 * Returns position of this edge in edges list
		 * 
		 * @return Position of vertex in edges
		 */
		public Position<Edge<E>> getPos() {
			return pos;
		}
		
		/**
		 * Set the position of this vertex
		 * 
		 * @param pos Position in vertices of this vertex
		 */
		public void setPos(Position<Edge<E>> pos) {
			this.pos = pos;
		}
		
		/**
		 * Returns vertex array of size 2, holding adjacent vertices to this edge.
		 * 
		 * @return Vertex array
		 */
		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}
		
		/**
		 * Returns origin incident
		 * 
		 * @return Position that represents origin incident
		 */
		public Position<Edge<E>> getOriginIncident() {
			return originIncident;
		}
		
		/**
		 * Returns destination incident
		 * 
		 * @return Position that represents destination incident
		 */
		public Position<Edge<E>> getDestinationIncident() {
			return destinationIncident;
		}

		/**
		 * Returns string representing mode of transportation
		 * 
		 * @return String representing mode of transportation
		 */
		public String getModeOfTransportation() {
			return modeOfTransportation;
		}
		
		/**
		 * Sets string representing mode of transportation
		 * 
		 * @param modeOfTransportation String representing mode of transportation
		 */
		public void setModeOfTransportation(String modeOfTransportation) {
			this.modeOfTransportation = modeOfTransportation;
		}

		/**
		 * Validates this Edge belongs to current graph.
		 * 
		 * @param graph Graph to be checked against
		 * @return true if belongs, false otherwise
		 */
		private boolean validate(Graph<V, E> graph) {
			return (pos != null && graph == AdjacencyListGraph.this);
		}
		
		/**
		 * String representation of edge
		 * 
		 * @return String representation of edge
		 */
		public String toString() {
			return pathBetweenVertex(this);
		}

	} // InnerEdge

	/**
	 * Constructs an empty graph (assume undirected).
	 */
	public AdjacencyListGraph() {
		this(false);
	}

	/**
	 * Constructs an empty graph.
	 * 
	 * @param isDirected Determine if graph is directed or not
	 */
	public AdjacencyListGraph(boolean isDirected) {
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
	@SuppressWarnings("unchecked")
	private InnerVertex<V> validateV(Vertex<V> v) throws IllegalArgumentException {
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
	@SuppressWarnings("unchecked")
	private InnerEdge<E> validateE(Edge<E> e) throws IllegalArgumentException {
		if (!(e instanceof InnerEdge))
			throw new IllegalArgumentException("Not an InnerEdge");
		InnerEdge<E> edge = (InnerEdge<E>) e;
		// Check if Vertex is a part of this graph
		if (!edge.validate(this))
			throw new IllegalArgumentException("Invalid Edge");
		return edge;
	}
	
	/**
	 * Returns the number of vertices of the graph.
	 * 
	 * @return integer corresponding to amount of vertices
	 */
	@Override
	public int numVertices() {
		return vertices.size();
	}

	/**
	 * Return the number of edges of the graph.
	 * 
	 * @return integer corresponding to amount of edges
	 */
	@Override
	public int numEdges() {
		return edges.size();
	}
	
	/**
	 * Returns the vertices of graph as an iterable collection.
	 * 
	 * @return Iterable collection of Vertex objects
	 */
	@Override
	public Iterable<Vertex<V>> vertices() {
		return (NodePositionalList<Vertex<V>>) vertices;
	}
	
	/**
	 * Returns the edges of graph as an iterable collection.
	 * 
	 * @return Iterable collection of Edge objects
	 */
	@Override
	public Iterable<Edge<E>> edges() {
		return (NodePositionalList<Edge<E>>) edges;
	}
	
	/**
	 * Returns the edge between Vertex u and v; null if they're non adjacent.
	 * 
	 * @param u First Vertex to be checked
	 * @param v Second Vertex to be checked
	 * @return {@code Edge<E>} if an edge exists, null otherwise
	 * @throws IllegalArgumentException if v or u is not a valid vertex
	 */
	@Override
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> origin = validateV(u);
		InnerVertex<V> destination = validateV(v);

		PositionalList<Edge<E>> incident; // incident list
		Position<Edge<E>> incidentEdge; // node in incident list
		InnerEdge<E> edge; // actual edge (element of incident node)

		// Choose the vertex with the smaller degree
		if (origin.getOutgoing().size() < destination.getIncoming().size())
			incident = origin.getOutgoing();
		else
			incident = destination.getIncoming();

		incidentEdge = incident.first();

		for (int i = 0; i < incident.size(); i++) {
			edge = validateE(incidentEdge.getElement());
			Vertex<V>[] varr = edge.getEndpoints();
			if ((isDirected && varr[0].equals(origin) && varr[1].equals(destination))) {
				return edge;
			} else if (!isDirected) {
				if ((varr[0].equals(origin) && varr[1].equals(destination))
						|| (varr[0].equals(destination) && varr[1].equals(origin))) {
					return edge;
				}
			}
			// get next node in incident list
			incidentEdge = incident.after(incidentEdge);
		}
		// if u, v are non adjacent
		return null;
	}
	
	/**
	 * Return number of edges for which Vertex v is destination.
	 * 
	 * @param v Vertex to be checked
	 * @return integer value of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		return vertex.getIncoming().size();
	}
	
	/**
	 * Returns the number of edges leaving Vertex v.
	 * 
	 * @param v Vertex to be checked
	 * @return integer of edges leaving v
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public int OutDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		return vertex.getOutgoing().size();
	}
	
	/**
	 * Returns an iterable collection of edges where v is the ending vertex.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		return (NodePositionalList<Edge<E>>) vertex.getIncoming();
	}
	
	/**
	 * Returns an iterable collection of edges where v is the starting vertex.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		return (NodePositionalList<Edge<E>>) vertex.getOutgoing();
	}
	
	/**
	 * Returns an iterable collection of edges incident to v.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws IllegalArgumentException {
		return outgoingEdges(v);
	}
	
	/**
	 * Returns the vertices of Edge e as an array of length 2. If the graph is
	 * directed, the first Vertex is the origin, and the second is the destination.
	 * If the graph is undirected, the order is arbitrary.
	 * 
	 * @param e Edge to be checked
	 * @return
	 * @throws IllegalArgumentException if e is not a valid edge
	 */
	@Override
	public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validateE(e);
		return edge.getEndpoints();
	}
	
	/**
	 * Returns the Vertex that is opposite Vertex v on Edge e.
	 * 
	 * @param v Vertex to be checked
	 * @param e Edge to be checked
	 * @return Vertex opposite to v on e
	 * @throws IllegalArgumentException if v/e is not a valid vertex/edge
	 */
	@Override
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		InnerEdge<E> edge = validateE(e);
		Vertex<V>[] varr = edge.getEndpoints();
		// Check if v is incident to e
		if (varr[0] != vertex && varr[1] != vertex)
			throw new IllegalArgumentException("v is not incident to this edge");
		return (varr[0] == vertex ? varr[1] : varr[0]);
	}
	
	/**
	 * Inserts and returns a new Vertex with given element.
	 * 
	 * @param element Element of Vertex to be inserted
	 * @return Vertex that was inserted
	 */
	@Override
	public Vertex<V> insertVertex(V element) {
		InnerVertex<V> vertex = new InnerVertex<V>(element, isDirected);
		// Add new vertex to end of vertices List
		vertex.setPos(vertices.addLast(vertex));
		return vertex;
	}
	
	/**
	 * Inserts and returns a new Edge between vertices u and v, holding element.
	 * 
	 * @param u       First Vertex to be checked
	 * @param v       Second Vertex to be checked
	 * @param element Element to be stored in Edge
	 * @return Edge containing Element
	 * @throws IllegalArgumentException if u or v is an invalid vertex, or if an
	 *                                  edge already exists
	 */
	@Override
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException {
		InnerVertex<V> vertexA = validateV(u);
		InnerVertex<V> vertexB = validateV(v);

		// Check if u and v already has an edge between
		Edge<E> e = getEdge(vertexA, vertexB);
		if (e != null) {
			replace(e, element);
			return e;
		}
		// Else create edge, add to end of edges List
		InnerEdge<E> edge = new InnerEdge<E>(u, v, element);
		edge.setPos(edges.addLast(edge));

		return edge;
	}

	/**
	 * Inserts an edge along with a mode of transportation in that edge.
	 * 
	 * @param u              Vertex
	 * @param v              Vertex
	 * @param element        Value of edge
	 * @param transportation String of mode of transportation
	 * @return String representation of path
	 * @throws IllegalArgumentException if u or v is invalid vertex
	 */
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element, String transportation)
			throws IllegalArgumentException {
		InnerEdge<E> e = validateE(insertEdge(u, v, element));
		e.setModeOfTransportation(transportation);
		return e;
	}
	
	/**
	 * Removes a Vertex and all of its incident edges from graph.
	 * 
	 * @param v Vertex to be removed
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		// Remove edges
		for (Edge<E> incidentEdge : (NodePositionalList<Edge<E>>) vertex.getOutgoing())
			removeEdge(incidentEdge);
		for (Edge<E> incidentEdge : (NodePositionalList<Edge<E>>) vertex.getIncoming())
			removeEdge(incidentEdge);
		// remove vertex
		vertices.remove(vertex.getPos());
	}
	
	/**
	 * Removes an Edge from a graph.
	 * 
	 * @param e Edge to be removed
	 * @throws IllegalArgumentException if e is an invalid Edge
	 */
	@Override
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validateE(e);
		Vertex<V>[] varr = edge.getEndpoints();
		InnerVertex<V> vertexA = validateV(varr[0]);
		InnerVertex<V> vertexB = validateV(varr[1]);

		// removes incident nodes from incident list of each vertex
		vertexA.getOutgoing().remove(edge.getOriginIncident());
		vertexB.getIncoming().remove(edge.getDestinationIncident());

		// remove edge
		edges.remove(edge.getPos());

	}
	
	/**
	 * Returns true if e is a directed Edge.
	 * 
	 * @param e Edge to be checked
	 * @return True if e is directed Edge, false otherwise
	 * @throws IllegalArgumentException if e is an invalid Edge
	 */
	@Override
	public boolean isDirected(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validateE(e);
		edge.validate(this);
		return isDirected;
	}
	
	/**
	 * Insert and return a new directed edge with origin v and destination w,
	 * storing element o.
	 * 
	 * @param v Origin Vertex
	 * @param w Destination Vertex
	 * @param o Element stored
	 * @return Edge with origin v and destination w
	 * @throws IllegalArgumentException if u or v is an invalid vertex, or if an
	 *                                  edge already exists
	 */
	@Override
	public Edge<E> insertDirectedEdge(Vertex<V> v, Vertex<V> w, E o) throws IllegalArgumentException {
		return insertEdge(v, w, o);
	}
	

	/**
	 * Returns true if v and w are adjacent.
	 * 
	 * @param v First Vertex to be checked
	 * @param w Second Vertex to be checked
	 * @return true if they're adjacent, false otherwise
	 * @throws IllegalArgumentException if u or v is an invalid vertex
	 */
	@Override
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws IllegalArgumentException {
		InnerVertex<V> vertexA = validateV(v);
		InnerVertex<V> vertexB = validateV(w);
		return (getEdge(vertexA, vertexB) != null);
	}
	
	/**
	 * Replace element at Vertex v with {@code element}.
	 * 
	 * @param v Vertex whose element is to be replaced
	 * @param element New element
	 * @return Old element at Vertex v
	 * @throws IllegalArgumentException if v is an invalid vertex
	 */
	@Override
	public V replace(Vertex<V> v, V element) throws IllegalArgumentException {
		InnerVertex<V> vertex = validateV(v);
		V old = vertex.getElement();
		vertex.setElement(element);
		return old;
	}
	
	/**
	 * Replace element at Edge e with {@code element}.
	 * 
	 * @param e Edge whose element is to be replaced
	 * @param element New element
	 * @return Old element at Edge e
	 * @throws IllegalArgumentException if e is an invalid edge
	 */
	@Override
	public E replace(Edge<E> e, E element) throws IllegalArgumentException {
		InnerEdge<E> edge = validateE(e);
		E old = edge.getElement();
		edge.setElement(element);
		return old;
	}

	/**
	 * Represents the path between two vertices and its weighted edge. String is in
	 * the form "[String] [String] [int] [String]"
	 * 
	 * @param u Origin vertex
	 * @param v Destination vertex
	 * @return String representation of the path between u, v
	 */
	public String pathBetweenVertex(Vertex<V> u, Vertex<V> v) {
		InnerVertex<V> origin = validateV(u);
		InnerVertex<V> destination = validateV(v);
		InnerEdge<E> edge = validateE(getEdge(u, v));

		return origin.getElement() + " " + destination.getElement() + " " + edge.getElement() + " "
				+ edge.getModeOfTransportation();

	}

	/**
	 * Represents the path between two vertices and its weighted edge. String is in
	 * the form "[String] [String] [int] [String]"
	 * 
	 * @param e Edge of path
	 * @return String representation of the path between u, v
	 */
	public String pathBetweenVertex(Edge<E> e) {
		InnerEdge<E> edge = validateE(e);
		Vertex<V>[] endPoints = endVertices(edge);
		return pathBetweenVertex(endPoints[0], endPoints[1]);
	}
	/**
	 * String representation of Graph
	 * 
	 * @return String representation of Graph
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Vertex<V> v : vertices()) {
			sb.append("Vertex " + v.getElement() + "\n");
			if (isDirected) {
				sb.append("\t[outgoing]");
			}
			sb.append("\t" + OutDegree(v) + " adjacent vertices:\n");
			for (Edge<E> e : outgoingEdges(v)) {
				sb.append("\t\t");
				sb.append(String.format("(node %s to node %s via edge %s)", v.getElement(), opposite(v, e).getElement(),
						e.getElement()));
				sb.append("\n");
			}
			sb.append("\n");
			if (isDirected) {
				sb.append("\t[incoming]");
				sb.append("\t" + inDegree(v) + " adjacent vertices:\n");
				for (Edge<E> e : incomingEdges(v)) {
					sb.append("\t\t");
					sb.append(String.format("(node %s to node %s via edge %s)", v.getElement(),
							opposite(v, e).getElement(), e.getElement()));
					sb.append("\n");
				}
				sb.append("\n");
			}
		}

		return sb.toString();
	}

}
