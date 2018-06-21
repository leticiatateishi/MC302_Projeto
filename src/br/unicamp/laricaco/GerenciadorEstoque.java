package br.unicamp.laricaco;

import java.util.*;
import java.util.stream.*;

public class GerenciadorEstoque {

    private final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();
    private final HashSet<Produto> produtos = new HashSet<>();

    public void adicionarTransacao(TransacaoEstoque transacaoEstoque) {
        transacoes.add(transacaoEstoque);
    }

    public List<TransacaoEstoque> getTransacoes(Produto produto) {
        /* Pegamos a lista de transações que envolvem esse produto */
        return transacoes.stream()
                .filter(transacao -> transacao.quantidadeProduto(produto) > 0)
                .collect(Collectors.toUnmodifiableList());
    }

    public Produto criarProduto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa)
            throws LariCACoException {
        Produto produto = new Produto(this, nome, precoVenda, precoCusto, quantidadePorCaixa);

        if (!produtos.add(produto)) {
            throw new LariCACoException("Produto já existente!");
        }

        return produto;
    }

    public Set<Produto> getProdutos() {
        return Collections.unmodifiableSet(produtos);
    }
}
