package br.unicamp.laricaco.transacoes;

import br.unicamp.laricaco.usuario.*;

import java.util.Date;

/**
 * Classe abstrata que representa uma transação:
 * Uma transação permite calcular a diferença em reais no saldo de algum usuário.
 */
public abstract class Transacao {

    /**
     * Permite a relação bidirecional com a transação.
     */
    private final Usuario usuario;
    /*
     * Utilizando new Date() para receber a data atual (número de milisegundos desde 1 de janeiro de 1970).
     */
    private final Date data;

    Transacao(Usuario usuario, Date data) {
        this.usuario = usuario;
        this.data = data;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Date getData() {
        return data;
    }

    /**
     * Calcula, a partir das informações da transação, seu valor total.
     *
     * @return o valor total da transação em reais.
     */
    public abstract float getValor();

}
