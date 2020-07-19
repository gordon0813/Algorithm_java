/* *****************************************************************************
 *  Name:    Chuang Bo-Han
 *  NetID:   B06505004
 *  Precept: P00
 *
 *  Description:  PercolationStats
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // independent Percolation test record
    private double[] record;
    private double _mean;
    // standard deviation
    private double _stddev;
    // low endpoint of 95% confidence interval
    private double confLow;
    // high endpoint of 95% confidence interval
    private double confHigh;
    // to measure running time
    private Stopwatch sw;
    // ruinning time
    private double elapseT;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) throw new IllegalArgumentException();
        // how many grid has open
        double count = 0;
        // random row id
        int randrow;
        // random column id
        int randcol;
        Percolation pc;
        record = new double[trials];
        sw = new Stopwatch();
        for (int i = 0; i < trials; ++i) {
            pc = new Percolation(n);
            count = 0;
            while (!pc.percolates()) {
                randrow = StdRandom.uniform(n);
                randcol = StdRandom.uniform(n);

                if (!pc.isOpen(randrow, randcol)) {
                    count += 1;
                    pc.open(randrow, randcol);
                }
            }
            record[i] = count / (n * n);
        }
        elapseT = sw.elapsedTime();
        _mean = StdStats.mean(record);
        _stddev = StdStats.stddev(record);
        confLow = _mean - (1.96 * _stddev) / Math.sqrt(trials);
        confHigh = _mean + (1.96 * _stddev) / Math.sqrt(trials);
    }


    // sample mean of percolation threshold
    public double mean() {
        return _mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return _stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return confLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confHigh;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats pc = new PercolationStats(n, trails);
        StdOut.println("mean                    = " + pc.mean());
        StdOut.println("stddev                  = " + pc.stddev());
        StdOut.println(
                "95% confidence interval = " + pc.confidenceLow() + ", " + pc.confidenceHigh());
        StdOut.println("elapse time             = " + pc.elapseT);
    }

}
