package br.unicamp.laricaco;

import java.security.PublicKey;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class GerenciadorUsuario {

    /**
     * Variável estática que guardará todos os usuários registrados (banco de dados na memória). É estático pois está
     * atrelado à classe e não a cada objeto usuário. Através dos **construtores** iremos manter o banco de dados.
     * <p>
     * HashSet é uma coleção (estrutura de dados) que utiliza hashing pra definir igualdade. Não poderá haver 2 usuários
     * com o mesmo hash. O hash de cada objeto é calculado pelo método hashCode() que será sobreescrito do objeto-base
     * Object. Consequentemente, devemos sobreescrever o método equals(Object) para definir a igualdade de maneira
     * consistente.
     * Isso é necessário para mantermos apenas uma cópia do objeto na memória (ou seja, não devemos possuir dois
     * usuários com mesmo RA, e essa estrutura irá garantir isso). No entanto, precisamos tomar cuidado na hora de
     * adicionar itens, o método add(Usuario) do HashSet só irá retornar true se a estrutura foi modificada, retornará
     * false se o objeto não foi inserido por ser um duplicate.
     * Mais informações: https://en.wikipedia.org/wiki/Java_hashCode%28%29 e
     * https://www.javaworld.com/article/2074996/hashcode-and-equals-method-in-java-object---a-pragmatic-concept.html
     */
    private final HashSet<Usuario> usuarios = new HashSet<>();

    /**
     * @return conjunto não modificável de usuários
     * @see Collections#unmodifiableSet(Set) conjunto não modificável
     */
    public Set<Usuario> getUsuarios() {
        return Collections.unmodifiableSet(usuarios);
    }

    public Usuario getUsuario(int ra) {
        for (Usuario i : usuarios) {
            if (i.getRA() == ra) return i;
        }
        return null;
    }

    public Usuario adicionarUsuario(int ra, int pin, String email) {
        Usuario usuario = getUsuario(ra);
        if (usuario == null) {
            usuario = new Usuario(ra, pin, email);
            usuarios.add(usuario);
        }
        return usuario;
    }

    public UsuarioAdministrador adicionarAdministrador(int ra, int pin, String email) throws ExcecaoUsuarioExistente{

        Usuario usuario = getUsuario(ra);

        if (usuario != null && !(usuario instanceof UsuarioAdministrador)){
            throw new ExcecaoUs
        }

        if (usuario == null){
            usuario = new UsuarioAdministrador(ra, pin, email);
            usuarios.add(usuario);
        }
        return usuario;
    }
}
