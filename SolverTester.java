/* YOU DON'T NEED TO READ THIS FILE */

public class SolverTester {

    public static void main(String[] args) {
        long time = 0;
        long maxtime = 0;
        Sudoku maxinstance = null;
        int portion = Math.max(Constant.NUM_TRIALS / 10, 1);
        for (int i = 1; i <= Constant.NUM_TRIALS; i++) {
            Sudoku instance = new Sudoku();
            Sudoku copy = instance.deepcopy();
            Solver s = new Solver(instance);
            long elapsed = s.start();
            if (instance.checkAllConstraints() && instance.complete()) {
                time += elapsed;
                if (i % portion == 0)
                    System.out.println((i / portion) +
                        "0% solved; time elapsed: " + time + "ms");
                if (elapsed > maxtime) {
                    maxtime = elapsed;
                    maxinstance = copy;
                }
            } else {
                System.out.println(copy);
                System.out.println("BECOMES");
                System.out.println(instance);
                throw new RuntimeException("Failed to solve Sudoku #" + i);
            }
        }
        System.out.println("Solved " + Constant.NUM_TRIALS + " puzzles in " + time + "ms");
        System.out.println("On average, it's " + (1.0 * time / Constant.NUM_TRIALS) + "ms per puzzle");
        System.out.println("Toughest puzzle takes " + maxtime + "ms to solve:");
        System.out.println(maxinstance);
    }

}
