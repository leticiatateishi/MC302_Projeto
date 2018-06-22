package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.*;
import br.unicamp.laricaco.utilidades.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorEstoque implements Salvavel {

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

    public Produto getProduto(String nome) {
        for (Produto produto : produtos) {
            if (produto.getNome().equals(nome))
                return produto;
        }
        return null;
    }

    public Produto getOuCriarProduto(String nome, float precoVenda, float precoCusto, int quantidadePorCaixa) {
        Produto produto = getProduto(nome);
        if (produto == null) {
            try {
                produto = criarProduto(nome, precoVenda, precoCusto, quantidadePorCaixa);
            } catch (LariCACoException e) {
            }
        }
        return produto;
    }

    public Date ultimaReposição() {
        for (int i = 1; i <= transacoes.size(); i++) {
            if (transacoes.get(transacoes.size() - i) instanceof Reposicao) {
                return transacoes.get(transacoes.size() - i).getData();
            }
        }
        return null;
    }

    public Date ultimaCompra() {
        for (int i = 1; i <= transacoes.size(); i++) {
            if (transacoes.get(transacoes.size() - i) instanceof Compra) {
                return transacoes.get(transacoes.size() - i).getData();
            }
        }
        return null;
    }

    public String produtoComMaiorEstoque() {
        ArrayList<Produto> copiaProdutos = new ArrayList<>();
        for (Produto i : produtos) {
            copiaProdutos.add(new Produto(i.gerenciadorEstoque, i.getNome(), i.getPrecoVenda(), i.getPrecoCusto(),
                    i.getQuantidadePorCaixa()));
        }
        Collections.sort(copiaProdutos);
        return copiaProdutos.get(copiaProdutos.size() - 1).getNome();
    }

    public Produto produtoMaisVendido() {
        List<Produto> produtos = new ArrayList<>(this.produtos);
        produtos.sort(new Comparator<Produto>() {
            @Override
            public int compare(Produto produto1, Produto produto2) {
                int quantidadeVendida1 = produto1.getQuantidadeVendida();
                int quantidadeVendida2 = produto2.getQuantidadeVendida();
                return Integer.compare(quantidadeVendida2, quantidadeVendida1);
            }
        });
        return produtos.get(0);
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        /* Salvamos todos os produtos */
        outputStream.writeInt(produtos.size());
        outputStream.flush();
        for (Produto produto : produtos) {
            produto.salvar(outputStream);
        }

        /* Não salvamos transações pois é necessário o objeto usuário. */
    }

    public static GerenciadorEstoque carregar(DataInputStream inputStream) throws IOException {
        GerenciadorEstoque gerenciadorEstoque = new GerenciadorEstoque();

        for (int i = 0; i < inputStream.readInt(); i++) {
            Produto produto = Produto.carregar(gerenciadorEstoque, inputStream);
            gerenciadorEstoque.produtos.add(produto);
        }

        return gerenciadorEstoque;
    }

    /* Carregamos a transação enquanto lemos o usuário e já inserimos na lista de transações do gerenciador de
     * estoque */
    public Transacao carregarTransacao(Usuario usuario, DataInputStream inputStream) throws IOException {
        Transacao transacao = Transacao.carregar(this, usuario, inputStream);
        if (transacao.getTipo().isTransacaoEstoque()) {
            transacoes.add((TransacaoEstoque) transacao);
        }
        return transacao;
    }
}
