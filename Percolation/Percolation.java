/* *****************************************************************************
 *  Name:    Chuang Bo-Han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  Percolation
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // record where is blocked (no need to be 2d)
    // with lenght n*n+1 (index would be n*row +column)(index n*n is top)
    private boolean[] arr;
    // set (only connect to top)
    private WeightedQuickUnionUF qf;
    // ground set (connect to top and ground)
    private WeightedQuickUnionUF gqf;
    // col and row length
    private int lenght;
    // number of open sites
    private int num;
    // top
    private int topid;
    // is percolate or not
    private boolean pc;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.lenght = n;
        this.arr = new boolean[n * n];
        this.topid = n * n;
        this.qf = new WeightedQuickUnionUF(n * n + 1);
        this.gqf = new WeightedQuickUnionUF(n * n + 2);
        this.pc = false;
        this.num = 0;
    }


    // opens the site (row, col) if it is not open already
    // also union the connect set
    public void open(int row, int col) {
        if (this.isOpen(row, col)) return;

        validin(row, col);

        int thisID = rcTOid(row, col);

        this.arr[thisID] = true;
        if (row == 0) {
            qf.union(topid, thisID);
            gqf.union(topid, thisID);
        }
        else if (isOpen(row - 1, col)) { // -1
            qf.union(rcTOid(row - 1, col), thisID);
            gqf.union(rcTOid(row - 1, col), thisID);
        }
        if (col != 0 && isOpen(row, col - 1)) { // -1
            qf.union(rcTOid(row, col - 1), thisID);
            gqf.union(rcTOid(row, col - 1), thisID);
        }
        if (col != lenght - 1 && isOpen(row, col + 1)) { // -1
            qf.union(rcTOid(row, col + 1), thisID);
            gqf.union(rcTOid(row, col + 1), thisID);
        }
        if (row == lenght - 1) {
            gqf.union(topid + 1, thisID);
        }
        else if (isOpen(row + 1, col)) { // -1
            qf.union(rcTOid(row + 1, col), thisID);
            gqf.union(rcTOid(row + 1, col), thisID);
        }
        this.num += 1;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validin(row, col);
        return this.arr[rcTOid(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validin(row, col);
        if (!isOpen(row, col)) return false; // -1
        return qf.find(rcTOid(row, col)) == qf.find(topid);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.num;
    }

    // does the system percolate?
    public boolean percolates() {
        return gqf.find(topid + 1) == gqf.find(topid);
    }

    // row column to id
    private int rcTOid(int r, int c) {
        return lenght * r + c;
    }

    private void validin(int r, int c) {
        if (r >= this.lenght || c >= this.lenght || r < 0 || c < 0) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Percolation testpc;
        testpc = new Percolation(3);
        StdOut.println("open 0 1");
        testpc.open(0, 1);
        StdOut.println("0 1 is open: " + testpc.isOpen(0, 1));
        StdOut.println("1 1 is open: " + testpc.isOpen(1, 1));
        StdOut.println("is Percolation: " + testpc.percolates());
        StdOut.println("open 1 1");
        testpc.open(1, 1);
        StdOut.println("1 1 is open: " + testpc.isOpen(1, 1));
        StdOut.println("2 1 is open: " + testpc.isOpen(2, 1));
        StdOut.println("is Percolation: " + testpc.percolates());
        StdOut.println("open 2 1");
        testpc.open(2, 1);
        StdOut.println("2 1 is open: " + testpc.isOpen(2, 1));
        StdOut.println("2 2 is open: " + testpc.isOpen(2, 2));
        StdOut.println("is Percolation: " + testpc.percolates());
        StdOut.println("2 1 is full: " + testpc.isFull(2, 1));
        StdOut.println("number of open: " + testpc.numberOfOpenSites());
    }


}
