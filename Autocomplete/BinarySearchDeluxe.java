/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Partner Name:    Chuang bo-han
 *  Partner NetID:   B06505004
 *  Partner Precept: P00
 *
 *  Description:  BinarySearchDeluxe main
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {
    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        int idf = 0;
        int ide = a.length - 1;
        int mid;
        int cmp;
        int lastid = -1;
        while (idf <= ide) {
            mid = idf + (ide - idf) / 2;
            cmp = comparator.compare(key, a[mid]);
            if (cmp < 0) ide = mid - 1;
            else if (cmp > 0) idf = mid + 1;
            else {
                ide = mid - 1;
                lastid = mid;
            }
        }
        return lastid;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        int idf = 0;
        int ide = a.length - 1;
        int mid;
        int cmp;
        int lastid = -1;
        while (idf <= ide) {
            mid = idf + (ide - idf) / 2;
            cmp = comparator.compare(key, a[mid]);

            if (cmp < 0) ide = mid - 1;
            else if (cmp > 0) idf = mid + 1;
            else {
                idf = mid + 1;
                lastid = mid;
            }
        }
        return lastid;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term[] arr = new Term[6];
        arr[0] = new Term("a111", 5);
        arr[1] = new Term("a211", 5);
        arr[2] = new Term("a211", 4);
        arr[3] = new Term("a311", 4);
        arr[4] = new Term("a311", 4);
        arr[5] = new Term("b411", 2);
        StdOut.println(firstIndexOf(arr, new Term("a211", 2),
                                    Term.byPrefixOrder(3)) + " = 1");
        StdOut.println(lastIndexOf(arr, new Term("a211", 2),
                                   Term.byPrefixOrder(3)) + " = 2");
        StdOut.println(firstIndexOf(arr, new Term("a211", 2),
                                    Term.byPrefixOrder(1)) + " = 0");
        StdOut.println(lastIndexOf(arr, new Term("a211", 2),
                                   Term.byPrefixOrder(1)) + " = 4");
        StdOut.println(firstIndexOf(arr, new Term("a211", 4),
                                    Term.byReverseWeightOrder()) + " = 2");


    }
}
