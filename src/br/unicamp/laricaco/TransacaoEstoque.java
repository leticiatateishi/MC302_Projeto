package br.unicamp.laricaco;

import java.util.Date;
import java.util.HashMap;

/**
 * Classe intermediária entre Transacao e Produtos, permitindo uma relação bidirecional entre usuário e produtos.
 */
/*
 * Essa classe dispensa QuantidadeProdutos, já que não temos uma relação muito complexa para precisar de mais de uma
 * variável (inteiro quantidade).
 */
public abstract class TransacaoEstoque extends Transacao {

    protected final GerenciadorEstoque gerenciadorEstoque;

    protected final HashMap<Produto, Integer> produtos = new HashMap<>();

    public TransacaoEstoque(GerenciadorEstoque gerenciadorEstoque, Usuario usuario, Date data) {
        super(usuario, data);
        this.gerenciadorEstoque = gerenciadorEstoque;
    }

    public abstract float getValor(Produto produto);

    @Override
    public float getValor() {
        float valor = 0.0f;

        for (Produto produto : gerenciadorEstoque.getProdutos()) {
            valor += getValor(produto);
        }

        return valor;
    }

    public boolean adicionarProduto(Produto produto, int quantidade) {
        if (produto.isEspecial() || quantidade <= 0) {
            return false;
        }
        produtos.put(produto, quantidade + produtos.getOrDefault(produto, 0));
        return true;
    }

    public boolean removerProduto(Produto produto, int quantidade) {
        if (quantidade > 0 && produtos.getOrDefault(produto, 0) >= quantidade) {
            produtos.put(produto, produtos.get(produto) - quantidade);
            return true;
        }
        return false;
    }

    public int quantidadeProduto(Produto produto) {
        if (produto.isEspecial()) {
            return 0;
        }
        return produtos.getOrDefault(produto, 0);
    }
}
