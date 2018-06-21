package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.UsuarioAdministrador;

import java.util.Date;

public class Reposicao extends TransacaoEstoque {

    Reposicao(GerenciadorEstoque gerenciadorEstoque, UsuarioAdministrador usuario, Date data) {
        super(gerenciadorEstoque, usuario, data);
    }

    @Override
    public float getValor(Produto produto) {
        return produtos.getOrDefault(produto, 0) * produto.getPrecoCusto();
    }

    @Override
    public int quantidadeProduto(Produto produto) {
        return super.quantidadeProduto(produto) * produto.getQuantidadePorCaixa();
    }
}
