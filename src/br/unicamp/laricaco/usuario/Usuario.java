package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.Main;
import br.unicamp.laricaco.estoque.Carrinho;
import br.unicamp.laricaco.estoque.Compra;
import br.unicamp.laricaco.estoque.Credito;
import br.unicamp.laricaco.estoque.Transacao;
import br.unicamp.laricaco.utilidades.*;

import java.io.*;
import java.util.*;

public class Usuario implements Salvavel {

    /* Estará disponível às classes que estão na pasta usuário */
    final Main main;
    final ArrayList<Transacao> transacoes = new ArrayList<>();

    private final int ra;
    private int pin;
    private String pergunta, resposta;

    private Carrinho carrinho;

    Usuario(Main main, int ra, int pin, String pergunta, String resposta) {
        this.main = main;
        this.ra = ra;
        this.pin = pin;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.carrinho = new Carrinho(this, main.getGerenciadorEstoque());
    }

    public int pedirTrocaSenha(String resposta) throws LariCACoException {
        return main.getGerenciadorUsuario().pedirTrocaSenha(this, resposta);
    }

    public boolean alterarSenha(int antiga, int nova) {
        if (antiga == pin) {
            pin = nova;
            return true;
        }
        return false;
    }

    void trocarSenha(int pin) {
        this.pin = pin;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void addCompra(Compra compra) {
        /* Adicionamos a transação à lista de transações do usuário */
        transacoes.add(compra);
    }

    public void creditar(float valor) {
        Credito credito = new Credito(this, new Date(), valor);
        /* Adicionamos a transação à lista de transações do usuário */
        transacoes.add(credito);
    }

    public int getRA() {
        return ra;
    }

    public int getPin() {
        return pin;
    }

    public String getPergunta() {
        return pergunta;
    }

    String getResposta() {
        return resposta;
    }

    public boolean isAdministrador() {
        return false;
    }

    public float getSaldo() {
        float saldo = 0.0f;

        for (Transacao transacao : transacoes) {
            if (transacao instanceof Credito) {
                saldo += transacao.getValor();
            } else if (transacao instanceof Compra) {
                saldo -= transacao.getValor();
            }
        }

        return saldo;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Usuario && ((Usuario) obj).getRA() == getRA();
    }

    @Override
    public int hashCode() {
        /* Nesse caso, é suficiente considerar o RA um hash, por ser inteiro */
        return ra;
    }

    @Override
    public String toString() {
        return "R.A.: " + getRA();
    }

    @Override
    public void salvar(DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(ra);
        outputStream.writeInt(pin);
        outputStream.writeUTF(pergunta);
        outputStream.writeUTF(resposta);
        outputStream.writeBoolean(isAdministrador());
        outputStream.writeInt(transacoes.size());
        outputStream.flush();

        Collections.sort(transacoes);
        for (Transacao transacao : transacoes) {
            /* As transações serão guardadas pelo usuário, o estoque só guardará os produtos, pois são independentes
             * do usuário (transações por outro lado requerem um usuário) */
            System.out.println(transacao.getTipo() + " " + transacao.getData().toString());
            transacao.salvar(outputStream);
        }
    }

    public static Usuario carregar(Main main, DataInputStream inputStream) throws IOException {
        int ra = inputStream.readInt();
        int pin = inputStream.readInt();
        String pergunta = inputStream.readUTF();
        String resposta = inputStream.readUTF();

        Usuario usuario;
        if (inputStream.readBoolean()) {
            usuario = new UsuarioAdministrador(main, ra, pin, pergunta, resposta);
        } else {
            usuario = new Usuario(main, ra, pin, pergunta, resposta);
        }

        int numTransacoes = inputStream.readInt();
        for (int i = 0; i < numTransacoes; i++) {
            usuario.transacoes.add(main.getGerenciadorEstoque().carregarTransacao(usuario, inputStream));
        }

        return usuario;
    }
}
