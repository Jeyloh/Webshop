/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * This implementation is slightly different from the java standard
 * library.
 * java.util.Stack is a subclass of java.util.Vector (Vector and
 * ArrayList
 * are both array-based list implementation. There are two of them for
 * backward
 * compatibility with earlier versions of java.
 *
 * I have implemented Stack as two separate classes which implement a
 * common interface Stack instead, to show how a stack can be
 * implemented
 * in different ways.
 *
 * This is the array-based implementation of Stack.
 *
 * @author even
 */
public class ArrayStack<E> implements Stack<E>, Collection<E>
{

    /**
     * the default size of a new stack
     */
    private static final int INITIAL_CAPACITY = 16;

    /**
     * is the stack allowed to grow
     */
    private boolean canGrow;

    /**
     * the stack data
     */
    private E[] stack;

    /**
     * The number of objects on the stack. The top of the stack is at
     * size-1
     */
    private int size;


    /**
     * the default constructor creates a stack that will grow when
     * needed
     */
    public ArrayStack() {
        this(INITIAL_CAPACITY, true);
    }


    /**
     * This constructor can be used to create a fixed size stack
     */
    public ArrayStack(int capacity, boolean canGrow) {
        stack = (E[]) (new Object[capacity]);
        this.canGrow = canGrow;
        size = 0;
    }


    /*
     * These methods are needed to make the stack a Collection
     */
    @Override

    /**
     * Add is the same as push
     */
    public boolean add(E obj) {
        push(obj);
        return true;
    }


    /**
     * Empty the stack
     */
    @Override
    public void clear() {
        if (canGrow) stack = (E[]) (new Object[INITIAL_CAPACITY]);
        else stack = (E[]) (new Object[stack.length]);
        size = 0;
    }


    /**
     * Returns true if obj is on the stack
     */
    @Override
    public boolean contains(E obj) {
        if (null == obj) {
            for (E item : stack) {
                if (null == item) return true;
            }
        }
        else {
            for (E item : stack) {
                if (obj.equals(item)) return true;
            }
        }
        return false;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }


    @Override
    public boolean remove(E obj) {
        throw new UnsupportedOperationException();
    }



    private class Iter implements Iterator<E>
    {

        /**
         * Position of the cursor.
         */
        int pos;
        /*
         * index of the last object returned next()/previous()
         */
        int lastPos;


        /**
         * pos will never move outside the range 0 to size
         * So if pos == size, we are at the end, and there is no next
         */
        @Override
        public boolean hasNext() {
            return pos < size;
        }


        /**
         * return the next object, and increment pos
         */
        @Override
        public E next() {
            if (pos >= size) {
                throw new NoSuchElementException();
            }
            lastPos = pos;
            pos++;
            return stack[lastPos];
        }


        /**
         * This operation is a bit dubious. It makes it possible to
         * remove objects from the middle of the stack - through a
         * "back door" so I have chosen to block it
         */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();

        }
    }


    /*
     * The stack methods with their traditional names below
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * Return the object at the top of the stack without removeing it
     */
    @Override
    public E peek() {
        if (isEmpty()) throw new EmptyStackException();
        else return stack[size - 1];
    }


    /**
     * Remove and return the object at the top of the stack
     */
    @Override
    public E pop() {
        if (isEmpty()) throw new EmptyStackException();
        size--;
        E item = stack[size];
        stack[size] = null;
        return item;
    }


    /**
     * Add an object to the stack
     */
    @Override
    public E push(E item) {
        if (size == stack.length) {
            // array is full
            if (canGrow) {
                stack = Arrays.copyOf(stack, 2 * size);
            }
        }
        stack[size] = item;
        size++;
        return item;
    }
}
