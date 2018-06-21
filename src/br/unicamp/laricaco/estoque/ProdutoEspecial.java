package br.unicamp.laricaco.estoque;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProdutoEspecial extends Produto {

    private final HashSet<Produto> produtoEspecial = new HashSet<>();

    ProdutoEspecial(GerenciadorEstoque gerenciadorEstoque,
                           String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        super(gerenciadorEstoque, nome, precoVenda, precoCusto, quantidadePorCaixa);
    }

    @Override
    public int getEstoque() {
        int estoque = 0;

        for (Produto produto : getVariacoes()) {
            estoque += produto.getEstoque();
        }

        return estoque;
    }

    public Produto adicionarVariacaoProduto(String nome) {
        Produto produto = new Produto(
                gerenciadorEstoque, nome, getPrecoVenda(), getPrecoCusto(), getQuantidadePorCaixa());
        produtoEspecial.add(produto);
        return produto;
    }

    public Set<Produto> getVariacoes() {
        return Collections.unmodifiableSet(produtoEspecial);
    }

    @Override
    public boolean isEspecial() {
        return true;
    }

    @Override
    public String toString() {
        return "Especial: " + getNome() + " (R$" + getPrecoCusto() + "/caixa com " + getQuantidadePorCaixa() +
                "unidades) vendido a preco unitario de R$" + getPrecoVenda() + ". Estoque atual: " + getEstoque() +
                "\n";
    }
}
