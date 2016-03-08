/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * A map is a "database" of key,value pairs. It can be thought of
 * as an array where the indices (the keys) are objects, rather than numbers.
 * Maps are also known as "association lists".
 *
 * Each key is associated with a value, and the key is used to store and
 * retrieve the value, in similar fashion as the index is used
 * in an array.
 *
 * @author even
 */
public interface Map<K extends Comparable<K>,V> {
    interface Entry<K,V> {
        K getKey();
        V getValue();
    }

    /** Return the value associated with key, or null if key is not
     * in the map */
    V get(K key);

    /**
     * Adds the pair key,value to the map, or (if key is already in the map)
     * updates the value associated with key. Returns the old value associated
     * with key, or null if the key was not mapped before.
     */
    V put(K key, V value);

    /**
     * Remove a key,value pair from the map. Returns the value
     * associated with key, or null if key was not mapped
     */
    V remove(K key);

    /** Return true if the map is empty, false if not */
    boolean isEmpty();

    /** Return the number of key,value pairs in the map */
    int size();
}
