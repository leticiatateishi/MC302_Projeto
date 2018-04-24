public class Credito extends Transacao {

    private final double valor;

    public Credito(Usuario usuario, double valor) {
        super(usuario);
        this.valor = valor;
    }

    public double getValor(){
        return valor;
    }

    @Override
    public String toString() {
        return "Credito de R$" + valor + " para " + getUsuario() + "\n" + "Novo Saldo: " + getUsuario().getSaldo() +"\n";
    }
}
