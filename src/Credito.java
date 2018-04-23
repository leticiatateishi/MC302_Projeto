public class Credito extends Transacao {

    private final double valor;

    public Credito(Usuario usuario, double valor) {
        super(usuario);
        this.valor = valor;
    }

    public double getValor(){
        return valor;
    }
}
