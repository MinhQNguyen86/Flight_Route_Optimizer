import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @author Minh Nguyen
 */
public class NodePositionalList<E> implements PositionalList<E>, Iterable<E> {
	
	private DNode<E> header;
	private DNode<E> trailer;
	private int size = 0;
	
	/**
	 * Constructs a Doubly-linked list with a header and
	 * trailer pointing to each other.
	 */
	public NodePositionalList() {
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(null, header, null);
		header.setNext(trailer);
	}
	
	/**
	 * Constructs a Doubly-linked list with a header and
	 * trailer pointing to each other, and one Node. 
	 * 
	 * @param e Element to be added as DNode
	 */
	public NodePositionalList(E e) {
		this();
		addFirst(e);
	}
	
	/**
	 * Validates the position and returns it as a node.
	 * 
	 * @param p Position to be checked
	 * @return DNode if Position p is valid
	 * @throws IllegalArgumentException if p is not a DNode or if p is not in the list
	 */
	private DNode<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof DNode)) throw new IllegalArgumentException("Invalid p");
		DNode<E> node = (DNode<E>) p;
		if (node.getNext() == null)
			throw new IllegalArgumentException("p is no longer in the list");
		return node;
	}
	
	/**
	 * Checks if {@code v} is a valid position.
	 * 
	 * @param v Position to be checked
	 * @return TreePosition if Position v is valid
	 * @throws InvalidPositionException if p is null or is not a DNode
	 */
	/*
	protected Position<E> checkPosition(Position<E> v) throws InvalidPositionException {
		if (v == null || !(v instanceof DNode))
			throw new InvalidPositionException("The position is invalid");
		return (TreePosition<E>) v;
	}
	*/

	/**
	 * Returns given node as a Position or null if a sentinels.
	 * 
	 * @param node Node to be checked
	 * @return null if {@code node} is header or trailer, else return {@code node}
	 */
	private Position<E> position(DNode<E> node) {
		if (node == header || node == trailer)
			return null;
		return node;
	}
	//------------- Nested PositionIterator class --------------
	private class PositionIterator implements Iterator<Position<E>> {
		private Position<E> cursor = first();
		private Position<E> recent = null;
		
		@Override
		public boolean hasNext() { return (cursor != null); }

		@Override
		public Position<E> next() throws NoSuchElementException {
			if (cursor == null) throw new NoSuchElementException();
			recent = cursor;
			cursor = after(cursor);
			return recent;
		}
		
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException("Unsupported Operation");
		}
	} //----------- End of nested PositionIterator class
	
	//-------------- Nested PositionIterable class -----------
	private class PositionIterable implements Iterable<Position<E>> {
		public Iterator<Position<E>> iterator() { return new PositionIterator(); }
	} //----------- End of nested PositionIterable class -----
	
	/**
	 * Returns an Iterable representation of list's positions
	 * 
	 * @return Iterable representation of list's positions
	 */
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	
	//--------------- Nested ElementIterator class ----------
	private class ElementIterator implements Iterator<E> {
		Iterator<Position<E>> posIterator = new PositionIterator();
		public boolean hasNext() { return posIterator.hasNext(); }
		public E next() { return posIterator.next().getElement(); } // return element!
		public void remove() { posIterator.remove(); }
	} //------------- End of nested ElementIterator class ----
	
	/**
	 *	Returns an iterator of the elements stored in the list
	 *
	 *	@return {@code Iterator<E>} of elements
	 */
	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}

	/**
	 * Returns size of list.
	 * 
	 * @return integer size of list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Checks if list is empty.
	 * 
	 * @return True if empty, false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Returns the Position of the first node (null if empty).
	 * 
	 * @return Position of first node.
	 */
	@Override
	public Position<E> first() {
		return position(header.getNext());
	}
	
	/**
	 * Returns the Position of the last node (null if empty).
	 * 
	 * @return Position of last node.
	 */
	@Override
	public Position<E> last() {
		return position(trailer.getPrev());
	}
	
	/**
	 * Returns the Position immediately before Position p (null if p is last).
	 * 
	 * @return Position of node before {@code p}.
	 * @param p Position of element after return node
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		return position(node.getPrev());
	}
	
	/**
	 * Returns the Position immediately after Position p (null if p is last).
	 * 
	 * @return Position of node after {@code p}.
	 * @param p Position of element before return node
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		return position(node.getNext());
	}
	
	/**
	 * Adds element e between node {@code pre} and {@code succ}.
	 * 
	 * @param e Element to be inserted
	 * @param pre Position that will point to new node
	 * @param succ Position that new node will point to 
	 * @return Position of element that was inserted
	 */
	private Position<E> addBetween(E e, DNode<E> pre, DNode<E> succ) {
		DNode<E> newest = new DNode<E>(e, pre, succ);
		pre.setNext(newest);
		succ.setPrev(newest);
		size++;
		return newest;
	}
	
	/**
	 * Inserts element e at the front and returns new Position.
	 * 
	 * @return Position of inserted element
	 * @param e Element to be added 
	 */
	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext());
	}
	
	/**
	 * Inserts element e at the back and returns new Position.
	 * 
	 * @return Position of inserted element
	 * @param e Element to be added
	 */
	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer.getPrev(), trailer);
	}
	
	/**
	 * Inserts element e immediately before Position p, and return new Position.
	 * 
	 * @return Position of inserted element
	 * @param p Position of node to be added before
	 * @param e Element to be added
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		return addBetween(e, node.getPrev(), node);
	}

	/**
	 * Inserts element e immediately after Position p, and return new Position.
	 * 
	 * @return Position of inserted element
	 * @param p Position of node to be added after
	 * @param e Element to be added
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		return addBetween(e, node, node.getNext());
	}
	
	/**
	 * Replace the element at Position p and returns the replaced element.
	 * 
	 * @return Original element at {@code p}
	 * @param p Position of element to be replaced
	 * @param e New element
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		E answer = node.getElement();
		node.setElement(e);
		return answer;
	}
	
	/**
	 * Removes the element stored at Position p and returns the replaced element.
	 * 
	 * @return Element that was at p
	 * @param p Position of element to be removed
	 * @throws IllegalArgumentException if p is not valid
	 */
	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		DNode<E> node = validate(p);
		DNode<E> prev = node.getPrev();
		DNode<E> succ = node.getNext();
		prev.setNext(succ);
		succ.setPrev(prev);
		size--;
		E answer = node.getElement();
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		return answer;
	}
	
	/**
	 * Return a String of all elements in PositionalList
	 * 
	 * @return String containing all elements in PositionalList;
	 */
	@Override
	public String toString() {
		Iterator<E> it = new ElementIterator();
		String str = "";
		while(it.hasNext()) {
			str += it.next();
		}
		return str;
	}

}
