/* YOU SHOULD READ THIS FILE */

public class Triple extends Tuple {
    private int v;
    public Triple(int r, int c, int v) {
        super(r, c); this.v = v;
    }
    public Triple(Tuple t, int v) {
        super(t); this.v = v;
    }

    public int val() { return v; }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        Triple o = (Triple) other;
        return this.row() == o.row() && this.col() == o.col() && this.v == o.v;
    }

    @Override
    public int hashCode() {
        return row() * 10201 + col() * 101 + v;
    }

    @Override
    public String toString() {
        return "Triple(row=" + row() + ",col=" + col() + ",val=" + v + ")";
    }

    public Tuple toTuple() {
        return new Tuple(row(), col());
    }
}
