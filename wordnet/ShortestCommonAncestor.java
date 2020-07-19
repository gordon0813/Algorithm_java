/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  ShortestCommonAncestor that no need to unmark
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class ShortestCommonAncestor {
    // mindisA record min distance from set A to this node
    private int[] mindisA;
    // same as mindisA
    private int[] mindisB;
    // markA is mark when set A can reach this vertex
    private int[] markA;
    // same as markA
    private int[] markB;
    // that is: mark == markconstant ==> is mark ,this let us no need to unmark
    // all we need to do is markconstant++
    private int markconstA;
    // as markconstA
    private int markconstB;
    // shortest length of to node
    private int shortestlength;
    // nearest commen ancestor
    private int shortestancestor;
    // Graph
    private Digraph G;

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        valid(G);
        mindisA = new int[G.V()];
        mindisB = new int[G.V()];
        markA = new int[G.V()];
        markB = new int[G.V()];
        markconstA = 0;
        markconstB = 0;
        this.G = G;

    }


    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        Queue<Integer> a = new Queue<Integer>();
        Queue<Integer> b = new Queue<Integer>();
        a.enqueue(v);
        b.enqueue(w);
        lengthSubset(a, b);
        return shortestlength;
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        length(v, w);
        return shortestancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        initbfs();
        for (int v : subsetA) {
            bfsA(v, 0);
        }
        for (int v : subsetB) {
            bfsB(v, 0);
        }
        return shortestlength;
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        lengthSubset(subsetA, subsetB);
        return shortestancestor;
    }

    // that is virtually unmark all node by markconst++
    private void initbfs() {
        markconstA++;
        markconstB++;
        shortestlength = Integer.MAX_VALUE;
    }

    // bfs A part from nowv with disrance nowdis
    private void bfsA(int nowv, int nowdis) {
        if (ismarkA(nowv)) {
            // if this vertex already vistied and by other A set member with
            // smaller distance , no need to continue
            if (mindisA[nowv] < nowdis) {
                return;
            }
            else {
                mindisA[nowv] = nowdis;
            }
        }
        else {
            mindisA[nowv] = nowdis;
            markA(nowv);
        }
        for (int v : G.adj(nowv)) {
            bfsA(v, nowdis + 1);
        }
    }

    // bfs B part from nowv with disrance nowdis
    private void bfsB(int nowv, int nowdis) {
        if (ismarkB(nowv)) {
            if (mindisB[nowv] < nowdis) {
                return;
            }
            else {
                mindisB[nowv] = nowdis;
            }
        }
        else {
            mindisB[nowv] = nowdis;
            markB(nowv);

        }
        if (ismarkA(nowv)) {
            if (mindisB[nowv] + mindisA[nowv] < shortestlength) {
                shortestlength = mindisB[nowv] + mindisA[nowv];
                shortestancestor = nowv;
            }
            else {
                return;
            }
        }
        for (int v : G.adj(nowv)) {
            bfsB(v, nowdis + 1);
        }
    }

    // is node in wordnet be marked in a part ?
    private boolean ismarkA(int v) {
        return markconstA == markA[v];
    }

    // mark a node in wordnet in a part
    private void markA(int v) {
        markA[v] = markconstA;
    }

    // is node in wordnet be marked in b part ?
    private boolean ismarkB(int v) {
        return markconstB == markB[v];
    }

    // mark a node in wordnet in b part
    private void markB(int v) {
        markB[v] = markconstB;
    }

    // this graph is root DAG or not
    private void valid(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException();
        }
        int totalv = G.V();
        int rootcount = 0;
        for (int v = 0; v < totalv; v++) {
            if (G.outdegree(v) == 0) {
                rootcount++;
            }
        }
        if (rootcount > 1) {
            throw new IllegalArgumentException();
        }
        DirectedCycle dc = new DirectedCycle(G);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        In in = new In("digraph25.txt");
        Digraph G = new Digraph(in);
        for (int i : G.adj(7)) {
            StdOut.println(i);
        }
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        StdOut.println(sca.length(16, 13));
        StdOut.println(sca.length(17, 11));
        StdOut.println(sca.ancestor(14, 13));
        StdOut.println(sca.ancestor(20, 13));
        Queue<Integer> a = new Queue<Integer>();
        Queue<Integer> b = new Queue<Integer>();
        a.enqueue(13);
        a.enqueue(23);
        a.enqueue(24);
        b.enqueue(6);
        b.enqueue(16);
        b.enqueue(17);
        StdOut.println(sca.ancestorSubset(a, b));
        StdOut.println(sca.lengthSubset(a, b));
        // test circle
        G = new Digraph(5);
        G.addEdge(0, 1);
        G.addEdge(1, 2);
        G.addEdge(2, 3);
        G.addEdge(3, 0);
        G.addEdge(0, 4);
        // throw exception
        new ShortestCommonAncestor(G);
    }
}
