/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This is a very simpleminded array-based implementation of a queue.
 * It illustrates some of the pitfalls: It is fixed size, but if it was allowed
 * to grow it would waste large amounts of memory, because the front of the array
 * will not be reused.
 * @author even
 */
public class SimpleArrayQueue<E> implements Queue<E> {
    private static final int INITIAL_SIZE = 16;

    //Queue state
    private E[] queue;

    /** Index of the first element in the queue */
    private int front;
    /** Index of the cell behind the last element */
    private int back;


    public SimpleArrayQueue() {
        queue = (E[])(new Object[INITIAL_SIZE]);
        front = back = 0;
    }

    /* Exception-throwing methods from the Queue interface */

    /** Throw an exception if insert (delegated to offer()) fails. */
    @Override
    public boolean add(E e) throws IllegalStateException {
        if (offer(e)) return true;
        else throw new IllegalStateException();
    }

    /** Throw an exception if poll() fails */
    public E remove() {
        E e = poll();
        if (null == e) throw new NoSuchElementException();
        return e;
    }

    @Override
    public E element() {
        E e = peek();
        if (null == e) throw new NoSuchElementException();
        return e;
    }

    /* Status returning methods from Queue */
    @Override
    public boolean offer(E e) {
        if (null == e) throw new IllegalArgumentException();
        if (back < queue.length) {
            queue[back] = e;
            back++;
            return true;
        }
        else return false;
    }

    @Override
    public E poll() {
        if (front < back && front < queue.length) {
            E e = queue[front];
            front++;
            return e;
        }
        else {
            return null;
        }
    }

    @Override
    public E peek() {
        if (front < back && front < queue.length) {
            E e = queue[front];
            return e;
        }
        else return null;
    }

    /* Methods inherited from Collection */

    @Override
    public void clear() {
        front = back = 0;
    }

    @Override
    public boolean contains(E obj) {
        for (int i=front; i<back; i++) {
            if (queue[i].equals(obj)) return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return front == back;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean remove(E obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int size() {
        return back - front;
    }
}
