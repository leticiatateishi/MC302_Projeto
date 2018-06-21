package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.LariCACoException;
import br.unicamp.laricaco.usuario.Usuario;

import java.util.Date;

public class Carrinho extends TransacaoEstoque {

    private final Usuario usuario;

    public Carrinho(Usuario usuario, GerenciadorEstoque gerenciadorEstoque) {
        super(gerenciadorEstoque, usuario, new Date());
        this.usuario = usuario;
    }

    @Override
    public float getValor(Produto produto) {
        return produtos.getOrDefault(produto, 0) * produto.getPrecoVenda();
    }

    @Override
    public void adicionarProduto(Produto produto, int quantidade) throws LariCACoException {
        /* Essa transação em estoque NÃO  */
        if ((produtos.getOrDefault(produto, 0) + quantidade) > produto.getEstoque()) {
            throw new LariCACoException("A quantidade desejada é maior que o estoque!");
        }
        super.adicionarProduto(produto, quantidade);
    }

    public void esvaziarCarrinho() {
        produtos.clear();
    }

    public Compra finalizarCompra() throws LariCACoException {
        Compra compra = gerenciadorEstoque.criarCompra(this);
        esvaziarCarrinho();
        return compra;
    }
}
