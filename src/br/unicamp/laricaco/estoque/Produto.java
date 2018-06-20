package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.transacoes.*;

import java.util.*;

public class Produto {

    private final String nome;
    private float precoVenda, precoCusto;
    private int quantidadePorCaixa;

    Produto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.quantidadePorCaixa = quantidadePorCaixa;
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
    public boolean equals(Object object) {
        return object instanceof Produto && ((Produto) object).getNome().equals(nome);
    }

    @Override
    public String toString() {
        return "\t* " + getNome() + " (R$" + getPrecoCusto() + "/caixa com " + getQuantidadePorCaixa() + "unidades)"
                + "vendido a preco unitario de R$" + getPrecoVenda() + "\n";
    }
}
