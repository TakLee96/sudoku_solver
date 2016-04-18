import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Tracker {

    private static HashSet<Integer> full = new HashSet<Integer>();
    static {
        full.add(1); full.add(2); full.add(3);
        full.add(4); full.add(5); full.add(6);
        full.add(7); full.add(8); full.add(9);
    }

    private boolean dead;
    public boolean dead() { return dead; }

    private HashSet<Integer>[][] domain;
    private Sudoku instance;

    @SuppressWarnings("unchecked")
    private void init() {
        this.dead = false;
        this.domain = new HashSet[9][9];
        LinkedList<Tuple> queue = new LinkedList<Tuple>();
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                if (instance.empty(row, col)) {
                    domain[row-1][col-1] = (HashSet<Integer>) full.clone();
                } else {
                    HashSet<Integer> s = new HashSet<Integer>();
                    s.add(instance.get(row, col));
                    domain[row-1][col-1] = s;
                    queue.add(new Tuple(row, col));
                }
            }
        }
        while (!queue.isEmpty())
            pruneDomain(queue.poll(), queue);
    }
    public Tracker(Sudoku instance) {
        this.instance = instance;
        init();
    }

    public HashSet<Integer> domain(int row, int col) {
        return domain[row-1][col-1];
    }

    public HashSet<Integer> domain(Tuple t) {
        return domain(t.row(), t.col());
    }

    private boolean remove(int row, int col, Integer elem, LinkedList<Tuple> queue) {
        HashSet<Integer> s = domain(row, col);
        if (s.remove(elem) && s.size() == 1) {
            queue.add(new Tuple(row, col));
        }
        if (s.size() == 0) {
            queue.clear();
            dead = true;
            return true;
        }
        return false;
    }

    private void pruneDomain(Tuple t, LinkedList<Tuple> queue) {
        if (dead) return;
        Integer elem = (Integer) domain(t).toArray()[0];
        for (int row = 1; row <= 9; row++)
            if (row != t.row())
                if (remove(row, t.col(), elem, queue))
                    return;
        for (int col = 1; col <= 9; col++)
            if (col != t.col())
                if (remove(t.row(), col, elem, queue))
                    return;
        int row = t.row() - (t.row() - 1) % 3;
        int col = t.col() - (t.col() - 1) % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (row + i != t.row() || col + j != t.col())
                    if (remove(row + i, col + j, elem, queue))
                        return;
    }

    public void put(Tuple t, Integer v) {
        instance.set(t, v);
        HashSet<Integer> s = domain(t);
        s.clear();
        s.add(v);
        LinkedList<Tuple> queue = new LinkedList<Tuple>();
        queue.add(t);
        while (!queue.isEmpty())
            pruneDomain(queue.poll(), queue);
    }

    public void rewind(Tuple t) {
        instance.set(t, 0);
        init();
    }

    public Tuple mrv() {
        if (dead) return null;
        int minVal = 10; Tuple minPos = null;
        for (int row = 1; row <= 9; row++) {
            for (int col = 1; col <= 9; col++) {
                if (instance.empty(row, col) && domain(row, col).size() < minVal) {
                    minVal = domain(row, col).size();
                    minPos = new Tuple(row, col);
                    if (minVal == 1) return minPos;
                }
            }
        }
        return minPos;
    }

    @Override
    public String toString() {
        return instance.toString();
    }

}
