package br.unicamp.laricaco.estoque;

import br.unicamp.laricaco.usuario.Usuario;

import java.util.Date;

public abstract class Transacao {

    private final Usuario usuario;
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

    public abstract float getValor();

}
