package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.utilidades.LariCACoException;
import br.unicamp.laricaco.usuario.Usuario;

import java.io.*;
import java.util.*;

public abstract class TransacaoEstoque extends Transacao {

    final GerenciadorEstoque gerenciadorEstoque;

    final HashMap<Produto, Integer> produtos = new HashMap<>();

    TransacaoEstoque(Tipo tipo, GerenciadorEstoque gerenciadorEstoque, Usuario usuario, Date data) {
        super(tipo, usuario, data);
        this.gerenciadorEstoque = gerenciadorEstoque;
    }

    public abstract float getValor(Produto produto);

    @Override
    public float getValor() {
        float valor = 0.0f;

        for (Produto produto : gerenciadorEstoque.getProdutos()) {
            if (produto.isEspecial()) {
                for (Produto p : ((ProdutoEspecial) produto).getVariacoes()) {
                    valor += getValor(p);
                }
            } else {
                valor += getValor(produto);
            }
        }

        return valor;
    }

    public void adicionarProduto(Produto produto, int quantidade) throws LariCACoException {
        if (quantidade <= 0) {
            throw new LariCACoException("A quantidade resultante não deve ser negativa!");
        }
        if (produto.isEspecial()) {
            throw new LariCACoException("Produto Especial nao pode ser comprado!");
        }
        produtos.put(produto, quantidade + produtos.getOrDefault(produto, 0));
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

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        super.salvar(outputStream);
        outputStream.writeInt(produtos.size());
        for (Map.Entry<Produto, Integer> entry : produtos.entrySet()) {
            outputStream.writeUTF(entry.getKey().getNome());
            outputStream.writeInt(entry.getValue());
        }
    }
}
