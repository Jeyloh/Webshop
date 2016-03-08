/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;



/**
 * An arrayList uses an array to store the list elements.
 *
 * @author even
 */
public class ArrayList<E> extends AbstractCollection<E> implements List<E> {

    public static long getCount = 0;
    /**
     * The initial size of the data array
     */
    private static final int INITIAL_CAPACITY = 16;

    /**
     * The array data are stored in
     */
    private E[] list;

    /**
     * The size of the list, which is also the number of cells in list
     * that are being used.
     */
    private int size;

    // Counters for performance analysis
    static int count;


    /**
     * The constructord creates the array
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        list = (E[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }


    public ArrayList(Collection<E> c) {
        for (E e : c)
            add(e);
    }


    /**
     * ...easy :-)
     */
    @Override
    public int size() {
        return size;
    }


    /**
     * We can do this because ListIterator is a sub-interface of
     * Iterator, so the ListIterator is an Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return listIterator();
    }


    /**
     * Adding elements to the end of list is also easy. Put the new
     * value in the first unused cell in list, and increment the size
     * counter
     */
    @Override
    public boolean add(E obj) {
        checkCapacity();
        list[size++] = obj;
        return true;
    }


    /**
     * This method is called by all methods that insert objects into
     * list, to make sure there is room for the new element.
     */
    private void checkCapacity() {
        //if the existing array is full
        if (size==list.length) {
//            list = Arrays.copyOf(list, 2*size);
            // create a new larger array, ..
            E[] newList = (E[]) (new Object[2*size]);
            // ... copy the data into it, ...
            for (int i = 0; i<size; i++) {
                newList[i] = list[i];
            }
            //...and replace list with it
            list = newList;
        }
    }


    /**
     * This is a more efficient implementation of clear() for
     * ArrayList
     */
    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        list = (E[]) (new Object[INITIAL_CAPACITY]);
        size = 0;
    }


    /**
     * Another easy method
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return list[index];
    }


    private void checkIndex(int i) {
        if (i<0||i>=size) {
            throw new IndexOutOfBoundsException(""+i+" > "+size+" !!!");
        }
    }


    @Override
    public E set(int index, E obj) {
        if (index==size) {
            add(obj);
            return null;
        }
        checkIndex(index);
        E oldElement = list[index];
        list[index] = obj;
        return oldElement;
    }


    /**
     * Insert obj at position index. That is between the existing
     * objects at index, and index-1
     *
     * @param index
     * @param obj
     */
    @Override
    public void add(int index, E obj) {
        if (index<0||index>size)
            throw new IndexOutOfBoundsException();
        checkCapacity();
        // we need to shift the contents of list "upwards" to provide an empty
        // cell at position index for the new object
        for (int i = size; i>index; i--) {
            getCount++;
            list[i] = list[i-1];
        }
        // System.arraycopy achieves the same thing as the loop above,
        // in a more efficient manner
//            System.arraycopy(list, index, list, index+1, size-index);
        list[index] = obj;
        size++;
    }


    /**
     * This method follows the pattern of contains() in
     * AbstractCollection
     */
    @Override
    public int indexOf(E obj) {
        ListIterator<E> it = listIterator();
        if (null==obj) {
            while (it.hasNext()) {
                if (null==it.next()) {
                    return it.previousIndex();
                }
            }
        }
        else {
            // but we can also use a for-loop insead of the iterator.
            for (int i = 0; i<size; i++) {
                if (obj.equals(list[i])) {
                    return i;
                }
            }
//            while (it.hasNext()) {
//                if (obj.equals(it.next()))
//                    return it.previousIndex();
//            }
        }
        return -1;
    }


    /**
     * Remove is the opposite of add(), it removes the object at a
     * specific position.
     *
     * @param index
     *
     * @return
     */
    @Override
    public E remove(int index) {
        E old = list[index];
        // We must shift the contents "down" to fill the hole left by
        // the removed value (actually we do not reomove it, the loop
        // below overwrites it). The call to arraycopy does the same
        //thing as the loop, but faster.
//        System.arraycopy(list, index+1, list, index, size-index-1);
        for (int i = index; i<size-1; i++) {
            list[i] = list[i+1];
        }
        // we insert null in the original position of the last
        // element in the list - to prevent "old" elements from
        // hanging around forever.
        list[size--] = null;
        return old;
    }


    @Override
    public ListIterator<E> listIterator() {
        return new Iter();
    }



    /**
     * Iter is an "inner" class. It is defined inside ArrayList. In
     * fact, Iter objects can only exist inside ArrayList objects. The
     * iterators (Iter objects) have full acces to the private fields
     * inside ArrayList. This is what we want, because it makes it
     * easy to implement the iterator. Note that Iter is private.
     * Other classes will not even know that it exists. Yet they can
     * use the Iter objects, returned by the iterator() method.
     */
    private class Iter implements ListIterator<E> {

        /**
         * Position of the cursor. The cursor is actually between pos
         * pos-1, so next() will return the object with index pos,
         * while previous will return object pos-1
         */
        int pos;
        /* index of the last object returned  next()/previous() */
        int lastPos;


        /**
         * pos will never move outside the range 0 to size So if pos
         * == size, we are at the end, and there is no next
         */
        @Override
        public boolean hasNext() {
            return pos!=size;
        }


        /**
         * return the next object, and increment pos
         */
        @Override
        public E next() {
            if (pos>=size) {
                throw new NoSuchElementException();
            }
            lastPos = pos;
            pos++;
            return list[lastPos];
        }


        @Override
        public boolean hasPrevious() {
            return pos!=0;
        }


        @Override
        public E previous() {
            int i = pos-1;
            if (i<0) {
                throw new NoSuchElementException();
            }
            lastPos = pos = i;
            return list[lastPos];
        }


        @Override
        public int nextIndex() {
            return pos;
        }


        @Override
        public int previousIndex() {
            return pos-1;
        }


        /**
         * The iterator keeps track of the position, so it just asks
         * the list to remove the object at the last returned position
         */
        @Override
        public void remove() {
            if (lastPos<0||lastPos>=size) {
                throw new IllegalStateException();
            }
            ArrayList.this.remove(lastPos);
        }


        @Override
        public void set(E e) {
            if (lastPos<0||lastPos>=size) {
                throw new IllegalStateException();
            }
            ArrayList.this.set(lastPos, e);
        }


        @Override
        public void add(E e) {
            if (lastPos<0||lastPos>size) {
                throw new IllegalStateException();
            }
            ArrayList.this.add(lastPos, e);
            pos++;
        }
    }
}
