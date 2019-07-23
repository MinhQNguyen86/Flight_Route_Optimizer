/**
 * @author Minh Nguyen
 * @author Christopher Nguyen
 */
public class DNode<E> implements Position<E> {
	
	private E element;
	private DNode<E> prev;
	private DNode<E> next;
	
	/**
	 * Constructs a DNode object with element {@code e},
	 * and links its previous node to {@code p}, and next
	 * node to {@code n}.
	 * 
	 * @param e Element which DNode holds
	 * @param p Previous node
	 * @param n Next node
	 */
	public DNode(E e, DNode<E> p, DNode<E> n) {
		element = e;
		prev = p;
		next = n;
	}
	
	/**
	 * Gets the next node
	 * 
	 * @return Next node
	 */
	public DNode<E> getNext() { return next; }
	
	/**
	 * Sets the next node
	 * 
	 * @param next Next node
	 */
	public void setNext(DNode<E> next) { this.next = next; }

	/**
	 * Gets the previous node
	 * 
	 * @return Previous node
	 */
	public DNode<E> getPrev() {	return prev; }

	/**
	 * Sets the previous node
	 * 
	 * @param prev Previous node
	 */
	public void setPrev(DNode<E> prev) { this.prev = prev; }


	/**
	 * Returns the element stored at this position.
	 *
	 * @return the stored element
	 * @throws IllegalStateException if position no longer valid
	 */
	@Override
	public E getElement() throws IllegalStateException { return element; }
	
	/**
	 * Sets the element of the node
	 * 
	 * @param element Element to be set to
	 */
	public void setElement(E element) {
		this.element = element;
	}
	
	/**
	 * Return a String of the element plus a space
	 * 
	 * @return String containing element + " ";
	 */
	@Override
	public String toString() {
		return element + " ";
	}
	

}
