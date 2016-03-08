import java.util.Iterator;


/**
 * Simple Collections implementation for IS-207
 *
 * The purpose of the AbstractCollection class, is to make it easier
 * to implement the Collection interface. Subclasses must implement add(),
 * iterator() and size(). The remaining methods are implemented using
 * those three.
 * 
 * @author even
 */
public abstract class AbstractCollection<E> implements Collection<E> {
    protected AbstractCollection() {
    }

    /**
     * This is not a particularly fast way of implementing clear(),
     * but it will always work (provided that the concrete subclass
     * implements iterator properly of course....
     */
    @Override
    public void clear() {
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
    }

    /**
     * This implementation of contains() will also work regardless of how
     * the collection is implemented. Provided that the iterator() method (and
     * the iterator class) are implemented correctly.
     * @param obj the object to look for.
     * @return true if the collection contains an object equal() to obj.
     */
    @Override
    public boolean contains(E obj) {
        Iterator<E> it = iterator();
        // There are two loops here. The first one handles the
        // case where obj == null (some collections may contain null values)
        if (null == obj) {
            while (it.hasNext()) {
                if (null == it.next()) return true;
            }
        }
        // and the second loop handles the "normal" case, using equals().
        else {
            while (it.hasNext()) {
                if (obj.equals(it.next())) return true;
            }
        }
        return false;
    }

    /**
     * The implementation of isEmpty() expects a correct implementation
     * of size().
     * @return true is the collection is empty.
     */
    @Override
    public boolean isEmpty() {
        return 0 == size();
    }

    @Override
    public abstract Iterator<E> iterator();

    /**
     * Remove follows the same pattern as contains()
     * @param obj
     * @return true if (the first) object equal() to obje was
     * removed from the collection.
     */
    @Override
    public boolean remove(E obj) {
        Iterator<E> it = iterator();
        if (null == obj) {
            while (it.hasNext()) {
                if (null == it.next()) {
                    it.remove();
                    return true;
                }
            }
        }
        else {
            while (it.hasNext()) {
                if (obj.equals(it.next())) {
                    it.remove();
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public abstract int size();

}
