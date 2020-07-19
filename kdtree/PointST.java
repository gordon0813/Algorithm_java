/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   b06505004
 *  Precept: P00
 *
 *  Description:  simple RedBlackBST base implement
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class PointST<Value> {
    // binary search tree that used to search node
    RedBlackBST<Point2D, Value> bst;

    // construct an empty symbol table of points
    public PointST() {
        bst = new RedBlackBST<Point2D, Value>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points
    public int size() {
        return bst.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        notNull(p);
        notNull(val);
        bst.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        notNull(p);
        return bst.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        notNull(p);
        return bst.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        notNull(rect);
        Queue<Point2D> re = new Queue<Point2D>();
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                re.enqueue(p);
            }
        }
        return re;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        notNull(p);
        Point2D re = bst.max();
        double mindis = re.distanceTo(p);

        for (Point2D pi : points()) {
            double tempdis;
            tempdis = pi.distanceTo(p);
            if (tempdis < mindis) {
                mindis = tempdis;
                re = pi;
            }

        }
        return re;
    }

    private void notNull(Object p) {
        if (p == null) throw new java.lang.NullPointerException();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Point2D p1 = new Point2D(1, 1);
        Point2D p2 = new Point2D(3, 3);
        Point2D p3 = new Point2D(2, 2);
        Point2D p4 = new Point2D(4, 4);
        Point2D p5 = new Point2D(0.5, 0.5);
        PointST<String> pst = new PointST<String>();
        pst.put(p1, "p1");
        pst.put(p2, "p2");
        pst.put(p3, "p3");
        pst.put(p4, "p4");
        pst.put(p5, "p5");
        StdOut.println(pst.get(p1));
        StdOut.println(pst.get(p3));

    }
}
