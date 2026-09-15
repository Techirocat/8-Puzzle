import java.util.List;

class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int board[][];

    public Board() {
        board = new int[dim][dim];
    }

    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    public String toString() {
        // TODO: Three rows, 0 as a space, newline after each row.
        throw new UnsupportedOperationException("TODO: toString");
    }

    public boolean equals(Object o) {
        // TODO: Compare tile positions; handle null/other types.
        throw new UnsupportedOperationException("TODO: equals");
    }

    public int hashCode() {
        // TODO: Equal boards must have equal hash codes.
        throw new UnsupportedOperationException("TODO: hashCode");
    }

    // These three stubs make explicit the methods required by Ilayout.
    public List<Ilayout> children() {
        // TODO: Make independent boards for all legal moves.
        throw new UnsupportedOperationException("TODO: children");
    }

    public boolean isGoal(Ilayout l) {
        // TODO: Compare this configuration with l.
        throw new UnsupportedOperationException("TODO: isGoal");
    }

    public double getK() {
        // TODO: Return the cost of one move.
        throw new UnsupportedOperationException("TODO: getK");
    }
}
