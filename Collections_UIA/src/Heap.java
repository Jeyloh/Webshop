import java.util.Iterator;



/**
 * Simple implementation of a binary heap. The corresponding class in
 * the standard library is called PriorityQueue.
 *
 * This class uses an ArrayList, not an array. This simplifies the
 * implementation, as many of the methods inherited from Collection
 * can be delegated to the ArrayList.
 *
 * @author even
 */
public class Heap<E extends Comparable<E>> implements Collection<E> {

    protected ArrayList<E> heap;


    public Heap() {
        heap = new ArrayList<>();
    }


    public Heap(E[] data, int size) {
        heap = new ArrayList<>();
        for (int i = 0; i<size; i++)
            heap.add(data[i]);
        makeHeap();
    }


    /**
     * We can not expect that the ArrayList parameter to the
     * constructor above is a heap. The elements must be reordered to
     * conform to the heap condition.
     * <ul>
     * <li>One possibility is to insert the elements using the add()
     * or offer() methods, which gives O(n*log n) performance for
     * making a heap from a random ordered array,</li>
     * <li>but it can be done much more efficiently if the heap is
     * reordered bottom up using the trickleDown() method.</li>s It
     * can be proven (see "binary heap" in wikipedia) that this
     * results in O(n) performance for creating the heap
     */
    private void makeHeap() {
        for (int i = heap.size()/2; i>=0; i--) {
            trickleDown(i);
        }
    }


    @Override
    public int size() {
        return heap.size();
    }


    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }


    @Override
    public boolean contains(E obj) {
        return heap.contains(obj);
    }


    @Override
    public void clear() {
        heap.clear();
    }


    /**
     * A heap is only partially ordered, so there is no requirement
     * for returning the elements in a particular order. The iterator
     * in the ArrayList will be just as good as anything else
     */
    @Override
    public Iterator<E> iterator() {
        return heap.iterator();
    }


    /**
     * Adds obj to the priority queue (heap)
     */
    @Override
    public boolean add(E obj) {
        int i = heap.size();
        heap.add(obj);
        // obj is now the last element in the heap, at index i.
        // it must be pushed (or pulled?) upwards, until it is smaller
        // than both it's children
        trickleUp(i);
        print("etter add "+obj);
        return true;
    }


    private void trickleUp(int i) {
        E obj = heap.get(i);
        int p = parent(i);
        while (i>0&&heap.get(p).compareTo(obj)>0) {
            // parent p is greater than obj - move it down
            heap.set(i, heap.get(p));
            i = p;
            p = parent(i);
        }
        heap.set(i, obj);
    }


    public boolean offer(E e) {
        return add(e);
    }


    /**
     * Return, but do not remove the first element
     */
    public E peek() {
        return heap.get(0);
    }


    /**
     * Remove and return the first element. Return null if heap is
     * empty
     */
    public E poll() {
        E oldRoot = heap.get(0);
        switch (size()) {
        case 0:
            return null;
        case 1:
            return heap.remove(heap.size()-1);
        default:
            E newRoot = heap.remove(heap.size()-1);
            heap.set(0, newRoot);
            // newroot must be moved down, it is not the smallest object in the queue
            trickleDown(0);
        }
        return oldRoot;
    }


    private void trickleDown(int i) {
        E val = heap.get(i);
        int c = smallestChild(i);
        while (c>0&&heap.get(c).compareTo(val)<0) {
            heap.set(i, heap.get(c));
            i = c;
            c = smallestChild(i);
        }
        heap.set(i, val);
    }


    private int smallestChild(int i) {
        int l = left(i);
        if (l<heap.size()) {
            int lc = l;
            int r = right(i);
            if (r<heap.size()&&heap.get(r).compareTo(heap.get(l))<0)
                lc = r;
            return lc;
        }
        else
            return -1;
    }


    /**
     * The remove method is used to remove any object from the queue,
     * i.e. not necessarily the first one. This is similar to poll(),
     * except that we're removing an object from a "random" position
     * in the heap.
     */
    @Override
    public boolean remove(E obj) {
        int i = heap.indexOf(obj);
        E replacement = heap.remove(heap.size()-1);
        heap.set(i, replacement);
        trickleDown(i);
        return true;
    }

    // private helper methods

    protected int parent(int node) {
        return (node-1)/2;
    }


    protected int left(int node) {
        return 2*node+1;
    }


    protected int right(int node) {
        return 2*node+2;
    }


    public void print(String prefix) {
        System.out.print("\n"+prefix+": {");
        for (E e : heap)
            System.out.print(" "+e);
        System.out.println(" }");

        tprint(0, "");
    }


    private void tprint(int n, String prefix) {
        if (n>=heap.size()) {
            System.out.println(prefix+"(empty)");
            return;
        }

        int l = left(n);
        int r = right(n);
        if (r<heap.size())
            tprint(r, prefix+"   ");
        System.out.println(prefix+heap.get(n));
        if (l<heap.size())
            tprint(l, prefix+"   ");

    }


    public static void main(String[] args) {
        Heap h = new Heap();

        h.add(3);
        h.add(5);
        h.add(1);
        h.add(62);
        h.add(2);
        h.add(2);
        h.add(6);
        h.add(5);

        while (h.size()>0) {
            System.out.println(h.poll());
        }
    }
}
