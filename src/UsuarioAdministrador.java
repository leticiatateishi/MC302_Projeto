import java.util.Date;

public class UsuarioAdministrador extends Usuario {

    public UsuarioAdministrador(int ra, int pin, String email) {
        super(ra, pin, email);
    }

    public Reposicao fazerReposicao() {
        Reposicao reposicao = new Reposicao(this, new Date());
        /* Adicionamos à lista de transações do usuário */
        transacoes.add(reposicao);
        return reposicao;
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
