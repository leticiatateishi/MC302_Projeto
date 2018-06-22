package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.*;
import br.unicamp.laricaco.utilidades.*;

import java.io.*;
import java.util.Date;

public abstract class Transacao implements Salvavel, Comparable<Transacao> {

    private final Tipo tipo;
    private final Usuario usuario;
    private final Date data;

    Transacao(Tipo tipo, Usuario usuario, Date data) {
        this.tipo = tipo;
        this.usuario = usuario;
        this.data = data;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getData() {
        return data;
    }

    public abstract float getValor();

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(tipo.ordinal());
        outputStream.writeLong(data.getTime());
    }

    static Transacao carregar(GerenciadorEstoque gerenciadorEstoque, Usuario usuario, DataInputStream inputStream)
            throws IOException {
        Tipo tipo = Tipo.fromOrdinal(inputStream.readInt());
        Date data = new Date(inputStream.readLong());

        /* Apenas Crédito não é uma transação em estoque */
        if (tipo == Tipo.CREDITO) {
            return new Credito(usuario, data, inputStream.readFloat());
        }

        TransacaoEstoque transacaoEstoque;
        if (tipo == Tipo.COMPRA) {
            transacaoEstoque = new Compra(gerenciadorEstoque, usuario, data);
        } else {
            transacaoEstoque = new Reposicao(gerenciadorEstoque, (UsuarioAdministrador) usuario, data);
        }
        /* Adicionamos as compras */
        for (int i = 0; i < inputStream.readInt(); i++) {
            transacaoEstoque.produtos.put(gerenciadorEstoque.getProduto(inputStream.readUTF()), inputStream.readInt());
        }
        return transacaoEstoque;
    }

    @Override
    public int compareTo(Transacao transacao) {
        return data.compareTo(transacao.getData());
    }

    public enum Tipo {

        COMPRA,
        REPOSICAO,
        CREDITO;

        public boolean isTransacaoEstoque() {
            switch (this) {
                case COMPRA:
                case REPOSICAO:
                    return true;
                default:
                    return false;
            }
        }

        public static Tipo fromOrdinal(int ordinal) {
            for (Tipo tipo : values()) {
                if (tipo.ordinal() == ordinal) {
                    return tipo;
                }
            }
            throw new NullPointerException("Não existe o tipo de transação com ordinal " + ordinal);
        }
    }
}
