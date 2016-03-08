/**
 * This is a fairly simple implementation of a hash table. However, you
 * can choose the strategy for handling collisions when the map is created.
 *
 * @author even
 */
public class HashMap<K extends Comparable<K>,V> implements Map<K,V> {
    private int size;
    private Object[] table;
    private Strategy strategy;


    /**
     * Create a HashMap with the specified capacity.
     * Specifying the size of the hash table in this way allow us
     * to control the load factor accurately, which is useful for
     * experimenting and testing.
     * java.util.HashMap will automatically resize the hash table
     * to keep the load factor below a chosen limit, which is
     * (much) more appropriate for a production setting.
     * When open addressing is used the capacity should be a prime
     * number to ensure that the entire table will be tried when
     * searching for a vacant spot.
     * @param capacity
     */
    public HashMap(int capacity, Strategy strategy) {
        size = 0;
        table = new Object[capacity];
        this.strategy = strategy;
    }

    public HashMap(int capacity) {
        this(capacity, Strategy.CHAINING);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V get(K key) {
        Node n = findNode(key);
        if (null == n) return null;
        else return n.getValue();
    }

    public V put(K key, V value) {
        if (value == null) remove(key);
        Node n = findNode(key);
        if (null != n) return n.setValue(value);
        n = new Node(key, value);
        insert(n);
        return null;
    }

    /** Removing is a bit fiddly, so I have written separate methods
     * for the two strategies. */
    public V remove(K key) {
        if (Strategy.CHAINING == strategy) return removeChaining(key);
        else return removeOpenAdressing(key);
    }

    /**
     * Removing an entry from a chaining hashmap is two-step:
     * 1 find the right list, using the primary hash function
     * 2 find and remove the right entry from the list
     */
    private V removeChaining(K key) {
        int pos = hash1(key);
        Node n = (Node)table[pos];
        if (null == n) return null;
        if (n.key.equals(key)) { // first element is a special case
            Node node = (Node)table[pos];
            V retval = node.value;
            table[pos] = node.next;
            return retval;
        }
        for (n=(Node)table[pos]; n.next != null; n = n.next) {
            // in the loop we check the next node, it is easier to remove
            if (n.next.key.equals(key)) {
                V retval = n.next.value;
                n.next = n.next.next;
                return retval;
            }
        }
        return null;
    }

    private V removeOpenAdressing(K key) {
        int pos = hash1(key);
        int step = hash2(key);
        for (int i=0; i<size; i++) {
            // this loop will step through the entire table, jumping step
            // cells, wrappin around to the beginning when necessary.
            // the loop will end earlier if we find the key to remove
            // or a null entry
            int rpos = (pos + i*step) % table.length;
            Node n = (Node)table[rpos];
            if (null == n) return null; // end of chain, not there
            else if (n.isDeleted()){} // deleted entry - skip
            else if (n.key.equals(key)) { // found it
                V retval = n.value;
                n.setDeleted();
                return retval;
            }
            else {} // wrong key - skip
        }
        // we will only ever get here if the table is completely full
        // and key is not in it
        return null;
    }

    private Node findNode(K key) {
        // use the key's hashcode to compute its position in the table
        int pos = hash1(key);

        if (Strategy.CHAINING == strategy) {
            for (Node n = (Node)table[pos]; n != null; n = n.next) {
                if (n.key.equals(key)) return n;
            }
            return null;
        }
        else {
            int step = hash2(key);
            for (int i=0; i<table.length; i++) {
                int epos = (pos + i*step) % table.length;
                Node n = (Node)table[epos];
                if (n != null && n.key.equals(key))
                    return n;
            }
            return null;
        }
    }


    private void insert(Node n) {
        int pos = hash1(n.key);
        if (Strategy.CHAINING == strategy) {
            n.next = (Node)table[pos];
            table[pos] = n;
            size++;
        }
        else {
            int step = hash2(n.key);
            for (int i=0; i<table.length; i++) {
                int epos = (pos + i*step) % table.length;
                Node node = (Node)table[epos];
                if (node == null || node.isDeleted()) {
                    table[epos] = n;
                    size++;
                    return;
                }
            }
            throw new IllegalStateException("Hash table is full");
        }
    }



    /** The primary hash function computes the position in the table
     * where a specific key should be stored */
    private int hash1(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    /** The secondary hash function computes the step to move while
     * looking for a vacant cell in the hashtable. It is only used
     * in open addressing */
    private int hash2(K key) {
        return (Math.abs(key.hashCode()) % (table.length - 1)) + 1;
    }

    /**
     * This is a single linked list node, with key and value field. It is
     * needed for the chaining strategy.
     */
    private class Node implements Map.Entry<K,V> {
        K key;
        V value;
        Node next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V newVal) {
            V old = value;
            value = newVal;
            return old;
        }

        public void setDeleted() {
            key = null;
            value = null;
        }

        public boolean isDeleted() {
            return key == null;
        }
    }

    public enum Strategy { CHAINING, OPEN_ADDRESSING }
}
