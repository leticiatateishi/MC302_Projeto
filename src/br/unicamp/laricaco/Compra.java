package br.unicamp.laricaco;

import java.util.Date;

/**
 * Classe que representa a transação de comprar produtos a partir do estoque.
 */
public class Compra extends TransacaoEstoque {

    /**
     * Transação que altera estoque de Produto automaticamente.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     */
    public Compra(GerenciadorEstoque gerenciadorEstoque, Usuario usuario, Date data) {
        super(gerenciadorEstoque, usuario, data);
    }

    @Override
    public boolean adicionarProduto(Produto produto, int quantidade) {
        return quantidade <= produto.getEstoque() && super.adicionarProduto(produto, quantidade);
    }

    @Override
    public float getValor(Produto produto) {
        return produtos.getOrDefault(produto, 0) * produto.getPrecoVenda();
    }

    @Override
    public String toString() {
        return "Compra realizada na data " + getData() + " por " + getUsuario() + " no valor de R$" + getValor();
    }
}
