import java.util.Iterator;


/**
 * Simplified collection classes and interfaces for is207.
 * Collection is the granddaddy of all collections classes and interfaces.
 * This inteface specifies the methods that are common to all
 * collections. See the J2SE javadoc for a more precise explanation of
 * what these methods do.
 * 
 * @param E the type of object that the collection can contain.
 * @author even
 */
public interface Collection<E> extends Iterable<E> {
    /**
     * Add the object obj to the collection
     * @return true if the collection was modidfied, false otherwise
     * @param obj the object to be added.
     */
    boolean add(E obj);

    /**
     * Remove all elements from the collection.
     */
    void clear();

    /**
     * Check whether the object obj is a member of the
     * collection. It is a member of the there if there is an
     * object in the collection that is equal() to obj.
     * @return true if the object is a member, false otherwise
     * @param
     */
    boolean contains(E obj);

    /**
     * @return true if the collection is empty.
     */
    boolean isEmpty();

    /**
     * Get an iterator that will traverse the collection. The
     * iterator's next() method will return every object in the collection
     * exactly once. The ordering of the objects is not specified, but
     * subclasses may be more specific about ordering.
     *@return the iterator
     */
    @Override
    Iterator<E> iterator();

    boolean remove(E obj);

    /**
     * Get the size of the collection (the number of elements).
     * @return the size
     */
    int size();
}
