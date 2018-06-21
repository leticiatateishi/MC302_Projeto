package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.LariCACoException;
import br.unicamp.laricaco.usuario.UsuarioAdministrador;

import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorEstoque {

    private final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();
    private final HashSet<Produto> produtos = new HashSet<>();

    public List<TransacaoEstoque> getTransacoes(Produto produto) {
        /* Pegamos a lista de transações que envolvem esse produto */
        return Collections.unmodifiableList(transacoes.stream()
                .filter(transacao -> transacao.quantidadeProduto(produto) > 0)
                .collect(Collectors.toList()));
    }

    public Produto criarProduto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa)
            throws LariCACoException {
        Produto produto = new Produto(this, nome, precoVenda, precoCusto, quantidadePorCaixa);

        if (!produtos.add(produto)) {
            throw new LariCACoException("Produto já existente!");
        }

        return produto;
    }

    public ProdutoEspecial criarProdutoEspecial(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa)
            throws LariCACoException {
        ProdutoEspecial produtoEspecial = new ProdutoEspecial(this, nome, precoVenda, precoCusto, quantidadePorCaixa);

        if (!produtos.add(produtoEspecial)) {
            throw new LariCACoException("Produto já existente!");
        }

        return produtoEspecial;
    }

    public Set<Produto> getProdutos() {
        /* Os produtos especiais devem ser decompostos */
        return Collections.unmodifiableSet(produtos);
    }

    public Compra criarCompra(Carrinho carrinho) throws LariCACoException {
        Compra compra = new Compra(this, carrinho.getUsuario(), new Date());
        transacoes.add(compra);
        try {
            for (Map.Entry<Produto, Integer> entry : carrinho.produtos.entrySet()) {
                compra.adicionarProduto(entry.getKey(), entry.getValue());
            }
        } catch (LariCACoException e) {
            /* Caso tenha falhado, não levamos a compra em consideração */
            transacoes.remove(compra);
            throw e;
        }
        carrinho.getUsuario().addCompra(compra);
        return compra;
    }

    public Reposicao criarReposicao(UsuarioAdministrador administrador, Date date) {
        Reposicao reposicao = new Reposicao(this, administrador, date);
        transacoes.add(reposicao);
        return reposicao;
    }
}
