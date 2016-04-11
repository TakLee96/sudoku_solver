public class Solver {

    private static final int NUM_TRIALS = 25;

    /* Solves the Sudoku instance IN-PLACE */
    public static void solve(Sudoku instance) {
        /* TODO: YOUR CODE HERE */

    }

    public static void main(String[] args) {
        long time = 0;
        for (int i = 0; i < NUM_TRIALS; i++) {
            Sudoku instance = new Sudoku(i);
            Sudoku copy = instance.deepcopy();
            long start = System.currentTimeMillis();
            solve(instance);
            long end = System.currentTimeMillis();
            clear();
            if (instance.checkAllConstraints() && instance.complete()) {
                time += end - start;
            } else {
                System.out.println(copy);
                System.out.println("BECOMES");
                System.out.println(instance);
                throw new RuntimeException("Failed to solve Sudoku #" + i);
            }
        }
        System.out.println("Solved " + NUM_TRIALS + " puzzles in " + time + "ms");
    }

}
