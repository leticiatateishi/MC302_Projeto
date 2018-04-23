import java.util.ArrayList;
import java.util.HashSet;

public class Usuario {

    private final int ra;
    private int pin;
    private String email;
    private double saldo;
    private int esqueciSenha;
    private ArrayList<Transacao> transacoes;
    private static HashSet<Usuario> usuarios;

    public Usuario(int ra, int pin, String email){
        this.ra = ra;
        this.pin = pin;
        this.email = email;
        transacoes = new ArrayList<>();
        usuarios = new HashSet<>();
    }

    public void pedirTrocaSenha(){}

    public void alterarSenha(int codigo){}

    public void Creditar(double valor){}

    public void Debitar(double valor){}




}
