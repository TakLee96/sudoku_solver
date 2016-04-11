/* YOU SHOULD READ THIS FILE */

public class Tuple {
    private int r, c;
    public Tuple(int r, int c) {
        this.r = r; this.c = c;
    }

    public int row() { return r; }
    public int col() { return c; }

    public Tuple next() {
        int count = (r - 1) * 9 + c;
        int col = count % 9 + 1;
        int row = count / 9 + 1;
        if (row > 9) return null;
        return new Tuple(row, col);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        Tuple o = (Tuple) other;
        return this.r == o.r && this.c == o.c;
    }

    @Override
    public int hashCode() {
        return r * 1023 + c;
    }

    @Override
    public String toString() {
        return "Tuple(row=" + r + ",col=" + c + ")";
    }
}
