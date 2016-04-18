import java.util.HashSet;

class AmazingSolver extends Solver {

    public AmazingSolver(Sudoku instance) {
        super(instance);
    }

    @Override
    public void solve(Sudoku instance) {
        try {
            EfficientTracker et = new EfficientTracker(instance);
            if (et.dead()) return;
            backtrack(et);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean backtrack(EfficientTracker et) {
        if (timedOut) throw new RuntimeException("timed out");
        if (et.dead()) return false;

        Tuple next = et.mrv();
        if (next == null) return et.done();

        Domain domain = et.domain(next);
        int[] d = domain.lcvdomain(et, next); int v = -1;

        EfficientTracker.Rewinder r = null;
        for (int i = 0; i < d.length; i++) {
            v = d[i];
            r = et.fill(new Triple(next, v));
            if (backtrack(et)) return true;
            et.rewind(r);
        }

        return false;
    }

    public static void main(String[] args) {
        Sudoku instance = new Sudoku(new int[][]{
            { 4, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 2, 0, 0, 4, 0 },
            { 0, 0, 0, 0, 0, 7, 0, 0, 0 },
            { 0, 8, 0, 0, 0, 0, 5, 0, 9 },
            { 0, 7, 0, 0, 0, 0, 4, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 7, 0, 0, 0, 0, 0, 0 },
            { 0, 0, 0, 7, 0, 0, 9, 0, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
        });
        AmazingSolver s = new AmazingSolver(instance);
        System.out.println(instance);
        long time = s.start();
        if (instance.complete() && instance.checkAllConstraints()) {
            System.out.println("Solved in " + time + "ms:");
        } else {
            System.out.println("Failed to solve the instance... [" + time + "ms]");
        }
        System.out.println(instance);
    }

}
