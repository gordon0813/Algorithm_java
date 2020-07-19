/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   b06505004
 *  Precept: P00
 *
 *  Description:  kd tree implement
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Iterator;

public class KdTreeST<Value> {
    // size of tree
    private int size;
    // root node
    private Node root;

    // construct an empty symbol table of points
    public KdTreeST() {
        root = null;
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        notNull(p);
        notNull(val);
        if (isEmpty()) {
            root = new Node(p, val, true);
        }
        else {
            root.insertBelow(p, val);
        }
        size++;
    }

    // value associated with point p
    public Value get(Point2D p) {
        notNull(p);
        return root.findBelow(p).value();
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        notNull(p);
        return get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return new NodeLevelOrderIterator(root);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        notNull(rect);
        Queue<Point2D> re = new Queue<Point2D>();
        root.findInRectBelow(rect, re);
        return re;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        notNull(p);
        return root.findNearBelow(p, p.distanceTo(root.point) + 1);
    }

    private class Node {
        // Node's position
        private Point2D point;
        // right child  (bigger one
        private Node rc;
        // left child (smaller or equal one
        private Node lc;
        // value
        private Value val;
        // horizon false   vertical true
        private boolean type;

        // init a node with point value  type( vertical or horizontal)
        public Node(Point2D p, Value v, boolean t) {
            this.point = p;
            this.val = v;
            this.type = t;
        }

        // compareTo a point
        public int compareTo(Point2D p) {
            if (type) {
                if (point.x() > p.x()) return 1;
                else if (point.x() < p.x()) return -1;
                else return 0;
            }
            else {
                if (point.y() > p.y()) return 1;
                else if (point.y() < p.y()) return -1;
                else return 0;
            }
        }

        // recursive find where to put a new point with value v
        public void insertBelow(Point2D p, Value v) {
            if (compareTo(p) < 0) {
                if (rc != null) {
                    rc.insertBelow(p, v);
                }
                else {
                    this.rc = new Node(p, v, !type);
                }
            }
            else {
                if (lc != null) {
                    lc.insertBelow(p, v);
                }
                else {
                    this.lc = new Node(p, v, !type);
                }
            }

        }

        // recursive find point insub tree
        public Node findBelow(Point2D p) {
            int cmp = compareTo(p);
            if (cmp < 0) {
                if (rc != null) {
                    return rc.findBelow(p);
                }
                else {
                    return null;
                }
            }
            else if (cmp > 0) {
                if (lc != null) {
                    return lc.findBelow(p);
                }
                else {
                    return null;
                }
            }
            else {
                return this;
            }

        }

        // recursive find all point in sub tree that inside rect ,save into ans
        public void findInRectBelow(RectHV rect, Queue<Point2D> ans) {
            if (type) { // vertical
                if (rect.xmin() <= this.point.x() && lc != null) {
                    lc.findInRectBelow(rect, ans);
                }
                if (rect.xmax() > this.point.x() && rc != null) {
                    rc.findInRectBelow(rect, ans);
                }
            }
            else {    // horizon
                if (rect.ymin() <= this.point.y() && lc != null) {
                    lc.findInRectBelow(rect, ans);
                }
                if (rect.ymax() > this.point.y() && rc != null) {
                    rc.findInRectBelow(rect, ans);
                }
            }
            if (rect.contains(this.point)) ans.enqueue(this.point);
        }

        // recursive travesal sub tree return nearest point ,
        // return null when not found point that is near than nowmindis
        // prune when other side will never exist a point with distance smaller than mindis
        public Point2D findNearBelow(Point2D target, double nowmindis) {
            double thisdis = this.point.distanceTo(target);
            Point2D nearestPoint = null;
            Point2D tmp;
            if (thisdis < nowmindis) {
                nowmindis = thisdis;
                nearestPoint = this.point;
            }
            if (type) {
                if (target.x() - this.point.x() < nowmindis && lc != null) {
                    tmp = lc.findNearBelow(target, nowmindis);
                    if (tmp != null) {
                        nearestPoint = tmp;
                        nowmindis = nearestPoint.distanceTo(target);
                    }
                }
                if (this.point.x() - target.x() < nowmindis && rc != null) {
                    tmp = rc.findNearBelow(target, nowmindis);
                    if (tmp != null) {
                        nearestPoint = tmp;
                        nowmindis = nearestPoint.distanceTo(target);
                    }
                }
            }
            else {
                if (target.y() - this.point.y() < nowmindis && lc != null) {
                    tmp = lc.findNearBelow(target, nowmindis);
                    if (tmp != null) {
                        nearestPoint = tmp;
                        nowmindis = nearestPoint.distanceTo(target);
                    }
                }
                if (this.point.y() - target.y() < nowmindis && rc != null) {
                    tmp = rc.findNearBelow(target, nowmindis);
                    if (tmp != null) {
                        nearestPoint = tmp;
                        nowmindis = nearestPoint.distanceTo(target);
                    }
                }

            }
            return nearestPoint;
        }


        // getvalue
        public Value value() {
            return val;
        }

    }

    private class NodeLevelOrderIterator implements Iterator<Point2D>, Iterable<Point2D> {
        // to perform level order traversal
        Queue<Node> totravesal;

        // init iterator
        public NodeLevelOrderIterator(Node root) {
            totravesal = new Queue<Node>();
            totravesal.enqueue(root);
        }

        // basic iterator function
        public boolean hasNext() {
            return totravesal.size() != 0;
        }

        // basic iterator function
        public Point2D next() {
            Node tmp;
            if (!hasNext()) return null;
            tmp = totravesal.dequeue();
            if (tmp.lc != null) totravesal.enqueue(tmp.lc);
            if (tmp.rc != null) totravesal.enqueue(tmp.rc);
            return tmp.point;
        }

        // make iterator as an iterable object
        public Iterator<Point2D> iterator() {
            return new NodeLevelOrderIterator(root);
        }
    }

    // reference not null if it is throw expection
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
        KdTreeST<String> kd = new KdTreeST<String>();
        kd.put(p1, "p1");
        kd.put(p2, "p2");
        kd.put(p3, "p3");
        kd.put(p4, "p4");
        kd.put(p5, "p5");
        StdOut.println(kd.get(p1));
        StdOut.println(kd.get(p3));
        for (Point2D p : kd.points()) {
            StdOut.println(" x " + p.x() + " y " + p.y());
        }
        RectHV rect = new RectHV(1, 1, 2, 2);
        for (Point2D p : kd.range(rect)) {
            StdOut.println(" x " + p.x() + " y " + p.y());
        }
        Point2D p6 = new Point2D(4.1, 4.1);
        Point2D find = kd.nearest(p6);
        StdOut.println(" x " + find.x() + " y " + find.y());
        StdOut.println("unit test finish");

        In in = new In("input1M.txt");
        // initialize the two data structures with point from standard input
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();

        PointST<Integer> pst = new PointST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            pst.put(p, i);
        }

        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 60000000; i++) {
            Point2D p = new Point2D(StdRandom.uniform(0, 1), StdRandom.uniform(0, 1));
            kdtree.nearest(p);
        }
        StdOut.println("60000000 times kdtree nearest :" + sw.elapsedTime() + " sec");
        sw = new Stopwatch();
        for (int i = 0; i < 60; i++) {
            Point2D p = new Point2D(StdRandom.uniform(0, 1), StdRandom.uniform(0, 1));
            pst.nearest(p);
        }
        StdOut.println("60 times pointST nearest :" + sw.elapsedTime() + " sec");


    }
}
