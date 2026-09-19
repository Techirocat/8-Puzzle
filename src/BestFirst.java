import java.util.*;

/**
 * Classe que implementa o algoritmo de procura Best-First
 */
class BestFirst {
    protected Queue<State> abertos;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    static class State {
        private Ilayout layout;
        private State father;
        private double g;

        /**
         * Construtor da classe State
         * @param l O layout do estado atual
         * @param n O estado pai (pode ser null para o estado inicial)
         */
        public State(Ilayout l, State n) {
            layout = l;
            father = n;
            if (father != null)
                g = father.g + l.getK();
            else g = 0.0;
        }

        /**
         * Retorna a representação em texto do estado
         * @return String representando o layout
         */
        public String toString() {
            return layout.toString();
        }

        /**
         * Retorna o custo acumulado do estado
         * @return O valor do custo g
         */
        public double getG() {
            return g;
        }

        /**
         * Calcula o código hash para o estado
         * @return O valor de hash
         */
        public int hashCode() {
            return toString().hashCode();
        }

        /**
         * Compara se dois estados são iguais, baseando-se no seu layout
         * @param o O objeto a ser comparado
         * @return true se os estados forem iguais, false caso contrário
         */
        public boolean equals(Object o) {
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    /**
     * Gera e retorna a lista de estados sucessores para um determinado estado
     * @param n O estado atual a partir do qual os sucessores são gerados
     * @return Uma lista de objetos State que são filhos do estado atual
     */
    final private List<State> sucessores(State n) {
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for (Ilayout e : children) {
            if (n.father == null || !e.equals(n.father.layout)) {
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    /**
     * Executa o algoritmo de procura para encontrar o caminho do estado inicial até ao objetivo
     * @param s O layout do estado inicial
     * @param goal O layout do estado objetivo
     * @return Um iterador sobre a lista de estados que formam o caminho da solução ou null se não for encontrada nenhuma solução
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal) {
        objective = goal;

        // Elementos organizados por menor valor de g
        abertos = new PriorityQueue<>(10, (s1, s2) -> (int) Math.signum(s1.getG() - s2.getG()));
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;

        while (!abertos.isEmpty()) {
            actual = abertos.poll();

            if (actual.layout.isGoal(objective)) {
                List<State> path = new ArrayList<>();
                State nn = actual;
                while (nn != null) {
                    path.add(nn);
                    nn = nn.father;
                }

                Collections.reverse(path);
                return path.iterator();
            }

            sucs = sucessores(actual);
            fechados.put(actual.layout, actual);

            for (State st : sucs) {
                if(!fechados.containsKey(st.layout) && !abertos.contains(st)) {
                    abertos.add(st);
                }
            }
        }

        return null;
    }
}
