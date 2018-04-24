import java.util.ArrayList;
import java.util.Date;

public class Compra extends TransacaoEstoque {

    public Compra(Usuario usuario, Date data) {
        super(usuario, data);
    }

    @Override
    public double getValor() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
