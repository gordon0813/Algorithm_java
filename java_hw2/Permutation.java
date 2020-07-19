/* *****************************************************************************
 *  Name:    Chuang Bo Han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  HW2 read String print out randomly
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// test : read all content randon print k of them
public class Permutation {
    public static void main(String[] args) {

        final int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String string = StdIn.readString();
            q.enqueue(string);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
