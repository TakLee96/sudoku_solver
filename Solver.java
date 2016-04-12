/* YOU NEED TO IMPLEMENT ONE METHOD IN THIS FILE */

public class Solver implements Runnable {

    /****************************************************
     * BELOW are the parts of code that YOU SHOULD READ *
     ****************************************************/

    /* Make sure to CHECK THIS VARIABLE OFTEN to make sure
     * that you terminate your solve method whenever this
     * boolean variable becomes true */
    private boolean timedOut;

    /* Solves the Sudoku instance IN-PLACE */
    public void solve(Sudoku instance) {
        /* TODO: YOUR CODE HERE */
        
    }

    /* Run SolverTester to begin a sequence of tests
     * Run Solver to try to solve one instance of the Sudoku game
     * This can help you debug and see why your code might be slow
     * You can also modify Constant.java */
    public static void main(String[] args) {
        Sudoku instance = new Sudoku(new int[][]{
            { 0, 0, 0, 0, 5, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 7 },
            { 0, 0, 0, 1, 0, 3, 2, 4, 5 },
            { 0, 0, 3, 0, 0, 0, 0, 0, 8 },
            { 0, 8, 0, 3, 0, 0, 0, 9, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 2 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0, 6, 0, 0, 0 },
            { 0, 0, 2, 0, 1, 0, 0, 0, 0 }
        });
        Solver s = new Solver(instance);
        System.out.println(instance);
        long now = System.currentTimeMillis();
        s.run();
        if (instance.complete() && instance.checkAllConstraints()) {
            System.out.println("Solved in " + (System.currentTimeMillis() - now) + "ms:");
        } else {
            System.out.println("Failed to solve the instance...");
        }
        System.out.println(instance);
    }

    /****************************************************
     * ABOVE are the parts of code that YOU SHOULD READ *
     ****************************************************/
















    /* YOU DON'T NEED to know about these methods BELOW */
    private Sudoku instance;
    private Thread blinker;
    public Solver(Sudoku instance) {
        this.instance = instance;
        this.blinker = new Thread(this);
        this.timedOut = false;
    }

    public long start() {
        long start = System.currentTimeMillis();
        blinker.start();
        while (blinker.isAlive() &&
            System.currentTimeMillis() - start < Constant.MAX_SOLVE_TIME);
        this.timedOut = true;
        return System.currentTimeMillis() - start;
    }

    public void run() {
        solve(this.instance);
    }

    public void stop() {
        this.timedOut = true;
    }

}
