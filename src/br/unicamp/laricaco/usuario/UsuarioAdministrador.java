package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.Main;
import br.unicamp.laricaco.estoque.Reposicao;

import java.util.Date;

public class UsuarioAdministrador extends Usuario {

    UsuarioAdministrador(Main main, int ra, int pin, String pergunta, String resposta) {
        super(main, ra, pin, pergunta, resposta);
    }

    public Reposicao fazerReposicao() {
        Reposicao reposicao = main.getGerenciadorEstoque().criarReposicao(this, new Date());
        /* Adicionamos à lista de transações do usuário */
        transacoes.add(reposicao);
        return reposicao;
    }

    @Override
    public boolean isAdministrador() {
        return true;
    }
}
