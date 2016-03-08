/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;



/**
 * This is a straightforwared implementation of a queue using single linked a
 * single linked list.
 *
 * This class does not accept null elements. This simplifies the code in the
 * methods that search for a specific element.
 *
 * @author even
 */
public class LinkedQueue<E> implements Queue<E> {

    public int count;
    public int n = 10000;


    public static void main(String[] args) {
        Random r = new Random();
        LinkedQueue<Integer> q = new LinkedQueue();
        for (int i = 0; i<q.n; i++)
            q.add(i);

        for (int j = 0; j<100; j++)
            q.contains(r.nextInt(q.n));
        System.out.println("coutn = "+q.count);
    }



    private class QueueNode {

        public QueueNode next;
        public E value;


        public QueueNode(E value) {
            next = null;
            this.value = value;
        }
    }

    private QueueNode first;
    private QueueNode last;
    private int size;


    public LinkedQueue() {
        first = last = null;
        size = 0;
    }


    @Override
    public boolean add(E e) throws IllegalStateException {
        boolean ret = offer(e);
        if (!ret)
            throw new IllegalStateException();
        return ret;
    }


    @Override
    public E remove() {
        if (size==0) {
            throw new NoSuchElementException();
        }
        else {
            E retval = first.value;
            first = first.next;
            size--;
            return retval;
        }
    }


    @Override
    public E element() {
        if (size==0)
            throw new NoSuchElementException();
        else {
            return first.value;
        }
    }


    @Override
    public boolean offer(E e) {
        if (null==e)
            throw new IllegalArgumentException();
        if (size==0) {
            first = new QueueNode(e);
            last = first;
        }
        else {
            last.next = new QueueNode(e);
            last = last.next;
        }
        size++;
        return true;
    }


    @Override
    public E poll() {
        if (size==0)
            return null;
        E retval = first.value;
        first = first.next;
        size--;
        return retval;
    }


    @Override
    public E peek() {
        if (size==0)
            return null;
        E retval = first.value;
        return retval;
    }


    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }


    @Override
    public boolean contains(E obj) {
        for (QueueNode qn = first; qn!=null; qn = qn.next) {
            count++;
            if (obj.equals(qn.value))
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
        // This method creates an anonymous inner class, which implements
        // Iterator, and creates a single object from the class
        // all in a single statement.
        return new Iterator<E>() {
            QueueNode cur = first;
            QueueNode lastRet = null;


            @Override
            public boolean hasNext() {
                return cur!=null;
            }


            @Override
            public E next() {
                E retVal = cur.value;
                lastRet = cur;
                cur = cur.next;
                return retVal;
            }


            @Override
            public void remove() {
                if (lastRet==null)
                    throw new IllegalStateException();
                if (first==lastRet) {
                    // remove first node
                    first = first.next;
                    if (last==lastRet)
                        last = null;
                    lastRet.next = null;
                    lastRet = null;
                }
                else {
                    // find the node before lastRet - this is not
                    // efficient, but we avoid a number of special cases
                    QueueNode prev = first;
                    while (prev.next!=lastRet)
                        prev = prev.next;
                    prev.next = lastRet.next;
                    lastRet.next = null;
                    if (last==lastRet)
                        last = prev;
                }
            }
        };
    }


    @Override
    public boolean remove(E obj) {
        // check for empty list
        if (first==null)
            return false; // empty list
        else if (obj.equals(first.value)) {
            // removing first node is a special case
            QueueNode qn = first;
            first = qn.next;
            // if this was the only element, update last - last will be null
            if (last==qn)
                last = first;
            qn.next = null;
            return true;
        }
        else {
            QueueNode cur = first.next;
            QueueNode prev = first;

            while (null!=cur) {
                if (obj.equals(cur.value)) {
                    prev.next = cur.next;
                    cur.next = null;
                    // if we removed the last node, update last pointer
                    if (last==cur)
                        last = prev;
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public int size() {
        return size;
    }

}
