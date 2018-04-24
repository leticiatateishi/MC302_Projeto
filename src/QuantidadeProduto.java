/**
 * Classe intermadiária das relações:
 * <p>
 * - Entre Compra e Produto:
 * É utilizado de forma unidirecional, no sentido de que não está atrelado à compra, apenas é utilizado por ela.
 * <p>
 * - Estoque em Produto
 * É utilizado também de forma unidirecional para cálculo de estoque. Após a finalização da compra ou da reposição,
 * deverá ser adicionado à lista em Produto.
 * <p>
 * Essa classe pode ser utilizada como uma intermediária bidirecional se criarmos uma herdeira e guardarmos a variável
 * Compra ou Produto de modo final. Teríamos que escrever uma CompraDeEstoque, por exemplo, para diferenciar o que irá
 * decrescer no estoque de produtos e o que irá acrescentar ao estoque.
 * <p>
 * Os setters não devem existir para uma compra já finalizada. Talvez criar uma versão final dessa classe ou ainda uma
 * em que os setters usam "throw" para impedir o usuário do código de utilizar esses métodos.
 */
public class QuantidadeProduto {

    private final TransacaoEstoque transacaoEstoque;
    private Produto produto;
    private int quantidade;

    /**
     * Cria uma instância de "delta quantidade"
     *
     * @param transacaoEstoque transação referente à essa mudança de quantidade de produtos.
     * @param produto          produto a ser comprado/adicionado ao estoque
     * @param quantidade       quantidade de produto, a razão principal por essa classe existir como intermediária
     */
    public QuantidadeProduto(TransacaoEstoque transacaoEstoque, Produto produto, int quantidade) {
        this.transacaoEstoque = transacaoEstoque;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public TransacaoEstoque getTransacaoEstoque() {
        return transacaoEstoque;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
