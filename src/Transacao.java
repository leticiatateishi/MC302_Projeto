/**
 * Classe abstrata que representa uma transação:
 * Uma transação permite calcular a diferença em reais no saldo de algum usuário.
 */
public abstract class Transacao {

    /**
     * Permite a relação bidirecional com a transação.
     */
    private final Usuario usuario;

    protected Transacao(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Calcula, a partir das informações da transação, seu valor total.
     *
     * @return o valor total da transação em reais.
     */
    public abstract double getValor();
}
