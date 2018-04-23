package client;

public class RegistroPartida {

    private static final int EMPATE = -1;
    private static int ultimoIdentificador = -1;


    private final int identificador;
    private final Usuario[] usuarios = new Usuario[2];
    private final int idVencedor;

    /**
     * Cria um registro para uma partida realizada.
     *
     * @param jogador1 primeiro jogador envolvido
     * @param jogador2 segundo jogador envolvido
     * @param vencedor jogador vencedor, pode ser nulo em caso de empate
     */
    public RegistroPartida(Usuario jogador1, Usuario jogador2, Usuario vencedor) {
        this.identificador = ++RegistroPartida.ultimoIdentificador;
        this.usuarios[0] = jogador1;
        this.usuarios[1] = jogador2;
        this.idVencedor = vencedor != null ? vencedor.getIdentificador() : RegistroPartida.EMPATE;
        if (vencedor != jogador1 && vencedor != jogador2) {
            throw new IllegalArgumentException("O vencedor deve estar no jogo!");
        }
    }

    public int getIdentificador() {
        return identificador;
    }

    public boolean possuiVencedor() {
        return idVencedor != RegistroPartida.EMPATE;
    }

    public Usuario[] getJogadores() {
        return usuarios;
    }

    /**
     * @return o vencedor da partida, null caso não existir.
     */
    public Usuario getVencedor() {
        return idVencedor == RegistroPartida.EMPATE ? null : Usuario.procurarUsuario(idVencedor);
    }
}
