import java.util.ArrayList;

/**
 * Classe que representa uma lista fixa de produtos.
 */
public enum Produto {

    TRENTO(1.25, 16),
    PACOCA(0.25, 50),
    PIRULITO(0.25, 60);

    private final double preco;
    private final int quantidadePorCaixa;

    /**
     * Lista de transações de quantidade de produtos para cada produto. Nos permitirá controlar o estoque.
     * Cada compra possui algumas QuantidadeProduto que deverão estar aqui também. Podemos tornar essa relação
     * bidirecional se QuantidadeProduto guardar Compra, porém isso não será válido para reposição de estoque.
     *
     * Para isso, podemos também formar uma espécie de "Compra" para "Reposição", como uma classe que extende uma
     * Transacao, algo como "TransacaoEstoque" e não apenas uma transação monetária.
     */
    private static final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();

    Produto(double preco, int quantidadePorCaixa) {
        this.preco = preco;
        this.quantidadePorCaixa = quantidadePorCaixa;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidadePorCaixa() {
        return quantidadePorCaixa;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Método que deve ser usado apenas por TransacaoEstoque para adicionar à
     * @param transacaoEstoque
     */
    protected static void adicionarTransacao(TransacaoEstoque transacaoEstoque) {
        transacoes.add(transacaoEstoque);
    }
}
