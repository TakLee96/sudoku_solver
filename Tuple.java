public class Tuple {
    private int r, c;
    public Tuple(int r, int c) {
        this.r = r; this.c = c;
    }

    public int row() { return r; }
    public int col() { return c; }

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
}
