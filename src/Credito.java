import java.util.Date;

public class Credito extends Transacao {

    private final double valor;

    public Credito(Usuario usuario, Date data, double valor) {
        super(usuario, data);
        this.valor = valor;
    }

    public double getValor() {
        return valor;
    }
}
