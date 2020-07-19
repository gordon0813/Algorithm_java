/* *****************************************************************************
 *  Name:    Chuang bo-han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  start d
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {
    // n by n board extend to id
    int[] board;
    // len of origion 2d board
    int s;
    // hamming
    int hdis;
    // manhattan
    int mdis;
    // where is zero
    int zeroID;

    // init a board with int array
    public Board(int[][] tiles) {
        s = tiles.length;
        board = new int[s * s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                board[conv2dTo1d(i, j)] = tiles[i][j];

            }
        }
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) zeroID = i;
            hdis += hammingdis(i);
            mdis += manhattandis(i);
        }
    }

    // constructer that let us no need to do useless step
    private Board() {
    }

    // copy a board
    private Board copy() {
        Board re = new Board();
        re.s = this.size();
        re.board = this.board.clone();
        re.zeroID = this.zeroID;
        re.hdis = this.hamming();
        re.mdis = this.manhattan();
        return re;
    }

    // string representation of this board
    public String toString() {
        StringBuilder ss = new StringBuilder();
        ss.append(s + "\n");
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                ss.append(String.format("%2d ", board[conv2dTo1d(i, j)]));
            }
            ss.append("\n");
        }
        return ss.toString();
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        return board[conv2dTo1d(row, col)];
    }

    // board size n
    public int size() {
        return s;
    }
    // overide hashcode

    // return board's hashcode
    public int hashCode() {
        return Arrays.hashCode(board);
    }

    // number of tiles out of place
    public int hamming() {
        return hdis;
    }

    // hamming distance of one unit
    private int hammingdis(int d) {
        int num = board[d];
        if (num == 0) return 0;
        return (d == num - 1) ? 0 : 1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return mdis;
    }

    // Manhattan  distance of one unit
    private int manhattandis(int d) {
        int num = board[d];
        if (num == 0) return 0;
        return abs((num - 1) / s - d / s) + abs((num - 1) % s - d % s);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hdis == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) return false;
        Board other = (Board) y;
        if (this.size() != other.size()) return false;
        for (int i = 0; i < this.size(); ++i) {
            if (board[i] != other.board[i]) return false;
        }
        return true;
    }

    //  swap 0 to where(input 0 : left ,1 :down ,2:right,3:up)
    private Board swapto(int n) {
        int[] rc = conv1dTo2d(zeroID);
        Board re = null;
        if (n == 0) {
            if (rc[1] == 0) return null;
            re = copy();
            re.unsafeswap(zeroID - 1);
        }
        else if (n == 1) {
            if (rc[0] == s - 1) return null;
            re = copy();
            re.unsafeswap(zeroID + s);
        }
        else if (n == 2) {
            if (rc[1] >= s - 1) return null;
            re = copy();
            re.unsafeswap(zeroID + 1);
        }
        else if (n == 3) {
            if (rc[0] == 0) return null;
            re = copy();
            re.unsafeswap(zeroID - s);
        }
        else {
            return null;
        }

        return re;
    }

    // change 0 with id to
    private void unsafeswap(int to) {
        this.mdis -= manhattandis(to);
        this.hdis -= hammingdis(to);
        board[zeroID] = board[to];
        board[to] = 0;
        this.mdis += manhattandis(zeroID);
        this.hdis += hammingdis(zeroID);
        zeroID = to;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> boards = new Stack<Board>();
        Board insert = null;
        for (int i = 0; i < 4; i++) {
            insert = swapto(i);
            if (insert != null) boards.push(insert);
        }
        return boards;
    }


    // is this board solvable?
    public boolean isSolvable() {
        if (s % 2 == 1) {
            return numberofInv() % 2 == 0;
        }
        else {
            return (numberofInv() + zeroID / s) % 2 == 1;
        }
    }

    // number of inverse number
    private int numberofInv() {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = i; j < board.length; j++) {
                if (board[i] > board[j] && board[j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    // convert 2d index to id index
    private int conv2dTo1d(int r, int c) {
        return r * s + c;
    }

    // convert id index to 2d index
    private int[] conv1dTo2d(int i) {
        int[] re = new int[2];
        re[0] = i / s;
        re[1] = i % s;
        return re;
    }

    // absolute value
    private int abs(int i) {
        return (i >= 0) ? i : -i;
    }

    // unit testing (required)
    public static void main(String[] args) {

        int[][] multi = new int[][] {
                { 1, 2, 3 },
                { 0, 4, 6 },
                { 8, 5, 7 },
                };
        int[][] multi2 = new int[][] {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 },
                };
        Board b = new Board(multi);
        StdOut.println(b);
        StdOut.println(b.isSolvable());
        Board b1 = new Board(multi2);
        StdOut.println(b1);
        StdOut.println(b1.isSolvable());
        int[][] multi3 = new int[][] {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 },
                };
        Board b3 = new Board(multi3);
        StdOut.println(b1);
        StdOut.println("hdis:" + b1.hamming() + " mdis:" + b1.manhattan());
        StdOut.println("b2 same as b3:" + b3.equals(b1));
        StdOut.println("all instance of b2");
        for (Board bi : b1.neighbors()) {
            StdOut.println(bi + "\nhdis:" + bi.hamming() + " mdis:" + bi.manhattan());
            for (Board bii : bi.neighbors()) {
                StdOut.println(bii);
                StdOut.println(bii.hashCode());
            }
        }

    }
}
