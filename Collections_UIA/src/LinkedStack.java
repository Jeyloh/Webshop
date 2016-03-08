/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *
 * @author even
 */
public class LinkedStack<E> implements Stack<E> {

    private StackNode stack;
    private int size;

    public LinkedStack() {
        size = 0;
    }

    /**
     * Stack methods - I don't have the time to repeat the javadoc comments. See
     * Stack or ArrayStack
     */
    @Override
    public boolean isEmpty() {
        return size == 0; // or stack == null, both conditions should give the same result
    }

    @Override
    public E peek() {
        if (stack == null)
            throw new NoSuchElementException();
        return stack.value;
    }

    @Override
    public E pop() {
        if (stack == null)
            throw new NoSuchElementException();
        StackNode popped = stack;
        stack = stack.next;
        popped.next = null;
        size--;
        return popped.value;
    }

    @Override
    public E push(E item) {
        stack = new StackNode(item, stack);
        size++;
        return item;
    }


    private class StackNode {

        E value;

        StackNode next;

        /**
         * This convenience constructor is the only one we need
         */
        StackNode(E value, StackNode next) {
            this.value = value;
            this.next = next;
        }
    }

    @Override
    public boolean add(E obj) {
        push(obj);
        return true;
    }

    @Override
    public void clear() {
        stack = null;
        size = 0;
    }

    @Override
    public boolean contains(E obj) {
        if (null == obj) {
            for (StackNode node = stack; node != null; node = node.next) {
                if (null == node.value)
                    return true;
            }
        }
        else {
            for (StackNode node = stack; node != null; node = node.next) {
                if (obj.equals(node.value))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }


    private class Iter implements Iterator<E> {

        StackNode cur;

        public Iter() {
            cur = stack;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public E next() {
            if (null == cur)
                throw new NoSuchElementException();
            E item = cur.value;
            cur = cur.next;
            return item;
        }

        /**
         * This method is still a bit dirty, and we would have to use a double
         * linked list, or a second pointer to implement it
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * It is still dubious practice to remove objects inside the stack, but here
     * it is, so you can see the hassle of removing objects from a single linked
     * list
     */
    @Override
    public boolean remove(E obj) {

        StackNode cur = stack;
        StackNode prev = null;
        boolean found = false;

        // need to check for the value to remove
        // AND that we havent fallen off the end of the list
        while (cur != null && !found) {
            if ((null == obj && null == cur.value) || // looking for and found null OR
                    (null != obj && obj.equals(cur.value))) // looking for an object and found it
                found = true;
            else {
                prev = cur;
                cur = cur.next;
            }
        }

        // if we fell of the end, it's not there
        if (null == cur)
            return false;

        // check if cur is the first node in the list
        // or stack == cur
        if (null == prev) stack = cur.next;
        else prev.next = cur.next;
        cur.next = null;
        return true;
    }

    @Override
        public int size() {
        return size;
    }

}
