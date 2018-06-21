package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.LariCACoException;
import br.unicamp.laricaco.usuario.Usuario;

import java.util.Date;
import java.util.HashMap;

public abstract class TransacaoEstoque extends Transacao {

    final GerenciadorEstoque gerenciadorEstoque;

    final HashMap<Produto, Integer> produtos = new HashMap<>();

    TransacaoEstoque(GerenciadorEstoque gerenciadorEstoque, Usuario usuario, Date data) {
        super(usuario, data);
        this.gerenciadorEstoque = gerenciadorEstoque;
    }

    public abstract float getValor(Produto produto);

    @Override
    public float getValor() {
        float valor = 0.0f;

        for (Produto produto : gerenciadorEstoque.getProdutos()) {
            if (produto.isEspecial()) {
                for(Produto p: ((ProdutoEspecial)produto).getVariacoes()){
                    valor += getValor(p);
                }
            }else{
                valor += getValor(produto);
            }
        }

        return valor;
    }

    public void adicionarProduto(Produto produto, int quantidade) throws LariCACoException {
        if (quantidade <= 0) {
            throw new LariCACoException("A quantidade resultante não deve ser negativa!");
        }
        if(produto.isEspecial()){
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
}
