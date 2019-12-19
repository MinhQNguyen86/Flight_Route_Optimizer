/**
 * @author Minh Nguyen
 */
public interface PositionalList<E> {

	/**
	 * Returns size of list.
	 * 
	 * @return integer size of list
	 */
	public int size();

	/**
	 * Checks if list is empty.
	 * 
	 * @return True if empty, false otherwise.
	 */
	public boolean isEmpty();

	/**
	 * Returns the Position of the first node (null if empty).
	 * 
	 * @return Position of first node.
	 */
	public Position<E> first();

	/**
	 * Returns the Position of the last node (null if empty).
	 * 
	 * @return Position of last node.
	 */
	public Position<E> last();

	/**
	 * Returns the Position immediately before Position p (null if p is last).
	 * 
	 * @return Position of node before {@code p}.
	 * @param p Position of element after return node
	 * @throws IllegalArgumentException if p is not valid
	 */
	public Position<E> before(Position<E> p) throws IllegalArgumentException;

	/**
	 * Returns the Position immediately after Position p (null if p is last).
	 * 
	 * @return Position of node after {@code p}.
	 * @param p Position of element before return node
	 * @throws IllegalArgumentException if p is not valid
	 */
	public Position<E> after(Position<E> p) throws IllegalArgumentException;
	
	/**
	 * Inserts element e at the front and returns new Position.
	 * 
	 * @return Position of inserted element
	 * @param e Element to be added 
	 */
	public Position<E> addFirst(E e);

	/**
	 * Inserts element e at the back and returns new Position.
	 * 
	 * @return Position of inserted element
	 * @param e Element to be added
	 */
	public Position<E> addLast(E e);

	/**
	 * Inserts element e immediately before Position p, and return new Position.
	 * 
	 * @return Position of inserted element
	 * @param p Position of node to be added before
	 * @param e Element to be added
	 * @throws IllegalArgumentException if p is not valid
	 */
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;

	/**
	 * Inserts element e immediately after Position p, and return new Position.
	 * 
	 * @return Position of inserted element
	 * @param p Position of node to be added after
	 * @param e Element to be added
	 * @throws IllegalArgumentException if p is not valid
	 */
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

	/**
	 * Replace the element at Position p and returns the replaced element.
	 * 
	 * @return Original element at {@code p}
	 * @param p Position of element to be replaced
	 * @param e New element
	 * @throws IllegalArgumentException if p is not valid
	 */
	public E set(Position<E> p, E e) throws IllegalArgumentException;

	/**
	 * Removes the element stored at Position p and returns the replaced element.
	 * 
	 * @return Element that was at p
	 * @param p Position of element to be removed
	 * @throws IllegalArgumentException if p is not valid
	 */
	public E remove(Position<E> p) throws IllegalArgumentException;
}