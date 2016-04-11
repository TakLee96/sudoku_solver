import java.util.Random;
import java.util.Iterator;

public class Sudoku implements Iterable<Tuple> {

    /****************************************************
     * BELOW are the parts of code that YOU SHOULD READ *
     ****************************************************/

    /* IMPORTANT: r is row and c is col, both range from 1 to 9; the
     * value for a cell is ranged from 0 to 9, where 0 indicates empty */
    public int get(int r, int c) { return this.grid[r-1][c-1]; }
    public void set(int r, int c, int val) { this.grid[r-1][c-1] = val; }
    public boolean empty(int r, int c) { return this.get(r, c) == 0; }

    /* Return true if none of the cells is 0/empty */
    public boolean complete() {
        for (int r = 1; r <= 9; r++)
            for (int c = 1; c <= 9; c++)
                if (this.get(r, c) == 0)
                    return false;
        return true;
    }

    /* Check all the constraints, RETURN TRUE if all of them
     * are SATISFIED; otherwise, return false. */
    public boolean checkAllConstraints() {
        for (int r = 1; r <= 9; r++)
            if (!this.checkRow(r))
                return false;
        for (int c = 1; c <= 9; c++)
            if (!this.checkColumn(c))
                return false;
        return (
            this.checkBlock(1, 1) && this.checkBlock(1, 4) && this.checkBlock(1, 7) &&
            this.checkBlock(4, 1) && this.checkBlock(4, 4) && this.checkBlock(4, 7) &&
            this.checkBlock(7, 1) && this.checkBlock(7, 4) && this.checkBlock(7, 7)
        );
    }

    /* Check all the constraints related to this cell at
     * Row r and Column c.  */
    public boolean checkConstraints(int r, int c) {
        return (this.checkRow(r) && this.checkColumn(c) && this.checkBlock(r, c));
    }

    /* Return a DEEP COPY of the current Sudoku instance */
    public Sudoku deepcopy() {
        return new Sudoku(grid);
    }

    /* Return the an iterator over all the possible position tuples
     * Use it this way:
     *     for (Tuple t : sudokuInstance) {
     *         int r = t.row(); int c = t.col();
     *         int val = instance.get(r, c);
     *         ...
     *     }
     */
    @Override
    public Iterator<Tuple> iterator() {
        return new SudokuIterator();
    }

    /****************************************************
     * ABOVE are the parts of code that YOU SHOULD READ *
     ****************************************************/







    /* YOU DON'T NEED to know about these methods BELOW */
    private static class SudokuIterator implements Iterator<Tuple> {
        private int count = 0;
        @Override
        public boolean hasNext() {
            return count < 81;
        }
        @Override
        public Tuple next() {
            int col = count % 9 + 1;
            int row = count / 9 + 1;
            count = count + 1;
            return new Tuple(row, col);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= 9; r++) {
            sb.append("| ");
            for (int c = 1; c < 9; c++) {
                sb.append(this.get(r, c));
                sb.append(", ");
            }
            sb.append(this.get(r, 9));
            sb.append(" |\n");
        }
        return sb.toString();
    }

    private static final double fillingRatio = 0.22;

    private int[][] grid;
    public Sudoku(int seed) {
        Random random = new Random(seed);
        this.grid = new int[9][9];
        for (int r = 1; r <= 9; r++)
            for (int c = 1; c <= 9; c++)
                if (random.nextDouble() < fillingRatio)
                    do this.set(r, c, random.nextInt(9) + 1);
                    while (!this.checkConstraints(r, c));
    }

    private Sudoku(int[][] grid) {
        this.grid = new int[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.grid[i][j] = grid[i][j];
    }

    private boolean checkRow(int r) {
        boolean[] exist = new boolean[9];
        for (int c = 1; c <= 9; c++) {
            if (this.empty(r, c)) {
                continue;
            } else if (exist[this.get(r, c)-1]) {
                return false;
            } else {
                exist[this.get(r, c)-1] = true;
            }
        }
        return true;
    }

    private boolean checkColumn(int c) {
        boolean[] exist = new boolean[9];
        for (int r = 1; r <= 9; r++) {
            if (this.empty(r, c)) {
                continue;
            } else if (exist[this.get(r, c)-1]) {
                return false;
            } else {
                exist[this.get(r, c)-1] = true;
            }
        }
        return true;
    }

    private boolean checkBlock(int r, int c) {
        int[] row, col;
        if      (r <= 3) row = new int[]{ 1, 2, 3 };
        else if (r <= 6) row = new int[]{ 4, 5, 6 };
        else             row = new int[]{ 7, 8, 9 };
        if      (c <= 3) col = new int[]{ 1, 2, 3 };
        else if (c <= 6) col = new int[]{ 4, 5, 6 };
        else             col = new int[]{ 7, 8, 9 };
        boolean[] exist = new boolean[9];
        for (int rr : row) {
            for (int cc : col) {
                if (this.empty(rr, cc)) {
                    continue;
                } else if (exist[this.get(rr, cc)-1]) {
                    return false;
                } else {
                    exist[this.get(rr, cc)-1] = true;
                }
            }
        }
        return true;
    }

}
