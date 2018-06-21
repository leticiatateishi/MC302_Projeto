package br.unicamp.laricaco.estoque;

public class Produto {

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
}
