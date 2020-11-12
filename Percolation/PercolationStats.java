import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double thresholds[];
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        thresholds = new double[trials];
        mean = 0;
        stddev = 0;

        for (int i = 0; i < trials; i++) {
            thresholds[i] = runPercolation(n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(thresholds);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(thresholds);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (1.96 * Math.sqrt(stddev) / Math.sqrt(thresholds.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (1.96 * Math.sqrt(stddev) / Math.sqrt(thresholds.length));
    }

    private double runPercolation(int n) {
        final Percolation percolation = new Percolation(n);

        while (!percolation.percolates()) {
            final int row = StdRandom.uniform(1, n + 1);
            final int col = StdRandom.uniform(1, n + 1);

            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
            }
        }

        return percolation.numberOfOpenSites() / Double.valueOf(n * n);
    }

   // test client (see below)
   public static void main(String[] args) {
       final int n = Integer.parseInt(args[0]);
       final int trials = Integer.parseInt(args[1]);

       if (n <= 0 || trials <= 0) {
          throw new IllegalArgumentException();
       }

       final PercolationStats percolationStats = new PercolationStats(n, trials);

       System.out.println(percolationStats.mean());
       System.out.println(percolationStats.stddev());
       System.out.println("[" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
   }

}