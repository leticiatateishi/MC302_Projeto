package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.Usuario;

import java.io.*;
import java.util.Date;

public class Credito extends Transacao {

    private final float valor;

    public Credito(Usuario usuario, Date data, float valor) {
        super(Tipo.CREDITO, usuario, data);
        this.valor = valor;
    }

    public float getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Credito de R$" + valor + " para " + getUsuario() + "\n" + "Novo Saldo: " + getUsuario().getSaldo() + "\n";
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        super.salvar(outputStream);
        outputStream.writeFloat(valor);
        outputStream.flush();
    }
}
