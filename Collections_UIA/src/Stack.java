/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author even
 */
public interface Stack<E> extends Collection<E> {
    /** return true if the stack is empty */
    boolean isEmpty();

    /** return the object at the top of the stack - without removing it */
    E peek();

    /** remove an return the object at the top of the stack */
    E pop();

    /** add item at the top of the stack */
    E push(E item);

}
