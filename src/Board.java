import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe que representa o estado de um tabuleiro
 */
class Board implements Ilayout, Cloneable {
    private static final int dim = 3;
    private int[][] board;

    /**
     * Construtor que inicializa um tabuleiro vazio
     */
    public Board() {
        board = new int[dim][dim];
    }

    /**
     * Construtor que inicializa o tabuleiro a partir de uma String
     * @param str String com números que vão preencher o tabuleiro
     * @throws IllegalStateException Se o tamanho da string não for o esperado
     */
    public Board(String str) throws IllegalStateException {
        if (str.length() != dim * dim)
            throw new IllegalStateException("Invalid arg in Board constructor");
        board = new int[dim][dim];
        int si = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                board[i][j] = Character.getNumericValue(str.charAt(si++));
    }

    /**
     * Retorna a representação textual do tabuleiro
     * @return Uma string formatada
     */
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
     * Verefica se este tabuleiro é igual a outro
     * @see "https://www.baeldung.com/java-equals-hashcode-contracts"
     * @param o   the reference object with which to compare.
     * @return true se os tabuleiros forem iguais, false caso contrário
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

    /**
     *
     * @return
     */
    public int hashCode() {
        return toString().hashCode();
    }

    /**
     * Gera uma lista dos estados sucessores válidos
     * @return Uma lista de Layouts que representam os próximos estados
     */
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

        if (x - 1 >= 0) children.add(cloneAndChange(x , y, x - 1, y)); // Esquerda
        if (y - 1 >= 0) children.add(cloneAndChange(x , y, x, y - 1)); // Cima
        if  (x + 1 < dim) children.add(cloneAndChange(x , y, x + 1, y)); // Direita
        if (y + 1 < dim) children.add(cloneAndChange(x , y, x, y + 1)); // Baixo

        return children;
    }

    /**
     * Verifica se o estado atual do trabuleiro corresponde ao estado objetivo
     * @param l O layout do estado objetivo a comparar
     * @return true se o tabuleiro for igual ao objetivo, falso caso contrário
     */
    public boolean isGoal(Ilayout l) {
        return this.equals(l);
    }

    /**
     * Retorna o custo para efetuar uma transição
     * @return O custo de movimento
     */
    public double getK() {
        return 1.0;
    }

    /**
     * Realiza um deep copy do tabuleiro
     * @see "https://www.geeksforgeeks.org/java/cloneable-interface-in-java/"
     * @return Um novo tabuleiro
     * @throws CloneNotSupportedException Se a clonagem não for possível
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
     * Cria um clone do tabuleiro atual e troca as posições do (0) e a peça destino
     * @param xO - X da posição atual do elemento 0
     * @param yO - Y da posição atual do elemento 0
     * @param xD - X da posição de destino do elemento 0
     * @param yD - Y da posição de destino do elemento 0
     * @return - retorna um novo Board com as posições trocadas
     */
    private Board cloneAndChange (int xO, int yO, int xD, int yD) {
        if (xO < 0 || yO < 0 || xD < 0 || yD < 0 || xD > dim - 1 || yD > dim - 1 ||  xO > dim - 1 || yO > dim - 1) {
            throw new IllegalStateException("Argumentos Inválidos");
        }

        try{
            Board board = (Board) this.clone();

            board.board[yO][xO] = board.board[yD][xD];
            board.board[yD][xD] = 0;

            return board;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

    }
}
