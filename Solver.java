public class Solver {

    /* Solves the Sudoku instance IN-PLACE */
    public static void solve(Sudoku instance) {
        /* TODO: YOUR CODE HERE */
    }

    public static void main(String[] args) {
        Sudoku instance = new Sudoku(0);
        System.out.println(instance);
        long start = System.currentTimeMillis();
        solve(instance);
        long end = System.currentTimeMillis();
        if (instance.checkAllConstraints() && instance.complete()) {
            System.out.println("Solved successfully in " + (end-start) + " ms");
            System.out.println(instance);
        } else {
            throw new RuntimeException("Failed to solve the Sudoku!");
        }
    }

}
