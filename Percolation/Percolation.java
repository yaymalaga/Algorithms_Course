import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    final private WeightedQuickUnionUF model;
    final int[][] matrix;
    private int openedCounter;
    final private int gridSize;
    final private int virtualTopNode;
    final private int virtualBottomNode;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // NxN grid + 2 virtual nodes (top and bottom)
        model = new WeightedQuickUnionUF((n * n) + 2);

        matrix = new int[n][n];
        openedCounter = 0;
        gridSize = n;
        
        virtualTopNode = gridSize*gridSize;
        virtualBottomNode = (gridSize*gridSize) + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        final int model_row = processInput(row);
        final int model_col = processInput(col);

        // Set open
        matrix[model_row][model_col] = 1;
        openedCounter = openedCounter + 1;

        final int node = transformRowCol(model_row, model_col);

        // Connect to top virtual node 
        if (model_row == 0) {
            model.union(node, virtualTopNode);
        // Connect to bottom virtual node
        } else if (model_row == gridSize - 1) {
            model.union(node, virtualBottomNode);
        }

        // Neighbor connections
        connectIfPossible(row-1, col, node);
        connectIfPossible(row+1, col, node);
        connectIfPossible(row, col-1, node);
        connectIfPossible(row, col+1, node);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        final int model_row = processInput(row);
        final int model_col = processInput(col);

        return matrix[model_row][model_col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) return false;

        boolean full = false;
        if (isOpen(row, col)) full = true;
        if (isOpen(row, col)) full = true;
        if (isOpen(row, col)) full = true;
        if (isOpen(row, col)) full = true;

        return full;
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
        if (!isValidPos(row, col) || !isOpen(row, col)) return;

        final int model_row = processInput(row);
        final int model_col = processInput(col);

        int neighborNode = transformRowCol(model_row, model_col);
            
        model.union(neighborNode, node);
    }

    private int processInput(int value) {
        if (value < 1) {
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

        final double threshold = percolationSystem.numberOfOpenSites() / Double.valueOf(modelSize * modelSize);
        System.out.println(threshold);
    }
}
