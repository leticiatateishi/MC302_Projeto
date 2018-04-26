import java.util.*;

public class ProdutoEspecial extends Produto {

    private final HashSet<Produto> produtoEspecial = new HashSet<>();

    public ProdutoEspecial(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        super(nome, precoVenda, precoCusto, quantidadePorCaixa);
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
        Produto produto = new Produto(nome, getPrecoVenda(), getPrecoCusto(), getQuantidadePorCaixa());
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
