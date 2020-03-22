import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class Percolation {

    // Attributes
    private byte[][] grid; // N x N grid that represents the system
    private int[][] id; //

    private int len; // length of rows and columns
    private int count; // Count of open sites

    private WeightedQuickUnionUF quickUnionUF; // Establish relation between sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Index must be higher than 0");
        count = 0;
        len = n;
        grid = new byte[n + 1][n + 1];
        id = new int[n + 1][n + 1];

        quickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                grid[i][j] = 0;
                id[i][j] = len * (i - 1) + (j - 1);
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;
        grid[row][col] = 1;
        count++;
        fill(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        System.out.print(quickUnionUF.connected(id[row][col], len * len));
        return quickUnionUF.connected(id[row][col], len * len);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.count;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionUF.connected(len * len - 2, len * len - 1);
    }

    // Test sites around grid[row][col] to apply union if are open
    private void fill(int row, int col) {
        int site = id[row][col];

        if (inRange(col + 1) && isOpen(row, col + 1)) quickUnionUF.union(site, id[row][col + 1]);
        if (inRange(col - 1) && isOpen(row, col - 1)) quickUnionUF.union(site, id[row][col - 1]);
        if (inRange(row + 1) && isOpen(row + 1, col)) quickUnionUF.union(site, id[row + 1][col]);
        if (inRange(row - 1) && isOpen(row - 1, col)) quickUnionUF.union(site, id[row - 1][col]);
        if (row == 1) quickUnionUF.union(site, len * len);
        if (row == len) quickUnionUF.union(site, len * len + 1);
    }

    // Returns true if element is between 1 and len
    private boolean inRange(int i) {
        return !(i < 1 || i > len);
    }

    // validate that p is a valid index
    private void validate(int row, int col) {
        if (row <= 0 || row > len)
            throw new IllegalArgumentException("Index " + row + " is not between 1 and " + len);
        if (col <= 0 || col > len)
            throw new IllegalArgumentException("indexes " + col + " is not between 1 and " + len);
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
