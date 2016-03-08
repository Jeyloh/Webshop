            /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


            /**
 * This is an implementation of the map interface, using a binary
 * search tree. To keep things simpler this class uses a straight-
 * forward binary tree, in contrast to the TreeMap class in the
 * standard library which uses a red-black tree - a variant of the
 * binary search tree that is always balanced.
 *
 * This class contains several implementations of the bst operations,
 * to show how it can be done. Any "production" implementation would
 * choose a single style of implementation.
 *
 * @author even
 */
public class TreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * Immplementation style
     */
    public static enum Implementation {

        ITERATIVE, // use iterative implementation
        RECURSIVE, // use recursive implementation
        IN_NODE // use in-node recursive implementation
    }



    private class Node implements Map.Entry<K, V> {

        // the parent pointer is the tree equivalent to a
        // double linked list - it enables two-way navigation
        Node parent;
        Node left;
        Node right;
        K key;
        V value;


        public Node(K key, V value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }


        public K getKey() {
            return key;
        }


        public V getValue() {
            return value;
        }


        public V setValue(V v) {
            V old = value;
            value = v;
            return old;
        }

        /* Here are the in-node implementation of map operations  */

        public V get(K key) {
            int comp = key.compareTo(this.key);
            if (comp<0) { // key is in left subtree
                if (null==left)
                    return null;
                else
                    return left.get(key);
            }
            else if (comp>0) { // key is in right subtree
                if (null==right)
                    return null;
                else
                    return right.get(key);
            }
            else
                return this.value;
        }


        public V put(K k, V v) {
            int comp = k.compareTo(this.key);
            if (comp==0)
                return setValue(v);
            else if (comp<0) {
                if (null!=left)
                    return left.put(k, v);
                else
                    left = new Node(k, v, this);
            }
            else if (comp>0) {
                if (null!=right)
                    return right.put(k, v);
                else
                    right = new Node(k, v, this);
            }
            // we end up here if we inserted new node as a
            // direct child of this node
            // if we have reused this node or inserted a
            // new node in a recursive call we will return
            // before getting here
            size++;
            return null;
        }


        public V remove(K k) {
            int cmp = k.compareTo(key);
            // delegate removal to one of the subtrees
            if (cmp<0)
                return left.remove(k);
            else if (cmp>0)
                return right.remove(k);
            else {
                // removing this
                if (left!=null&&right!=null) {
                    // two children - find the next node in sorted
                    // order, and use it as a replacement for this
                    Node r = right;
                    while (r.left!=null)
                        r = r.left;
                    // cut loose the replacement node r
                    r.parent.left = r.right;
                    if (r.right!=null)
                        r.right.parent = r.parent;
                    r.parent = r.right = null;
                    V old = value;
                    // copy the content from the replacement node to this
                    // we remove the replacement node r from the tree
                    // and keep this (with r's key and value)
                    key = r.key;
                    value = r.value;
                    return old;
                }

                // this has only one child - we can replace this with the only child
                Node r = null;
                if (left==null)
                    r = right;
                else if (right==null)
                    r = left;

                if (parent==null)
                    root = r;
                else if (parent.left==this)
                    parent.left = r;
                else
                    parent.right = r;
                return value;
            }
        }


        public void print() {
            if (left!=null)
                left.print();
            System.out.print(" ("+key+","+value+")");
            if (right!=null)
                right.print();
        }
    }

    /**
     * TreeMap fields
     */
    private int size;
    private Node root;
    private Implementation impl;


    /**
     * Create a TreeMap, which will use the chosen set of method
     * implementations
     */
    public TreeMap(Implementation impl) {
        size = 0;
        root = null;
        this.impl = impl;
    }

    // these two methods do not access the nodes at all, and they work
    // in constant time - O(1), so they are neither recursive nor iterative

    @Override
    public boolean isEmpty() {
        return size==0;
    }


    @Override
    public int size() {
        return size;
    }


    // Top level drivers - these are the public methods from
    // the map interface. They just call the corresponding
    // method from one of the implementation styles
    @Override
    public V get(K key) {
        switch (impl) {
        case RECURSIVE:
            return recGet(key);
        case ITERATIVE:
            return iterGet(key);
        default:
            return nodeGet(key);
        }
    }


    @Override
    public V put(K key, V value) {
        switch (impl) {
        case RECURSIVE:
            return recPut(key, value);
        case ITERATIVE:
            return iterPut(key, value);
        default:
            return nodePut(key, value);
        }
    }


    @Override
    public V remove(K key) {
        switch (impl) {
        case RECURSIVE:
        case ITERATIVE:
                // no separate recursive version as it
            // will be almost identical to the iterative version
            return iterRemove(key);
        default:
            return nodeRemove(key);
        }
    }

    // IN_NODE
    // These methods call recursive versions of get(), put() etc,
    // which are defined inside the node class

    private V nodeGet(K key) {
        if (null==root)
            return null;
        else
            return root.get(key);
    }


    private V nodePut(K key, V value) {
        if (null==root) {
            root = new Node(key, value, null);
            size = 1;
            return null;
        }
        else
            return root.put(key, value);
    }


    private V nodeRemove(K key) {
        if (null==root)
            return null;
        else
            return root.remove(key);
    }

    // RECURSIVE
    // These are plain recursive versions of get(), put() etc

    private V recGet(K key) {
        Node n = recGetNode(key);
        if (null==n)
            return null;
        else
            return n.value;
    }


    private Node recGetNode(K key) {
        return recGetNode(key, root);
    }


    /**
     * Using key to find a node recursively
     */
    private Node recGetNode(K key, Node n) {
        if (null==n)
            return null;
        else {
            int comp = key.compareTo(n.key);
            if (comp<0)
                return recGetNode(key, n.left);
            else if (comp>0)
                return recGetNode(key, n.right);
            else
                return n;
        }
    }


    private V recPut(K k, V v) {
        Node n = recGetNode(k, root);
        if (null==n) {
            // key not in map - insert new node
            n = new Node(k, v, null);
            insert(n);
            return null;
        }
        else {
            // existing key
            V old = n.value;
            n.value = v;
            return old;
        }
    }


    private void insert(Node n) {
        // handle empty tree, or call insert() to add a new node
        if (null==root)
            root = n;
        else
            insert(n, root);
        size++;
    }


    private void insert(Node n, Node tree) {
        // insert a new node
        int comp = n.key.compareTo(tree.key);
        if (comp<0) {
            if (null==tree.left)
                tree.left = n;
            else
                insert(n, tree.left);
        }
        else if (comp>0) {
            if (null==tree.right)
                tree.right = n;
            else
                insert(n, tree.right);
        }
        else {
            /**
             * impossible, existing keys were sortet out in put()
             */
            throw new IllegalStateException();
        }
    }


    // ITERATIVE
    // Here are the iterative versions of the methods
    private V iterGet(K key) {
        Node n = iterGetNode(key);
        if (null==n)
            return null;
        else
            return n.value;
    }


    /**
     * Using key to find a node iteratively
     */
    private Node iterGetNode(K key) {
        Node n = root;
        while (n!=null) {
            int comp = key.compareTo(n.key);
            if (comp<0)
                n = n.left;
            else if (comp>0)
                n = n.right;
            else
                return n;
        }
        return null;
    }


    private V iterPut(K key, V value) {
        if (null==root) {
            // empty tree - just insert new node as root
            root = new Node(key, value, null);
            size = 1;
            return null;
        }
        else {
            Node n = root;
            Node parent;
            int comp;
            do { // this loop finds the node that will be the parent of the new node
                parent = n;
                comp = key.compareTo(n.key);
                if (comp<0)
                    n = n.left;
                else if (comp>0)
                    n = n.right;
                else
                    return parent.setValue(value);
            }
            while (n!=null);
            // create the new node and make it the correct child of parent
            n = new Node(key, value, parent);
            if (comp<0)
                parent.left = n;
            else
                parent.right = n;
            size++;
            return null;
        }

    }


    private V iterRemove(K k) {
        Node n = iterGetNode(k);
        if (null==n)
            return null;
        else {
            V old = n.value;
            if (n.left!=null&&n.right!=null) {
                // two children - use next key as replacement
                Node r = n.right;
                while (r.left!=null)
                    r = r.left;
                r.parent.left = r.right;
                if (r.right!=null)
                    r.right.parent = r.parent;
                r.parent = r.right = null;

                n.key = r.key;
                n.value = r.value;
            }
            else {
                // max one child - replace n with subtree
                Node r = n.left!=null ? n.left : n.right;
                r.parent = n.parent;
                if (n==n.parent.left)
                    n.parent.left = r;
                else
                    n.parent.right = r;
            }
            return old;
        }
    }


    /**
     * A simple example of tree traversal. Prints the contents of the
     * map on a single line
     */
    public void print() {
        System.out.print("Tree: ");
        root.print();
        System.out.println();
    }


    /**
     * Prints the tree sideways, with increasing indentation for each
     * level og the tree
     */
    public void fancyPrint() {
        System.out.println("\nTree ("+impl+") =");
        fancyPrint(root, "  ");
    }


    /**
     * Another traversal...
     */
    private void fancyPrint(Node n, String prefix) {
        if (null==n)
            System.out.println("(empty)");
        String nextPrefix = prefix+"  ";
        if (null!=n.right)
            fancyPrint(n.right, nextPrefix);
        System.out.println(prefix+"("+n.key+", "+n.value+")");
        if (null!=n.left)
            fancyPrint(n.left, nextPrefix);
    }


    /**
     * An abstraction of traversal. The inspector :-) knows what type
     * of traversal is wanted, and what to do with each node. The
     * traverse() method just makes sure that the inspect() method is
     * called at the right time .
     */
    public void traverse(Inspector insp) {
        traverse(root, insp);
    }


    private void traverse(Node n, Inspector insp) {
        if (null==n)
            return;
        if (insp.order==Traversal.PREORDER)
            insp.inspect(n);
        traverse(n.left, insp);
        if (insp.order==Traversal.INORDER)
            insp.inspect(n);
        traverse(n.right, insp);
        if (insp.order==Traversal.POSTORDER)
            insp.inspect(n);
    }



    public enum Traversal {

        PREORDER, INORDER, POSTORDER;
    }



    public abstract class Inspector {

        public Traversal order;


        public abstract void inspect(Node n);
    }
}
