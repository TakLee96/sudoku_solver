/* YOU NEED TO IMPLEMENT ONE METHOD IN THIS FILE */
public class Solver {

    /* Solves the Sudoku instance IN-PLACE */
    public static void solve(Sudoku instance) {
        /* TODO: YOUR CODE HERE */

    }

    public static void main(String[] args) {
        long time = 0;
        for (int i = 1; i <= Constant.NUM_TRIALS; i++) {
            Sudoku instance = new Sudoku();
            Sudoku copy = instance.deepcopy();
            long start = System.currentTimeMillis();
            solve(instance);
            long end = System.currentTimeMillis();
            if (instance.checkAllConstraints() && instance.complete()) {
                System.out.println("Puzzle #" + i + " complete in " + (end-start) + "ms");
                if (end - start > 300) {
                    System.out.println("This takes quite long: ");
                    System.out.println(copy);
                }
                time += end - start;
            } else {
                System.out.println(copy);
                System.out.println("BECOMES");
                System.out.println(instance);
                throw new RuntimeException("Failed to solve Sudoku #" + i);
            }
        }
        System.out.println("Solved " + Constant.NUM_TRIALS + " puzzles in " + time + "ms");
    }

}
