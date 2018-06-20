package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.utilidades.*;

import java.util.*;

public class GerenciadorUsuario {

    /**
     * Variável que guardará todos os usuários registrados (banco de dados na memória).
     */
    private final HashSet<Usuario> usuarios = new HashSet<>();

    /**
     * @return um conjunto não modificável de usuários
     * @see Collections#unmodifiableSet(Set) conjunto não modificável
     */
    public Set<Usuario> getUsuarios() {
        return Collections.unmodifiableSet(usuarios);
    }

    /**
     * Pesquisa o usuário na lista de usuários.
     *
     * @param ra registro acadêmico procurado
     * @return usuário encontrado
     * @throws NoSuchElementException caso o usuário não tenha sido encontrado
     */
    public Usuario getUsuario(int ra) throws NoSuchElementException {
        return usuarios.parallelStream().findFirst().orElseThrow();
    }

    /**
     * Cria o usuário e o adiciona na lista de usuários.
     *
     * @param ra    registro acadêmico (R.A.) do usuário
     * @param pin   senha de 4 digitos do usuário
     * @param email e-mail do usuário
     * @return um usuário
     * @throws IllegalArgumentException caso a senha não possua 4 dígitos.
     * @throws LariCACoException        caso o R.A. já tenha sido registrado
     */
    public Usuario criarUsuario(int ra, int pin, String email) throws IllegalArgumentException, LariCACoException {
        if (pin < 0 || pin > 9999) {
            throw new IllegalArgumentException("PIN não possui 4 dígitos!");
        }

        Usuario usuario = new Usuario(ra, pin, email);
        /* Caso retorne falso, o conjunto já possuia este usuário */
        if (!usuarios.add(usuario)) {
            throw new LariCACoException("R.A. já registrado!");
        }
        return usuario;
    }
}
