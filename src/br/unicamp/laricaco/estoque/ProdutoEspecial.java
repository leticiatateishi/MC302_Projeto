package br.unicamp.laricaco.estoque;

import java.io.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ProdutoEspecial extends Produto {

    private final HashSet<Produto> variacoes = new HashSet<>();

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
                gerenciadorEstoque, getNome() + " " + nome, getPrecoVenda(), getPrecoCusto(), getQuantidadePorCaixa());
        variacoes.add(produto);
        return produto;
    }

    public Set<Produto> getVariacoes() {
        return Collections.unmodifiableSet(variacoes);
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

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        super.salvar(outputStream);
        outputStream.writeInt(variacoes.size());
        outputStream.flush();
        for (Produto variacao : variacoes) {
            variacao.salvar(outputStream);
        }
    }

    static ProdutoEspecial carregar(GerenciadorEstoque gerenciadorEstoque, DataInputStream inputStream,
                                    String nome, float precoVenda, float precoCusto, int quantidadePorcaixa)
            throws IOException {
        ProdutoEspecial produtoEspecial = new ProdutoEspecial(
                gerenciadorEstoque, nome, precoVenda, precoCusto, quantidadePorcaixa);

        /* Carregamos variações */
        for (int i = 0; i < inputStream.readInt(); i++) {
            produtoEspecial.variacoes.add(Produto.carregar(gerenciadorEstoque, inputStream));
        }

        return produtoEspecial;
    }
}
