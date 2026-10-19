# 8-Puzzle — Procura de Custo Uniforme

Resolução do **8-Puzzle** com procura de custo uniforme.

O programa recebe uma configuração inicial e uma configuração objetivo. Imprime a sequência de tabuleiros desde o início até ao objetivo e, no fim, o número de movimentos.

## O puzzle

O tabuleiro tem 3×3 posições, com 8 peças numeradas de 1 a 8 e um espaço vazio.

- Em cada jogada, uma peça adjacente ao espaço vazio desliza para lá.
- O objetivo é chegar a uma configuração final indicada na entrada.

## Formato de entrada e saída

A entrada tem duas linhas: o estado inicial e o estado objetivo. Cada estado tem 9 dígitos, lidos linha a linha, e `0` representa o espaço vazio.

**Exemplo de entrada:**

```
023145678
123405678
```

A saída mostra cada tabuleiro do caminho, separados por uma linha em branco, com o espaço vazio impresso como ` `. A última linha é o número de movimentos.

**Saída:**

```
 23
145
678

123
 45
678

123
4 5
678

2
```

Se não houver solução, é impresso `no solution found`.

## Estrutura do projeto

| Ficheiro | Descrição |
|---|---|
| `Ilayout.java` | Interface de um estado do problema: sucessores (`children`), teste de objetivo (`isGoal`) e custo (`getK`) |
| `Board.java` | Representação do tabuleiro 3×3 e geração de sucessores |
| `BestFirst.java` | Algoritmo de procura |
| `Main.java` | Lê a entrada, corre a procura e imprime o caminho e o custo |
| `PuzzleUnitTests.java` | Testes JUnit 5 da classe `Board` |
| `AssertionExamplesTest.java` | Testes JUnit 5 de exceções e de tempo de execução |

## Abordagem

### Representação do estado (`Board`)

- O tabuleiro é uma matriz `int[3][3]`, construída a partir da string de 9 dígitos.
- `toString` imprime o tabuleiro em 3 linhas, com o `0` como espaço. Usa `System.lineSeparator()` para as mudanças de linha.
- `equals` compara as posições uma a uma, e `hashCode` usa o hash da representação em texto.
- `clone` faz uma *deep copy* da matriz, para que cada sucessor tenha o seu próprio tabuleiro.

### Geração de sucessores (`children`)

O método encontra a posição do espaço vazio e troca-o com cada peça vizinha (esquerda, cima, direita e baixo), desde que esteja dentro do tabuleiro. Cada troca é feita numa cópia através de `cloneAndChange`. Um estado tem por isso entre 2 e 4 sucessores.

### Algoritmo de procura (`BestFirst`)

A procura é de **custo uniforme**: escolhe sempre o estado com menor custo acumulado `g`. Como cada movimento custa 1 (`getK`), a solução encontrada tem o número mínimo de movimentos.

- Os estados abertos ficam numa `PriorityQueue` ordenada por `g`.
- Os estados já expandidos ficam num `HashMap` de fechados, e não voltam a ser explorados.
- Um sucessor só entra na fila se não estiver nos fechados nem já nos abertos.
- Os sucessores que voltam ao estado pai são descartados logo à partida.
- Quando o estado retirado da fila é o objetivo, o caminho é reconstruído seguindo os pais até ao estado inicial.

## Como executar

**Requisitos:** Java 8 ou superior.

```bash
javac Ilayout.java Board.java BestFirst.java Main.java
java Main < sample1.in
```

Para comparar com a saída esperada:

```bash
java Main < sample1.in | diff - sample1.out
```

## Testes

Os testes usam **JUnit 5**. Para os correr no IntelliJ IDEA:

1. Adiciona o JUnit 5 ao projeto. Se `@Test` aparecer a vermelho, Alt+Enter e "Add JUnit 5 to classpath".
2. Clica com o botão direito na classe de testes e escolhe **Run**.

**`PuzzleUnitTests`** testa a classe `Board`:

| Teste | O que verifica |
|---|---|
| `testConstructor`, `testConstructor2` | A string de entrada dá o `toString` correto |
| `testEquals1` a `testEquals4` | Igualdade consigo próprio, entre tabuleiros iguais, com `null` e entre tabuleiros diferentes |
| `testHashCode1`, `testHashCode2` | Tabuleiros iguais têm o mesmo hash e tabuleiros diferentes têm hashes diferentes |
| `testChildren1`, `testChildren2` | Sucessores com o espaço ao centro (4) e num canto (2) |
| `testIsGoal1` | Deteção do estado objetivo |
| `testGetK1` | O custo de um movimento é 1 |

**`AssertionExamplesTest`**:

| Teste | O que verifica |
|---|---|
| `testCloneNotSupportedException` | `clone()` lança exceção numa classe que não implementa `Cloneable` |
| `testEficientSolve` | A procura resolve `sample1` em menos de 1 segundo |

### Exemplos incluídos

| Exemplo | Movimentos |
|---|---|
| `sample1.in` | 2 |
| `sample2.in` | 12 |