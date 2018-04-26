import java.util.Date;

/**
 * Classe que representa a adição de itens ao estoque.
 */
public class Reposicao extends TransacaoEstoque {

    /**
     * Transação que altera estoque de Produto automaticamente.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     */
    public Reposicao(Usuario usuario, Date data) {
        super(usuario, data);
    }

    public String toString() {
        return super.toString();
    }

    @Override
    public double getValor(Produto produto) {
        return produtos.getOrDefault(produto, 0) * produto.getPrecoCusto();
    }

    @Override
    public int quantidadeProduto(Produto produto) {
        return super.quantidadeProduto(produto)*produto.getQntCaixa();
    }
}
