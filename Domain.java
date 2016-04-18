import java.util.Arrays;
import java.util.Comparator;

public class Domain {
    private static final boolean[] trues = {
        true, true, true, true, true, true, true, true, true
    };
    private static final boolean[] falses = {
        false, false, false, false, false, false, false, false, false
    };

    private boolean[] domain;
    private int count;
    public Domain() {
        domain = new boolean[9];
        System.arraycopy(trues, 0, domain, 0, 9);
        count = 9;
    }

    public boolean contains(int val) {
        return domain[val-1];
    }

    public boolean remove(int val) {
        boolean removed = domain[val-1];
        domain[val-1] = false;
        if (removed) count -= 1;
        return removed;
    }

    public boolean add(int val) {
        boolean added = !domain[val-1];
        domain[val-1] = true;
        if (added) count += 1;
        return added;
    }

    public int[] set(int val) {
        int[] retval = domain();
        System.arraycopy(falses, 0, domain, 0, 9);
        domain[val-1] = true;
        count = 1;
        return retval;
    }

    public void empty() {
        System.arraycopy(falses, 0, domain, 0, 9);
        count = 0;
    }

    public int[] domain() {
        int[] retval = new int[count];
        int i = 0;
        for (int v = 1; v <= 9; v++)
            if (contains(v))
                retval[i++] = v;
        return retval;
    }

    private static class Item {
        public int val, key;
        public Item(int val, int key) {
            this.val = val; this.key = key;
        }
        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            return ((Item) o).val == val;
        }
        @Override
        public String toString() {
            return "Item(v="+val+",key="+key+")";
        }
    }
    private static class Comp implements Comparator<Item> {
        @Override
        public int compare(Item a, Item b) {
            return a.key - b.key;
        }
    }
    private int removeCount(EfficientTracker et, Tuple t, int val) {
        int count = 0;
        for (int row = 1; row <= 9; row++)
            if (row != t.row())
                if (et.domain(row, t.col()).contains(val))
                    count += 1;
        for (int col = 1; col <= 9; col++)
            if (col != t.col())
                if (et.domain(t.row(), col).contains(val))
                    count += 1;
        int row = t.row() - (t.row() - 1) % 3;
        int col = t.col() - (t.col() - 1) % 3;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (row + i != t.row() || col + j != t.col())
                    if (et.domain(row+i, col+j).contains(val))
                        count += 1;
        return count;
    }
    public int[] lcvdomain(EfficientTracker et, Tuple t) {
        int[] dm = domain();
        Item[] tosort = new Item[dm.length];
        for (int i = 0; i < dm.length; i++)
            tosort[i] = new Item(dm[i], removeCount(et, t, dm[i]));
        Arrays.sort(tosort, new Comp());
        int[] sorted = new int[dm.length];
        for (int i = 0; i < dm.length; i++)
            sorted[i] = tosort[i].val;
        return sorted;
    }

    public int count() { return count; }

    public boolean isEmpty() { return count == 0; }

}
