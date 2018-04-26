import java.util.*;

public class Produto {

    private static final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();
    private static final HashSet<Produto> produtos = new HashSet<>();

    private final String nome;
    private float precoVenda, precoCusto;
    private int quantidadePorCaixa;

    public Produto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.quantidadePorCaixa = quantidadePorCaixa;
        produtos.add(this);
    }

    public int getEstoque() {
        int estoque = 0;

        for (TransacaoEstoque transacaoEstoque : transacoes) {
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

    public static void adicionarTransacao(TransacaoEstoque transacaoEstoque) {
        transacoes.add(transacaoEstoque);
    }

    public static Set<Produto> getProdutos() {
        return Collections.unmodifiableSet(produtos);
    }
}
