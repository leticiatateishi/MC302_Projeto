package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.LariCACoException;
import br.unicamp.laricaco.Main;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GerenciadorUsuario {

    private final Main main;
    private final HashSet<Usuario> usuarios = new HashSet<>();

    public GerenciadorUsuario(Main main) {
        this.main = main;
    }

    public Set<Usuario> getUsuarios() {
        return Collections.unmodifiableSet(usuarios);
    }

    public Usuario getUsuario(int ra) {
        for (Usuario usuario : usuarios) {
            if (usuario.getRA() == ra) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario adicionarUsuario(int ra, int pin, String email) {
        Usuario usuario = getUsuario(ra);
        if (usuario == null) {
            usuario = new Usuario(main, ra, pin, email);
            usuarios.add(usuario);
        }
        return usuario;
    }

    public UsuarioAdministrador adicionarAdministrador(int ra, int pin, String email) throws LariCACoException {

        Usuario usuario = getUsuario(ra);

        if (usuario != null && !(usuario instanceof UsuarioAdministrador)) {
            throw new LariCACoException("Usuário já existe!");
        }

        if (usuario == null) {
            usuario = new UsuarioAdministrador(main, ra, pin, email);
            usuarios.add(usuario);
        }

        /* Como com certeza é um UsuarioAdministrador, podemos usar o casting */
        return (UsuarioAdministrador) usuario;
    }
}
