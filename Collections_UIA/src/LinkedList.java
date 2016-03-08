import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;



/**
 * LinkedList is the other way to implement the list ADT. It chains
 * the elements together with list nodes.
 *
 * This list class uses double linked nodes, and a head/tail node
 * tying the ends of the list together, to form a circular structure.
 *
 * @author even
 */
public class LinkedList<E>
        extends AbstractCollection<E>
        implements List<E> {

    public static long getCount;
    /**
     * This implementation uses a header node. head.next points to the
     * first node in the list, and head.prev points to the last node.
     * This makes the list appear circular, and we avoid some special
     * cases. If the list is empty head.next and head.prev will point
     * to head itself
     */
    private ListNode head;
    private int size;


    /**
     * Constructor creates the head node, and makes it point to
     * itself.
     */
    public LinkedList() {
        head = new ListNode(null);
        head.prev = head;
        head.next = head;
        size = 0;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }


    @Override
    public boolean add(E obj) {
        linkBefore(obj, head);
        size++;
        return true;
    }


    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).value;
    }


    @Override
    public E set(int index, E obj) {
        checkIndex(index);
        ListNode node = getNode(index);
        E old = node.value;
        node.value = obj;
        return old;
    }


    @Override
    public void add(int index, E obj) {
        checkIndex(index);
        ListNode next = getNode(index);
        ListNode node = new ListNode(obj);
        node.linkBetween(next.prev, next);
        size++;
    }


    @Override
    public int indexOf(E obj) {
        int i = 0;
        for (ListNode n = head.next; n!=head; n = n.next) {
            if (n.value.equals(obj))
                return i;
            else
                i++;
        }
        return -1;
    }


    @Override
    public E remove(int index) {
        checkIndex(index);
        ListNode node = getNode(index);
        node.unlink();
        size--;
        return node.value;
    }


    @Override
    public ListIterator<E> listIterator() {
        return new Iter();
    }


    /**
     * check that an index is valid
     */
    private void checkIndex(int i) {
        if (i<0||i>=size) {
            throw new IndexOutOfBoundsException();
        }
    }


    /**
     * get the index'th node from the list
     */
    private ListNode getNode(int ind) {
        ListNode n = head.next;
        for (int i = 0; i<ind; i++) {
            n = n.next;
            getCount++;
        }
        return n;
    }


    /**
     * inset a new node with value e in front of node
     */
    private void linkBefore(E e, ListNode node) {
        ListNode n = new ListNode(e);
        n.linkBetween(node.prev, node);
    }



    /**
     * ListNode
     */
    private class ListNode {

        private E value;
        private ListNode prev;
        private ListNode next;


        public ListNode(E e, ListNode next) {
            this.value = e;
            this.next = next;
        }


        public ListNode(E e) {
            value = e;
        }


        /**
         * Splice this node into the list between prev and next.
         */
        public void linkBetween(ListNode prev, ListNode next) {
            // make sure that next and prev are neighbours
            if (next.prev!=prev) {
                throw new IllegalArgumentException();
            }
            this.prev = prev;
            this.next = next;
            prev.next = this;
            next.prev = this;
        }


        /**
         * Remove this node from the list by splicing together the
         * next and previous nodes, before cutting the prev and next
         * links
         */
        public void unlink() {
            prev.next = next;
            next.prev = prev;
            prev = next = null;
        }
    }
    // end ListNode



    /**
     * Iterator implementation
     */
    private class Iter implements ListIterator<E> {

        ListNode nextReturn;
        ListNode lastReturn;


        public Iter() {
            nextReturn = head.next;
            lastReturn = null;

        }


        @Override
        public boolean hasNext() {
            return nextReturn!=head;
        }


        @Override
        public E next() {
            if (hasNext()) {
                E e = nextReturn.value;
                lastReturn = nextReturn;
                nextReturn = nextReturn.next;
                return e;
            }
            throw new NoSuchElementException();
        }


        @Override
        public boolean hasPrevious() {
            return nextReturn.prev!=head;
        }


        @Override
        public E previous() {
            if (hasPrevious()) {
                nextReturn = nextReturn.prev;
                lastReturn = nextReturn;
                return nextReturn.value;
            }
            throw new NoSuchElementException();
        }


        @Override
        public int nextIndex() {

            throw new UnsupportedOperationException("Not supported yet.");
        }


        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("Not supported yet.");
        }


        @Override
        public void remove() {
            if (nextReturn==lastReturn)
                nextReturn = nextReturn.next;
            lastReturn.unlink();
            lastReturn = null;
            size--;
        }


        @Override
        public void set(E e) {
            lastReturn.value = e;
        }


        @Override
        public void add(E e) {
            ListNode n = new ListNode(e);
            n.linkBetween(lastReturn.prev, lastReturn);
            size++;
        }
    }
   // end iterator
}
