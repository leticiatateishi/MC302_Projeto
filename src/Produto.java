import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe que representa uma lista fixa de produtos.
 */
public enum Produto {

    TRENTO(1.25, 16),
    PACOCA(0.25, 50),
    PIRULITO(0.25, 60),
    AMENDOIM(0.5, 50);

    /**
     * Lista de transações de quantidade de produtos para cada produto. Nos permitirá controlar o estoque.
     * <p>
     * Cada compra possui uma lista de quantidade de itens, isso que nos ajudará a calcular o estoque. A relação é
     * bidirecional, então conseguimos a partir da transação, chegar aos usuários que compraram tal produto.
     */
    private static final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();

    private final double preco;
    private final int quantidadePorCaixa;

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

    /**
     * Filtra todas as transações que envolvem este produto.
     *
     * @return a lista de transações que alteram a quantidade desse produto.
     * @see Collections#unmodifiableList(List) lista não modificável
     */
    public List<TransacaoEstoque> getTransacoes() {
        ArrayList<TransacaoEstoque> transacoesProduto = new ArrayList<>();

        for (TransacaoEstoque transacaoEstoque : transacoes) {
            if (transacaoEstoque.quantidadeProduto(this) > 0) {
                transacoesProduto.add(transacaoEstoque);
            }
        }

        return Collections.unmodifiableList(transacoesProduto);
    }

    /**
     * Calcula o estoque a partir do histórico de transações.
     *
     * @return o estoque deste produto
     */
    public int getEstoque() {
        int estoque = 0;

        for (TransacaoEstoque transacaoEstoque : getTransacoes()) {
            if (transacaoEstoque instanceof Compra) {
                estoque -= transacaoEstoque.quantidadeProduto(this);
            } else {
                estoque += transacaoEstoque.quantidadeProduto(this);
            }
        }

        return estoque;
    }

    @Override
    public String toString() {
        return name() + " (R$" + getPreco() + ", " + getQuantidadePorCaixa() + " unidades por caixa)";
    }

    /**
     * Método que deve ser usado apenas por TransacaoEstoque para adicionar à lista de transações dos Produtos.
     *
     * @param transacaoEstoque transação que modifica produtos
     */
    protected static void adicionarTransacao(TransacaoEstoque transacaoEstoque) {
        transacoes.add(transacaoEstoque);
    }

    /**
     * Lista não modificável (ponteiro apenas para vizsalização) de transações.
     *
     * @return uma lista não modificável de transações.
     * @see Collections#unmodifiableList(List) lista não modificável
     */
    public static List<TransacaoEstoque> getTodasTransacoes() {
        return Collections.unmodifiableList(transacoes);
    }
}
