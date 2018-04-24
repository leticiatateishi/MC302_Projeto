/**
 * Será utilizado para adicionar estoque em caixas.
 */
public class QuantidadeCaixa extends QuantidadeProduto {

    /**
     * Cria uma instância de "delta quantidade"
     *
     * @param produto    produto a ser comprado/adicionado ao estoque
     * @param quantidade quantidade de produto, a razão principal por essa classe existir como intermediária
     */
    public QuantidadeCaixa(TransacaoEstoque transacaoEstoque, Produto produto, int quantidade) {
        super(transacaoEstoque, produto, quantidade);
    }

    @Override
    public int getQuantidade() {
        return super.getQuantidade() * getProduto().getQuantidadePorCaixa();
    }
}
