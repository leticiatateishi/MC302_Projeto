package client;

import java.util.HashMap;

public class Usuario {

    private static int ultimoIdentificador = -1;
    private static final HashMap<Integer, Usuario> usuarios = new HashMap<>();

    private final int identificador;

    private String nome;
    private String email;
    private String senha;
    private String dataRegistro;

    public Usuario(String nome, String email, String senha) {
        this.identificador = ++Usuario.ultimoIdentificador;
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        usuarios.put(getIdentificador(), this);
    }

    public int getIdentificador() {
        return identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    /**
     * Procura no mapa de usuários instanciados o identificador dado.
     *
     * @param identificador identificador do usuário, número único para cada usuário.
     * @return null se não encontrado, um usuário caso contrário
     */
    public static Usuario procurarUsuario(int identificador) {
        return usuarios.get(identificador);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Usuario && ((Usuario) obj).getIdentificador() == this.getIdentificador();
    }

    @Override
    public String toString() {
        return "Usuário id=" + getIdentificador() + " (\n" +
                "Nome: " + getNome() + ",\n" +
                "E-mail: " + getEmail() + ",\n" +
                "Data de registro: " + getDataRegistro() + "\n" +
                ");";
    }
}
