/* *****************************************************************************
 *  Name:    Chuang Bo Han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  HW2
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        // next node
        public Node next;
        // previous node
        public Node prev;
        // content
        public Item it;

        // init the node ==> root
        public Node() {
            next = this;
            prev = this;
        }

        // create node with content
        public Node(Item in) {
            it = in;
        }

        // insert a node after this node
        public void insertafter(Node addnode) {
            addnode.prev = this;
            addnode.next = this.next;
            this.next.prev = addnode;
            this.next = addnode;
        }

        // insert a node before this node
        public void insertbefore(Node addnode) {
            addnode.prev = this.prev;
            addnode.next = this;
            this.prev.next = addnode;
            this.prev = addnode;
        }

        // remove the node after this node
        public Item removeafter() {
            Node toremove = this.next;
            toremove.next.prev = this;
            this.next = toremove.next;
            return toremove.it;
        }

        // remove the node before this node
        public Item removebefore() {
            Node toremove = this.prev;
            toremove.prev.next = this;
            this.prev = toremove.prev;
            return toremove.it;
        }

    }

    // iterater for Deque
    private class Diterator implements Iterator<Item> {
        // where is the iterater now
        private Node node;

        // init iterater with root node
        Diterator(Node n) {
            this.node = n;
        }

        // iterater has next node to visit
        public boolean hasNext() {
            return node.next != root;
        }

        // iterater visit next node and return the content
        public Item next() {
            if (node.next == root) throw new NoSuchElementException();
            node = node.next;
            return node.it;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // root of Dqeue
    private Node root;
    // size of Dqeue
    private int s;

    // init Dqeue with size 0
    public Deque() {
        s = 0;
        root = new Node();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return root.next == root;
    }

    // return the number of items on the deque
    public int size() {
        return s;
    }

    // add the item to the front
    public void addFirst(Item item) {
        isvalid(item);
        s += 1;
        root.insertafter(new Node(item));
    }

    // add the item to the back
    public void addLast(Item item) {
        isvalid(item);
        s += 1;
        root.insertbefore(new Node(item));
    }

    // remove and return the item from the front
    public Item removeFirst() {
        isvalid();
        s -= 1;
        return root.removeafter();
    }

    // remove and return the item from the back
    public Item removeLast() {
        isvalid();
        s -= 1;
        return root.removebefore();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Diterator(root);
    }

    // is valid operate
    private void isvalid() {
        if (isEmpty()) throw new NoSuchElementException();
    }

    // is valid input
    private void isvalid(Item t) {
        if (t == null) throw new IllegalArgumentException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        for (int i = 0; i < 10; i += 2) {
            deque.addFirst(i);
            deque.addLast(i + 1);
        }
        Iterator<Integer> it = deque.iterator();
        while (it.hasNext()) {
            StdOut.print(it.next() + " ");
        }
        StdOut.println("size: " + deque.size());
        StdOut.println(" is empty: " + deque.isEmpty());
        for (int i = 0; i < 3; ++i) {
            deque.removeLast();
            deque.removeFirst();
        }
        it = deque.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println("size: " + deque.size());

        for (int i = 0; i < 6; i += 2) {
            deque.addLast(i);
            deque.addFirst(i + 1);
        }
        it = deque.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + deque.size());
    }
}
