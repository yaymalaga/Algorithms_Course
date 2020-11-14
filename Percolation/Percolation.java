import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF model;
    private boolean[][] matrix;
    private int openedCounter;
    private final int gridSize;
    private final int virtualTopNode;
    private final int virtualBottomNode;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // NxN grid + 2 virtual nodes (top and bottom)
        model = new WeightedQuickUnionUF((n * n) + 2);

        matrix = new boolean[n][n];
        openedCounter = 0;
        gridSize = n;

        virtualTopNode = gridSize * gridSize;
        virtualBottomNode = virtualTopNode + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        final int modelRow = processInput(row);
        final int modelCol = processInput(col);

        if (isOpen(row, col))
            return;

        // Set open
        matrix[modelRow][modelCol] = true;
        openedCounter = openedCounter + 1;

        final int node = transformRowCol(modelRow, modelCol);

        // Connect to top virtual node
        if (modelRow == 0) {
            model.union(node, virtualTopNode);
        }

        // Connect to bottom virtual node
        if (modelRow == gridSize - 1) {
            model.union(node, virtualBottomNode);
        }

        // Neighbor connections
        connectIfPossible(row - 1, col, node);
        connectIfPossible(row + 1, col, node);
        connectIfPossible(row, col - 1, node);
        connectIfPossible(row, col + 1, node);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        final int modelRow = processInput(row);
        final int modelCol = processInput(col);

        return matrix[modelRow][modelCol];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col))
            return false;

        return model.find(virtualTopNode) == model.find(transformRowCol(row - 1, col - 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        // Check if the virtual nodes are connected (last two)
        return model.find(virtualBottomNode) == model.find(virtualTopNode);
    }

    private boolean isValidPos(int row, int col) {
        return (row > 0 && col > 0 && row <= gridSize && col <= gridSize);
    }

    private void connectIfPossible(int row, int col, int node) {
        if (!isValidPos(row, col) || !isOpen(row, col))
            return;

        final int modelRow = processInput(row);
        final int modelCol = processInput(col);

        int neighborNode = transformRowCol(modelRow, modelCol);

        model.union(neighborNode, node);
    }

    private int processInput(int value) {
        if (value < 1 || value > gridSize) {
            throw new IllegalArgumentException();
        }

        // Model uses range 0 to N-1
        return value - 1;
    }

    private int transformRowCol(int row, int col) {
        return (row * gridSize) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        final int modelSize = 20;
        final Percolation percolationSystem = new Percolation(modelSize);

        while (!percolationSystem.percolates()) {
            final int row = StdRandom.uniform(1, modelSize + 1);
            final int col = StdRandom.uniform(1, modelSize + 1);

            if (!percolationSystem.isOpen(row, col)) {
                percolationSystem.open(row, col);
            }
        }

        final double threshold = percolationSystem.numberOfOpenSites() / (double) (modelSize * modelSize);
        System.out.println(threshold);
    }
}
