import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;

class EfficientTracker {

    public class Rewinder {
        public LinkedList<Triple> updates;
        public LinkedList<Triple> fills;
        public Rewinder(LinkedList<Triple> updates,
                        LinkedList<Triple> fills) {
            this.updates = updates; this.fills = fills;
        }
    }

    public class Pitem implements Comparable<Pitem> {
        public Tuple item;
        public int priority;
        public Pitem(Tuple i, int p) {
            item = i; priority = p;
        }
        @Override
        public int compareTo(Pitem o) {
            return priority - o.priority;
        }
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            return item.equals(((Pitem) o).item);
        }
        @Override
        public String toString() {
            return item.toString() + "with p=" + priority;
        }
    }

    private LinkedList<Triple> updates;
    public PriorityQueue<Pitem> unfilledpq;
    private LinkedList<Triple> fills;
    private LinkedList<Triple> queue;
    public Domain[][] domain;
    private Sudoku instance;
    private boolean dead;
    public boolean nolcv = false;
    public boolean nomrv = false;
    public EfficientTracker(Sudoku inst) {
        updates = new LinkedList<Triple>();
        fills = new LinkedList<Triple>();
        domain = new Domain[9][9];
        instance = inst;
        dead = false;

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                domain[i][j] = new Domain();

        queue = instance.filledEntries();
        Triple t = null;
        while (!queue.isEmpty()) {
            t = queue.poll();
            if (instance.empty(t))
                instance.set(t);
            domain(t).empty();
            pruneDomain(t);
        }

        unfilledpq = new PriorityQueue<Pitem>();
        for (Tuple tp : instance.unfilledEntries())
            unfilledpq.add(new Pitem(tp, domain(tp).count()));
    }

    public boolean dead() { return dead; }

    public Domain domain(int row, int col) {
        return domain[row-1][col-1];
    }

    public Domain domain(Tuple t) {
        return domain[t.row()-1][t.col()-1];
    }

    public Sudoku instanceCopy() {
        return instance.deepcopy();
    }

    public Rewinder fill(Triple tri) {
        updates = new LinkedList<Triple>();
        fills = new LinkedList<Triple>();
        queue = new LinkedList<Triple>();
        queue.add(tri);

        tri = null; Domain d = null;
        while (!queue.isEmpty()) {
            tri = queue.poll();
            instance.set(tri);
            d = domain(tri);
            int[] pruned = d.set(tri.val());
            for (int i = 0; i < pruned.length; i++)
                updates.add(new Triple(tri.row(), tri.col(), pruned[i]));
            fills.add(tri);
            unfilledpq.remove(new Pitem(tri.toTuple(), 0));
            pruneDomain(tri);
        }
        return new Rewinder(updates, fills);
    }

    public void rewind(Rewinder r) {
        for (Triple tri : r.updates)
            domain(tri).add(tri.val());
        for (Triple tri : r.fills) {
            unfilledpq.add(new Pitem(tri.toTuple(), domain(tri).count()));
            instance.set(tri, 0);
        }
        dead = false;
    }

    public Tuple mrv() {
        if (unfilledpq.isEmpty()) return null;
        return unfilledpq.peek().item;
    }

    public boolean done() {
        return instance.complete();
    }

    private boolean remove(int row, int col, int elem) {
        Domain d = domain(row, col);
        boolean removed = d.remove(elem);
        boolean empty = instance.empty(row, col);

        if (removed) {
            updates.add(new Triple(row, col, elem));
            if (d.count() == 1 && empty)
                queue.add(new Triple(row, col, d.domain()[0]));
        }

        if (d.isEmpty() && empty) {
            queue.clear();
            dead = true;
            return true;
        }

        return false;
    }

    private void pruneDomain(Triple t) {
        if (dead) return;

        int elem = t.val();
        for (int row = 1; row <= 9; row++)
            if (row != t.row())
                if (remove(row, t.col(), elem))
                    return;
        for (int col = 1; col <= 9; col++)
            if (col != t.col())
                if (remove(t.row(), col, elem))
                    return;
        int row = t.row() - (t.row() - 1) % 3;
        int col = t.col() - (t.col() - 1) % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (row + i != t.row() || col + j != t.col())
                    if (remove(row + i, col + j, elem))
                        return;
    }

}
