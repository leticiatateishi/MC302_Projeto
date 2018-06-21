package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.Main;
import br.unicamp.laricaco.estoque.Carrinho;
import br.unicamp.laricaco.estoque.Compra;
import br.unicamp.laricaco.estoque.Credito;
import br.unicamp.laricaco.estoque.Transacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Usuario {

    private static final Random gerador = new Random(System.currentTimeMillis());

    /* Estará disponível às classes que estão na pasta usuário */
    final Main main;
    final ArrayList<Transacao> transacoes = new ArrayList<>();

    private final int ra;
    private int pin;
    private String email;

    private CodigoAlteracao esqueciSenha = null;
    private Carrinho carrinho;

    Usuario(Main main, int ra, int pin, String email) {
        this.main = main;
        this.ra = ra;
        this.pin = pin;
        this.email = email;
        this.carrinho = new Carrinho(this, main.getGerenciadorEstoque());
    }

    public void pedirTrocaSenha() {
        this.esqueciSenha = new CodigoAlteracao();

        // TODO: Mandar o codigo no email
        // TODO AAAAAAAAAAAAAAAAAAA :)

    }

    public boolean alterarSenha(String codigo, int pin) {
        if (esqueciSenha == null || pin < 0 || pin > 9_999 || !esqueciSenha.podeRealizarTentativa() ||
                !esqueciSenha.realizarTentativa(Integer.valueOf(codigo))) {
            return false;
        }

        this.pin = pin;
        return true;
    }

    public boolean podeAlterarSenha() {
        return esqueciSenha != null && esqueciSenha.podeRealizarTentativa();
    }

    public boolean alterarSenha(int antiga, int nova) {
        if (antiga == pin) {
            pin = nova;
            return true;
        }
        return false;
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

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
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
        return "R.A.: " + getRA() + ", e-mail: " + getEmail();
    }

    public class CodigoAlteracao {

        private static final int QUANTIDADE_MAXIMA = 3;

        private final int codigo;
        private int tentativas = 0;

        private CodigoAlteracao() {
            this.codigo = gerador.nextInt(100_000 - 10_000) + 10_000;
        }

        public boolean podeRealizarTentativa() {
            return tentativas <= 3;
        }

        public boolean realizarTentativa(int codigo) {
            if (!podeRealizarTentativa()) {
                return false;
            }

            tentativas++;
            return this.codigo == codigo;
        }

        @Override
        public String toString() {
            return "Alteração de senha: código = \"" + codigo + "\", tentativas remanescentes = "
                    + (QUANTIDADE_MAXIMA - tentativas);
        }
    }
}
