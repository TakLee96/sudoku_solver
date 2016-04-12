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
