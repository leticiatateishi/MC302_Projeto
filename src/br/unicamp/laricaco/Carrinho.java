package br.unicamp.laricaco;

import java.util.ArrayList;

public class Carrinho {

    private ArrayList<Produto> carrinho;

    public Carrinho(){
        carrinho = new ArrayList<>();
    }

    public void adicionarProduto(Produto produto){
        carrinho.add(produto);
    }
    public void esvaziarCarrinho(){
        this.carrinho.clear();
    }

}
