package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.utilidades.*;

import java.io.*;
import java.util.List;

public class Produto implements Comparable<Produto>, Salvavel {

    protected final GerenciadorEstoque gerenciadorEstoque;

    private final String nome;
    private float precoVenda, precoCusto;
    private int quantidadePorCaixa;

    Produto(GerenciadorEstoque gerenciadorEstoque,
            String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        this.gerenciadorEstoque = gerenciadorEstoque;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.quantidadePorCaixa = quantidadePorCaixa;
    }

    public int getEstoque() {
        int estoque = 0;

        for (TransacaoEstoque transacaoEstoque : gerenciadorEstoque.getTransacoes(this)) {
            if (transacaoEstoque instanceof Compra) {
                estoque -= transacaoEstoque.quantidadeProduto(this);
            } else if (transacaoEstoque instanceof Reposicao) {
                estoque += transacaoEstoque.quantidadeProduto(this);
            }
        }

        return estoque;
    }

    public int getQuantidadeVendida() {
        List<TransacaoEstoque> transacoes = gerenciadorEstoque.getTransacoes(this);

        return transacoes.stream()
                .filter(transacao -> transacao instanceof Compra)
                .mapToInt(transacao -> transacao.quantidadeProduto(this))
                .sum();
    }

    public String getNome() {
        return nome;
    }

    public float getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(float precoVenda) {
        this.precoVenda = precoVenda;
    }

    public float getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(float precoCusto) {
        this.precoCusto = precoCusto;
    }

    public int getQuantidadePorCaixa() {
        return quantidadePorCaixa;
    }

    public void setQuantidadePorCaixa(int quantidadePorCaixa) {
        this.quantidadePorCaixa = quantidadePorCaixa;
    }

    public boolean isEspecial() {
        return false;
    }

    @Override
    public int hashCode() {
        return nome.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Produto && ((Produto) o).getNome().equals(nome);
    }

    @Override
    public String toString() {
        return "\t* " + getNome() + " (R$" + getPrecoCusto() + "/caixa com " + getQuantidadePorCaixa() + "unidades)"
                + "vendido a preco unitario de R$" + getPrecoVenda() + ". Estoque atual: " + getEstoque() + "\n";
    }

    public int compareTo(Produto produto) {
        if (getEstoque() < produto.getEstoque()) return -1;
        if (getEstoque() > produto.getEstoque()) return 1;
        return 0;
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        outputStream.writeBoolean(isEspecial());
        outputStream.writeUTF(nome);
        outputStream.writeFloat(precoVenda);
        outputStream.writeFloat(precoCusto);
        outputStream.writeInt(quantidadePorCaixa);
        outputStream.flush();
    }

    static Produto carregar(GerenciadorEstoque gerenciadorEstoque, DataInputStream inputStream) throws IOException {
        boolean especial = inputStream.readBoolean();
        String nome = inputStream.readUTF();
        float precoVenda = inputStream.readFloat();
        float precoCusto = inputStream.readFloat();
        int quantidadePorcaixa = inputStream.readInt();

        if (especial) {
            return ProdutoEspecial.carregar(
                    gerenciadorEstoque, inputStream, nome, precoVenda, precoCusto, quantidadePorcaixa);
        } else {
            return new Produto(gerenciadorEstoque, nome, precoVenda, precoCusto, quantidadePorcaixa);
        }
    }
}
