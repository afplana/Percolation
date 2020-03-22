/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // Attributes
    private int trials;
    private double[] fractions;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        // Validation
        validate(n, trials);

        // Init
        this.trials = trials;
        fractions = new double[this.trials];

        // Test
        for (int i = 0; i < this.trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates())
                percolation.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);

            fractions[i] = percolation.numberOfOpenSites() * 1.0 / (n * n);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(trials);
    }

    // Validate that trials or grid size be higher than 0
    private void validate(int n, int t) {
        if (n <= 0) throw new IllegalArgumentException("N must be higher than 0");
        if (t <= 0)
            throw new IllegalArgumentException("Number of trials should be higher than 0");
    }

    // test client (see below)
    public static void main(String[] args) {
        int n, t;
        if (args.length == 2) {
            n = Integer.parseInt(args[0]);
            t = Integer.parseInt(args[1]);
        }
        else {
            n = StdIn.readInt();
            t = StdIn.readInt();
        }

        PercolationStats percolationStats = new PercolationStats(n, t);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", "
                               + percolationStats.confidenceHi() + "]");

    }
}
