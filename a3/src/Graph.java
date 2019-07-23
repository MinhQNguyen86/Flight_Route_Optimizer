/**
 * An interface for a graph structure.
 * 
 * @author Minh Nguyen
 * @author Christopher Nguyen
 */
public interface Graph<V, E> {
	/**
	 * Returns the number of vertices of the graph.
	 * 
	 * @return integer corresponding to amount of vertices
	 */
	public int numVertices();

	/**
	 * Return the number of edges of the graph.
	 * 
	 * @return integer corresponding to amount of edges
	 */
	public int numEdges();

	/**
	 * Returns the vertices of graph as an iterable collection.
	 * 
	 * @return Iterable collection of Vertex objects
	 */
	public Iterable<Vertex<V>> vertices();

	/**
	 * Returns the edges of graph as an iterable collection.
	 * 
	 * @return Iterable collection of Edge objects
	 */
	public Iterable<Edge<E>> edges();

	/**
	 * Returns the edge between Vertex u and v; null if they're non adjacent.
	 * 
	 * @param u First Vertex to be checked
	 * @param v Second Vertex to be checked
	 * @return {@code Edge<E>} if an edge exists, null otherwise
	 * @throws IllegalArgumentException if v or u is not a valid vertex
	 */
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;

	/**
	 * Return number of edges for which Vertex v is destination.
	 * 
	 * @param v Vertex to be checked
	 * @return integer value of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public int inDegree(Vertex<V> v) throws IllegalArgumentException;

	/**
	 * Returns the number of edges leaving Vertex v.
	 * 
	 * @param v Vertex to be checked
	 * @return integer of edges leaving v
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public int OutDegree(Vertex<V> v) throws IllegalArgumentException;

	/**
	 * Returns an iterable collection of edges where v is the ending vertex.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;

	/**
	 * Returns an iterable collection of edges where v is the starting vertex.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;
	
	/**
	 * Returns an iterable collection of edges incident to v.
	 * 
	 * @param v Vertex to be checked
	 * @return Iterable collection of edges
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws IllegalArgumentException;
	
	/**
	 * Inserts and returns a new Vertex with given element.
	 * 
	 * @param element Element of Vertex to be inserted
	 * @return Vertex that was inserted
	 */
	public Vertex<V> insertVertex(V element);

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
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;

	/**
	 * Removes a Vertex and all of its incident edges from graph.
	 * 
	 * @param v Vertex to be removed
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException;

	/**
	 * Removes an Edge from a graph.
	 * 
	 * @param e Edge to be removed
	 * @throws IllegalArgumentException if e is an invalid Edge
	 */
	public void removeEdge(Edge<E> e) throws IllegalArgumentException;
	
	/**
	 * Returns true if e is a directed Edge.
	 * 
	 * @param e Edge to be checked
	 * @return True if e is directed Edge, false otherwise
	 * @throws IllegalArgumentException if e is an invalid Edge
	 */
	public boolean isDirected(Edge<E> e) throws IllegalArgumentException;

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
	public Edge<E> insertDirectedEdge(Vertex<V> v, Vertex<V> w, E o) throws IllegalArgumentException;
		
	/**
	 * Returns the vertices of Edge e as an array of length 2. If the graph is
	 * directed, the first Vertex is the origin, and the second is the destination.
	 * If the graph is undirected, the order is arbitrary.
	 * 
	 * @param e Edge to be checked
	 * @return
	 * @throws IllegalArgumentException if e is not a valid edge
	 */
	public Vertex<V>[] endVerticles(Edge<E> e) throws IllegalArgumentException;

	/**
	 * Returns the Vertex that is opposite Vertex v on Edge e.
	 * 
	 * @param v Vertex to be checked
	 * @param e Edge to be checked
	 * @return Vertex opposite to v on e
	 * @throws IllegalArgumentException if v/e is not a valid vertex/edge
	 */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;
	
	/**
	 * Returns true if v and w are adjacent.
	 * 
	 * @param v First Vertex to be checked
	 * @param w Second Vertex to be checked
	 * @return true if they're adjacent, false otherwise
	 * @throws IllegalArgumentException if u or v is an invalid vertex
	 */
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws IllegalArgumentException;
	
	/**
	 * Replace element at Vertex v with {@code element}.
	 * 
	 * @param v Vertex whose element is to be replaced
	 * @param element New element
	 * @return Old element at Vertex v
	 * @throws IllegalArgumentException if v is an invalid vertex
	 */
	public E replace(Vertex<V> v, E element) throws IllegalArgumentException ;
	
	/**
	 * Replace element at Edge e with {@code element}.
	 * 
	 * @param e Edge whose element is to be replaced
	 * @param element New element
	 * @return Old element at Edge e
	 * @throws IllegalArgumentException if e is an invalid edge
	 */
	public E replace(Edge<E> e, E element) throws IllegalArgumentException;

	// https://github.com/rogeriogentil/data-structures-and-algorithms/blob/master/src/main/java/rogeriogentil/data/structures/chapter14/AdjacencyMapGraph.java
}
