/* *****************************************************************************
 *  Name:    Chuang Bo Han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  HW2_randQ
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // array that save item
    private Item[] arr;
    // item number
    private int size;

    // init array with length 1 and no content
    public RandomizedQueue() {
        size = 0;
        arr = (Item[]) new Object[1];
    }

    // double the array size
    private void resizeup() {
        Item[] newarr = (Item[]) new Object[2 * arr.length];
        for (int i = 0; i < size; i++) {
            newarr[i] = arr[i];
        }
        arr = newarr;
    }

    // dicrease array size to  1/4
    private void resizedown() {
        Item[] newarr = (Item[]) new Object[arr.length / 2];
        for (int i = 0; i < size; i++) {
            newarr[i] = arr[i];
        }
        arr = newarr;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        isvalid(item);
        if (size == arr.length) resizeup();
        arr[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        isvalid();
        Item tmp;
        int removeid = StdRandom.uniform(size);
        if (size == arr.length / 4) resizedown();
        tmp = arr[removeid];
        arr[removeid] = arr[--size];
        arr[size] = null;
        return tmp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        isvalid();
        return arr[StdRandom.uniform(size)];
    }

    // is valid operate
    private void isvalid() {
        if (size == 0) throw new NoSuchElementException();
    }

    // is valid input
    private void isvalid(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private class RandIterator implements Iterator<Item> {
        // reference to the array
        private Item[] items;
        // access sequence
        private int[] randid;
        // where we access now
        private int nowid;

        // init iterater
        public RandIterator(Item[] arr) {
            items = arr;
            randid = new int[size];
            for (int i = 0; i < size; i++) {
                randid[i] = i;
            }
            StdRandom.shuffle(randid);
            nowid = 0;
        }

        // next item
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[randid[nowid++]];
        }

        // has next item
        public boolean hasNext() {
            return nowid < randid.length;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandIterator(this.arr);
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        for (int i = 0; i < 10; ++i)
            rq.enqueue(i);
        Iterator<Integer> it = rq.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + rq.size());
        it = rq.iterator();
        while (it.hasNext()) StdOut.print(it.next() + " ");
        StdOut.println(" size: " + rq.size());
        StdOut.println(" is empty: " + rq.isEmpty());
        StdOut.println(" sample " + rq.sample());
        for (int j = 0; j < 3; ++j) {
            for (int i = 0; i < 3; ++i)
                rq.dequeue();
            it = rq.iterator();
            while (it.hasNext()) StdOut.print(it.next() + " ");
            StdOut.println(" size: " + rq.size());
        }
    }
}
