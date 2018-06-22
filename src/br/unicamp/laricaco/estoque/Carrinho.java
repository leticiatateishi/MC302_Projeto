package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.utilidades.LariCACoException;
import br.unicamp.laricaco.usuario.Usuario;

import java.io.*;
import java.util.Date;

public class Carrinho extends TransacaoEstoque {

    public Carrinho(Usuario usuario, GerenciadorEstoque gerenciadorEstoque) {
        super(null, gerenciadorEstoque, usuario, new Date());
    }

    @Override
    public Tipo getTipo() {
        throw new IllegalStateException("Carrinho não deve ser tratado como uma transação pois não foi finalizada.");
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

    @Override
    public void salvar(DataOutputStream outputStream) {
        throw new IllegalStateException("Carrinho não deve ser tratado como uma transação pois não foi finalizada.");
    }
}
