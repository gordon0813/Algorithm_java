/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;
import java.util.Set;

public class Solver {
    // final node (solve successfully)
    private Node ans;
    // full solution
    private Stack<Board> sol;

    private class Node implements Comparable<Node> {
        // board at this state
        Board board;
        // number of move to reach this state
        int move;
        // previous state
        Node previous;

        // root constructer
        public Node(Board b) {
            board = b;
            move = 0;
            previous = null;
        }

        // normal constructer
        public Node(Board b, Node prev) {
            board = b;
            move = prev.move + 1;
            previous = prev;
        }

        // Comparable<Node> method
        public int compareTo(Node o) {
            return (board.manhattan() + move) - (o.board.manhattan() + o.move);
        }

        // return hashcode of board save in this node
        public int hashCode() {
            return board.hashCode();
        }

        // have same board
        public boolean equals(Object obj) {
            return board.equals(obj);
        }

        // return board
        public Board getBoard() {
            return board;
        }

        // return previous node
        public Node getPrevious() {
            return previous;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        sol = new Stack<Board>();
        boolean flag = true;
        Node root = new Node(initial);
        Node tmp;
        MinPQ<Node> minPQ = new MinPQ<Node>();
        Set<Board> set = new HashSet<Board>();
        minPQ.insert(root);
        while (flag) {
            tmp = minPQ.delMin();
            set.add(tmp.getBoard());
            for (Board b : tmp.getBoard().neighbors()) {
                if (set.contains(b)) continue;
                else if (b.isGoal()) {
                    ans = new Node(b, tmp);
                    flag = false;
                }
                else {
                    minPQ.insert(new Node(b, tmp));
                }
            }
        }
        Node prev = ans;
        while (prev != null) {
            sol.push(prev.getBoard());
            prev = prev.getPrevious();
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        return sol.size() - 1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return sol;
    }

    // unit test
    public static void main(String[] args) {
        int[][] multi1 = new int[][] {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 },
                };
        Board b1 = new Board(multi1);
        Solver s1 = new Solver(b1);
        for (Board b : s1.solution()) {
            StdOut.println(b + "\nhdis:" + b.hamming() + " mdis:" + b.manhattan());
        }


    }
}
