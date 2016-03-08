/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.NoSuchElementException;


/**
 *
 * @author even
 */
public interface Queue<E> extends Collection<E> {
    /**
     * Insert e at the end of the queue.
     * @return true if successful
     * @throws IllegalStateException if queue is full */
    boolean add(E e) throws IllegalStateException;

    /**
     * Removes the first element from the queue.
     * @return the removed element
     * @throws NoSuchElementException if the queue is empty
     */
    E remove();

    /**
     * Returns the first element from the queue, without removing it
     * @throws NoSuchElementException if the queue is empty
     */
    E element();

    /**
     * Insert e at the end of the queue
     * @param e element to insert
     * @return true if e was added, false if not
     *
     */
    boolean offer(E e);

    /**
     * Retrieves and removes the first element from the queue.
     * @return the removed element in queue, or null if the queue is empty
     */
    E poll();

    /** Retrieves the first element from the queue, without removing it.
     * @return the first element, or null if the queue is empty
     */
    E peek();

}
