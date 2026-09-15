import java.util.ArrayList;
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
        String  str = "";
        for(int i = 0; i < dim; i ++){
            for (int j = 0; j < dim; j++){
                if (board[i][j] == 0){
                    str += " ";
                }else{
                    str += board[i][j];
                }
            }
            str += System.lineSeparator(); // Usar isto em vex de \n, pois o prof pede
        }

        return str;
    }

    /**
     *
     * @see "https://www.baeldung.com/java-equals-hashcode-contracts"
     * @param o   the reference object with which to compare.
     * @return
     */
    public boolean equals(Object o) {
        if(o == this){
            return true;
        }else if (!(o instanceof Board)) {
            return false;
        }

        Board b = (Board) o;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] != b.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int hashCode() {
        int hash = 1;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                hash *= 10;
                hash += board[i][j];
            }
        }

        return hash;
    }

    // These three stubs make explicit the methods required by Ilayout.
    public List<Ilayout> children() {
        int x = 0, y = 0;
        boolean found = false;

        for  (int i = 0; i < dim; i++){
            for  (int j = 0; j < dim; j++){
                if (board[i][j] == 0){
                    x = j;
                    y = i;
                    found = true;
                    break;
                }
            }
            if (found) break;
        }

        List<Ilayout> children = new ArrayList<Ilayout>();
        
        try{
            Board b = (Board) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        // Mover para esquerda
        if (x - 1 >= 0){
            children.add(cloneAndChange(x , y, x - 1, y));
        }

        // Mover para cima
        if (y - 1 >= 0) {
            children.add(cloneAndChange(x , y, x, y - 1));
        }

        // Mover para a direita
        if  (x + 1 < dim) {
            children.add(cloneAndChange(x , y, x + 1, y));
        }

        // Mover para baixo
        if (y + 1 < dim) {
            children.add(cloneAndChange(x , y, x, y + 1));
        }

        return children;
    }

    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    public double getK() {
        return 1.0;
    }

    /**
     * @see "https://www.geeksforgeeks.org/java/cloneable-interface-in-java/"
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Board board = (Board) super.clone();

        board.board = new int[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                board.board[i][j] = this.board[i][j];
            }
        }

        return board;
    }

    /**
     *
     * @param xO - X da posição atual do elemento 0
     * @param yO - Y da posição atual do elemento 0
     * @param xD - X da posição de destino do elemento 0
     * @param yD - Y da posição de destino do elemento 0
     * @return - retorna um novo Board com as posições trocadas
     */
    private Board cloneAndChange (int xO, int yO, int xD, int yD) {

        try{
            Board board = (Board) this.clone();

            board.board[xO][yO] = board.board[xD][yD];
            board.board[xD][yD] = 0;

            return board;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
}
