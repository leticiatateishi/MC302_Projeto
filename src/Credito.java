import java.util.Date;

public class Credito extends Transacao {

    private final float valor;

    public Credito(Usuario usuario, Date data, float valor) {
        super(usuario, data);
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Credito de R$" + valor + " para " + getUsuario() + "\n" + "Novo Saldo: " + getUsuario().getSaldo() + "\n";
    }
}
