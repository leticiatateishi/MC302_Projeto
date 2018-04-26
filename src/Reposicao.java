import java.util.Date;

/**
 * Classe que representa a adição de itens ao estoque.
 */
public class Reposicao extends TransacaoEstoque {

    /**
     * Transação que altera estoque de Produto automaticamente.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     */
    public Reposicao(UsuarioAdministrador usuario, Date data) {
        super(usuario, data);
    }
}
