/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;



/**
 * A ringbuffer implementation of queue. The queue 'wraps around' the end of the
 * array and continues at the beginning of the array.
 *
 * A word about the remainder operator (%). X % Y calculates the remainder of
 * dividing x by y. Think of it as dividing x ice creams equally among y
 * children. Unless x is an exact multiple of y, you will get some left over
 * when the children have all got the same number of ice creams. The number of
 * ice creams left over will be less than the number of children, and not less
 * than zero. This number is the remainder. It's usefulness in the ringbuffer is
 * obvious if you think of the index as the x and the size of the array as y.
 * The result of x % y is a number in the range 0-arraysize, so we can use it to
 * constrain indices. We can just increment the index as if the array continues
 * forever, and use the remainder operator to bring it back to a legal index
 * within the array.
 *
 * @author even
 */
public class RingBuffer<E> implements Queue<E> {

    private E[] ring;
    private int front;
    private int back;
    private int size;
    private boolean canGrow;


    public static void main(String[] args) {
        RingBuffer<String> q = new RingBuffer<>(true);
        q.offer("Anton");
        q.offer("Billy");
        q.offer("Charlie");
        System.out.println(q);
        q.offer("Delta");
        System.out.println(q);
        q.poll();
        System.out.println(q);
        q.offer("Echo");
        System.out.println(q);
        q.offer("Foxtrot");
        System.out.println(q);
        q.offer("Golf");
        System.out.println(q);

    }


    public RingBuffer() {
        this(false);
    }


    public RingBuffer(boolean canGrow) {
        this.canGrow = canGrow;
        this.front = 0;
        this.back = 0;
        this.size = 0;
        ring = (E[]) (new Object[4]);
    }


    private boolean checkCapacity() {
        if (size==ring.length) {
            if (canGrow) {
                int j = 0;
                E[] newRing = (E[]) (new Object[2*ring.length]);
                for (int i = 0; i<size; i++) {
                    newRing[i] = ring[(front+i)%ring.length];
                }
                ring = newRing;
                front = 0;
                back = size;
            }
            else
                return false;
        }
        return true;
    }


    @Override
    public boolean add(E e) throws IllegalStateException {
        if (!offer(e))
            throw new IllegalStateException();
        return true;
    }


    @Override
    public E remove() {
        if (peek()==null)
            throw new IllegalStateException();
        else
            return poll();
    }


    @Override
    public E element() {
        if (peek()==null)
            throw new IllegalStateException();
        else
            return peek();
    }


    @Override
    public boolean offer(E e) {
        if (checkCapacity()) {
            ring[back] = e;
            back = (back+1)%ring.length;
            size++;
            return true;
        }
        else
            return false;
    }


    @Override
    public E poll() {
        if (size>0) {
            E retVal = ring[front];
            ring[front] = null;
            front = (front+1)%ring.length;
            size--;
            return retVal;
        }
        return null;
    }


    @Override
    public E peek() {
        if (size>0)
            return ring[front];
        else
            return null;
    }


    @Override
    public void clear() {
        this.front = 0;
        this.back = 0;
        this.size = 0;
        ring = (E[]) (new Object[16]);
    }


    @Override
    public boolean contains(E obj) {
        for (int i = front; inQueue(i); i = (i+1)%ring.length) {
            if (ring[i].equals(obj))
                return true;
        }
        return false;
    }


    // Check whether an index points to an element in the queue
    private boolean inQueue(int index) {
        if (size==0)
            return false;
        if (front<back)
            return (front<=index)&&(index<back);
        else {
            if ((front<index)&&(index<ring.length))
                return true;
            if ((0<=index)&&(index<back))
                return true;
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return size==0;
    }


    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    public boolean remove(E obj) {
        int pos = posOf(obj);
        if (pos>=0) {
            for (int i = pos+1; i<size; i++) {
                int from = (front+i)%ring.length;
                int to = (front+i-1)%ring.length;
                ring[to] = ring[from];
            }
            size--;
            if (back==0)
                back = ring.length;
            back--;

            return true;
        }
        if (false) { // pos >= 0
            // alternativ kode:
            size--;
            if (back==0)
                back = ring.length;
            back--;

            if (pos<back) {
                for (int i = (front+pos)%ring.length; i<back; i++)
                    ring[i] = ring[i+1];
            }
            else {
                for (int i = pos+1; i<ring.length; i++)
                    ring[i-1] = ring[i];
                ring[ring.length-1] = ring[0];
                for (int i = 0; i<back; i++)
                    ring[i] = ring[i+1];
            }
        }
        return false;
    }


    private int posOf(E obj) {
        for (int i = 0; i<size; i++) {
            int index = (front+i)%ring.length;
            if (ring[index].equals(obj)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    public int size() {
        return size;
    }


    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("RingBuffer (logical) [");
        for (int i = 0; i<size; i++) {
            buf.append(" ");
            buf.append(ring[(front+i)%ring.length]);
        }
        buf.append(" ],f=");
        buf.append(front);
        buf.append(", b=");
        buf.append(back);
        buf.append("\n          (Physical) [");
        for (int i = 0; i<size; i++) {
            buf.append(" ");
            buf.append(ring[i]);
        }
        buf.append(" ]");
        return buf.toString();
    }
}
