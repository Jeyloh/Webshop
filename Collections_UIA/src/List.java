import java.util.ListIterator;


/**
 * Simplified collection classes and interfaces for is207.
 * List extends Collections that are only meaningful when the elements
 * are in a sequence (every object except the first has one in front of it,
 * and all except the last have one behind it. When the elements are
 * ordered in a sequence, we can give them position numbers by counting
 * from the front. By convention the first position is numbered 0 (zero).
 * 
 * @author even
 */
public interface List<E> extends Collection<E> {
    /** Get the element at position index */
    E get(int index);

    /** Replace the element at position index with obj.
     * @return the old element
     */
    E set(int index, E obj);

    /**
     * Insert an element at position index.
     * The existing elements at position index and higher, are moved one
     * to make room for the new element */
    void add(int index, E obj);

    /** Return the index of obj */
    int indexOf(E obj);

    /** Remove the element at position index. The elements with larger
     * indices are moved one step to fill the gap.
     * @return the removed element
     */
    E remove(int index);

    /**
     * @return a ListIterator(), which is an iterator that is
     * aware of positions in the list, and can navigate in both
     * directions along the list.
     */
    ListIterator<E> listIterator();
}
