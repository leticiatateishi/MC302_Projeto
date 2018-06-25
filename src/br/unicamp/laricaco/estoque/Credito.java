package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.Usuario;

import java.io.*;
import java.util.Date;

public class Credito extends Transacao {

    private final float valor;
    private final MetodoPagamento metodoPagamento;

    public Credito(Usuario usuario, Date data, float valor, MetodoPagamento metodoPagamento) {
        super(Tipo.CREDITO, usuario, data);
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
    }

    public float getValor() {
        return valor;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    @Override
    public String toString() {
        return "Credito de R$" + valor + " para " + getUsuario() + "\n" + "Novo Saldo: " + getUsuario().getSaldo() + "\n";
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        super.salvar(outputStream);
        outputStream.writeFloat(valor);
        outputStream.writeInt(metodoPagamento.ordinal());
        outputStream.flush();
    }
}
