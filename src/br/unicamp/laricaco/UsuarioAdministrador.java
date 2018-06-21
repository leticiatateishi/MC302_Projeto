package br.unicamp.laricaco;

import java.util.Date;

public class UsuarioAdministrador extends Usuario {

    public UsuarioAdministrador(Main main, int ra, int pin, String email) {
        super(main, ra, pin, email);
    }

    public Reposicao fazerReposicao() {
        Reposicao reposicao = new Reposicao(main.getGerenciadorEstoque(), this, new Date());
        /* Adicionamos à lista de transações do usuário */
        transacoes.add(reposicao);
        return reposicao;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
