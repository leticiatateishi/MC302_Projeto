package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.transacoes.*;
import br.unicamp.laricaco.usuario.*;

import java.util.*;

public class GerenciadorEstoque {

    private final ArrayList<TransacaoEstoque> transacoes = new ArrayList<>();
    private final HashSet<Produto> produtos = new HashSet<>();

    /**
     * Cria um produto e o adiciona ao banco de dados.
     *
     * @param nome               nome do produto
     * @param precoVenda         preço de venda do produto
     * @param precoCusto         preço de custo do produto
     * @param quantidadePorCaixa quantidade por caixa de produto
     * @return a instância de produto
     * @throws IllegalArgumentException caso o produto já tenha sido registrado
     */
    public Produto criarProduto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa)
            throws IllegalArgumentException {
        Produto produto = new Produto(nome, precoVenda, precoCusto, quantidadePorCaixa);
        if (!produtos.add(produto)) {
            throw new IllegalArgumentException("Produto já existente!");
        }
        return produto;
    }

    /**
     * Procura o produto no banco a partir do nome.
     *
     * @param nome nome do produto
     * @return o produto
     * @throws NoSuchElementException caso o produto não exista no banco.
     */
    public Produto getProduto(String nome) throws NoSuchElementException {
        return produtos.parallelStream().findFirst().orElseThrow();
    }

    /**
     * Calcula o estoque do produto.
     *
     * @param produto produto pesquisado
     * @return número de itens no estoque
     */
    public int getEstoque(Produto produto) {
        int estoque = 0;

        for (TransacaoEstoque transacaoEstoque : transacoes) {
            if (transacaoEstoque instanceof Compra) {
                estoque -= transacaoEstoque.quantidadeProduto(produto);
            } else if (transacaoEstoque instanceof Reposicao) {
                estoque += transacaoEstoque.quantidadeProduto(produto);
            }
        }

        return estoque;
    }

    /**
     * @return lista de produtos registrados
     */
    public Set<Produto> getProdutos() {
        return Collections.unmodifiableSet(produtos);
    }
}
