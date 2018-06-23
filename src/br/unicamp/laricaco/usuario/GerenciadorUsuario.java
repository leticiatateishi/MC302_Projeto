package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.Main;
import br.unicamp.laricaco.utilidades.LariCACoException;
import br.unicamp.laricaco.utilidades.Salvavel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class GerenciadorUsuario implements Salvavel {

    private static final Random gerador = new Random(System.currentTimeMillis());

    private final Main main;
    private final HashSet<Usuario> usuarios = new HashSet<>();
    private final HashMap<Usuario, CodigoAlteracao> codigosAlteracaoSenha = new HashMap<>();

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

    public Usuario adicionarUsuario(int ra, int pin, String pergunta, String resposta) {
        Usuario usuario = getUsuario(ra);
        if (usuario == null) {
            usuario = new Usuario(main, ra, pin, pergunta, resposta);
            usuarios.add(usuario);
        }
        return usuario;
    }

    public UsuarioAdministrador adicionarAdministrador(int ra, int pin, String pergunta, String resposta)
            throws LariCACoException {

        Usuario usuario = getUsuario(ra);

        if (usuario != null && !(usuario instanceof UsuarioAdministrador)) {
            throw new LariCACoException("Usuário já existe!");
        }

        if (usuario == null) {
            usuario = new UsuarioAdministrador(main, ra, pin, pergunta, resposta);
            usuarios.add(usuario);
        }

        /* Como com certeza é um UsuarioAdministrador, podemos usar o casting */
        return (UsuarioAdministrador) usuario;
    }

    /*
     * Para trocar a senha, o usuário que não tem acesso a sua conta deve colocar o seu RA e e-mail.
     */
    public int pedirTrocaSenha(Usuario usuario, String resposta) throws LariCACoException {
        if (usuario == null) {
            throw new LariCACoException("Usuário não encontrado!");
        }

        if (!usuario.getResposta().equalsIgnoreCase(resposta)) {
            throw new LariCACoException("A resposta à pergunta secreta está incorreta!");
        }

        /* Pegamos o código atual se for válido */
        CodigoAlteracao codigoAlteracao = codigosAlteracaoSenha.get(usuario);
        if (codigoAlteracao != null && codigoAlteracao.podeRealizarTentativa()) {
            throw new LariCACoException("O código de alteração passado ainda é válido! Informe ao administrador: " +
                    codigoAlteracao.getCodigo());
        } else {
            codigoAlteracao = new CodigoAlteracao();
            codigosAlteracaoSenha.put(usuario, codigoAlteracao);
            return codigoAlteracao.getCodigo();
        }
    }

    /*
     * O usuário informa ao administrador o RA e o código. Se o código informado coincidir com o código registrado no RA
     * da pessoa, iremos alterar a senha. Se não coincidir, sobrará N - 1 tentativas antes do código ser resetado.
     */
    public void trocarSenha(Usuario usuario, int codigo, int pin) throws LariCACoException {
        if (pin < 0 || pin > 9999) {
            throw new LariCACoException("Nova senha é inválida!");
        }

        CodigoAlteracao codigoAlteracao = codigosAlteracaoSenha.get(usuario);
        if (codigoAlteracao == null || !codigoAlteracao.realizarTentativa(codigo)) {
            throw new LariCACoException("Código é inválido!");
        }

        usuario.trocarSenha(pin);
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(usuarios.size());
        outputStream.flush();
        for (Usuario usuario : usuarios) {

            CodigoAlteracao codigoAlteracao = codigosAlteracaoSenha.get(usuario);
            if (codigoAlteracao != null) {
                outputStream.writeBoolean(true);
                codigoAlteracao.salvar(outputStream);
            } else {
                outputStream.writeBoolean(false);
            }

            usuario.salvar(outputStream);
        }
    }

    public static GerenciadorUsuario carregar(Main main, DataInputStream inputStream) throws IOException {
        GerenciadorUsuario gerenciadorUsuario = new GerenciadorUsuario(main);

        int numUsuarios = inputStream.readInt();
        for (int i = 0; i < numUsuarios; i++) {
            CodigoAlteracao codigoAlteracao = gerenciadorUsuario.carregar(inputStream);
            Usuario usuario = Usuario.carregar(main, inputStream);

            if (codigoAlteracao != null) {
                gerenciadorUsuario.codigosAlteracaoSenha.put(usuario, codigoAlteracao);
            }

            gerenciadorUsuario.usuarios.add(usuario);
        }

        return gerenciadorUsuario;
    }

    private CodigoAlteracao carregar(DataInputStream inputStream) throws IOException {
        return inputStream.readBoolean() ? new CodigoAlteracao(inputStream.readInt(), inputStream.readInt()) : null;
    }

    public class CodigoAlteracao implements Salvavel {

        private static final int MAXIMO_TENTATIVAS = 3;

        private final int codigo;
        private int tentativas = 0;

        private CodigoAlteracao() {
            this.codigo = gerador.nextInt(100_000 - 10_000) + 10_000;
        }

        private CodigoAlteracao(int codigo, int tentativas) {
            this.codigo = codigo;
            this.tentativas = tentativas;
        }

        private boolean realizarTentativa(int codigo) {
            if (!podeRealizarTentativa()) {
                return false;
            }

            tentativas++;
            return this.codigo == codigo;
        }

        public boolean podeRealizarTentativa() {
            return tentativas <= MAXIMO_TENTATIVAS;
        }

        public int getCodigo() {
            return codigo;
        }

        @Override
        public String toString() {
            return "Alteração de senha: código = \"" + codigo + "\"";
        }

        @Override
        public void salvar(DataOutputStream outputStream) throws IOException {
            outputStream.writeInt(tentativas);
            outputStream.writeInt(codigo);
        }
    }
}
