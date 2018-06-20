package br.unicamp.laricaco.usuario;

import br.unicamp.laricaco.transacoes.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Classe que guarda as informações do usuário. A ideia é conseguir determinar o saldo do usuário através da soma de
 * todas as transações. Ou seja, objetos do tipo Transacao irão formar a linha do tempo do usuário no LariCACo.
 * <p>
 * Será possível determinar quais usuários compraram quais itens e quando, quem está com saldo negativo ou positivo.
 */
public class Usuario {

    /**
     * Gerador de números cuja seed é o horário de inicialização da variável (não precisa possuir tanta segurança).
     * É utilizado para gerar códigos para alteração de senha.
     */
    private static final Random gerador = new Random(System.currentTimeMillis());

    /**
     * Variável que guardará as transações executadas pelo usuário. Deverá ser mantida a ordem, ou seja, mais novo deve
     * vir primeiro ou por último. Isso deve ser respeitado por quem implementa.
     */
    protected final ArrayList<Transacao> transacoes = new ArrayList<>();

    /**
     * Registro acadêmico (RA) do usuário
     */
    private final int ra;
    /**
     * PIN (senha de 4 digitos numéricos) do usuário.
     */
    private int pin;
    /**
     * E-mail do usuário, será usado para pedidos de troca de senha.
     */
    private String email;
    /**
     * A variável esqueciSenha só irá ser usada por aqueles que pedem pra mudar a senha por e-mail. Possui um código
     * em String. É null para aqueles que não querem mudar a senha. Apesar de usarmos uma senha numérica, utilizamos
     * String pois é possível sobrecarregar o método alterarSenha.
     */
    private CodigoAlteracao esqueciSenha = null;

    Usuario(int ra, int pin, String email) {
        this.ra = ra;
        this.pin = pin;
        this.email = email;
    }

    /**
     * A função deverá enviar um e-mail para o usuário com um código que deverá ser registrado em "esqueciSenha", de
     * maneira que o usuário consiga criar uma nova senha a partir do e-mail. O número aleatório está entre 10.000 e
     * 99.999.
     */
    public void pedirTrocaSenha() {
        this.esqueciSenha = new CodigoAlteracao();

        // TODO: Mandar o codigo no email
    }


    /**
     * Alteramos a senha do usuário caso o código seja idêntico ao esqueciSenha.
     *
     * @param codigo código enviado ao usuário para permitir a alteração de senha sem saber a senha atual.
     * @return true no caso em que a senha foi alterada com sucesso. False no caso em que o código esteja incorreto ou
     * que o novo PIN seja inválido.
     */
    public boolean alterarSenha(String codigo, int pin) {
        if (esqueciSenha == null || pin < 0 || pin > 9_999 || !esqueciSenha.podeRealizarTentativa() ||
                !esqueciSenha.realizarTentativa(Integer.valueOf(codigo))) {
            return false;
        }

        this.pin = pin;
        return true;
    }

    /**
     * @return true se o usuário pode alterar a senha utilizando o código enviado por e-mail.
     */
    public boolean podeAlterarSenha() {
        return esqueciSenha != null && esqueciSenha.podeRealizarTentativa();
    }

    /**
     * Alteramos a senha do usuário caso a senha dada seja igual a senha atual do usuário.
     *
     * @param antiga senha antiga do usuário
     * @param nova   senha nova do usuário
     * @return true no caso em que a senha foi alterada com sucesso. False no caso em que a senha nova tenha formato
     * inválido ou a variável antiga não coincide com a senha atual.
     */
    public boolean alterarSenha(int antiga, int nova) {
        if (antiga == pin) {
            pin = nova;
            return true;
        }
        return false;
    }

    /**
     * Cria uma transação que representa depósito no usuário.
     * <p>
     * Observação: em Java, métodos começam com letra minúscula.
     *
     * @param valor quantidade em reais a ser depositada
     */
    public void creditar(float valor) {
        Credito credito = new Credito(this, new Date(), valor);
        /* Adicionamos a transação à lista de transações do usuário */
        transacoes.add(credito);
    }

    /*
     * Podemos decidir se devemos criar Compra novamente (herdeira de TransacaoEstoque) só pra ficar mais explícito o
     * que é compra e o que não é.
     */
    public Compra fazerCompra() {
        Compra compra = new Compra(this, new Date());
        /* Adicionamos a transação à lista de transações do usuário */
        transacoes.add(compra);
        return compra;
    }

    /**
     * @return o registro acadêmico do usuário.
     */
    public int getRA() {
        return ra;
    }

    /**
     * @return a senha de 4 dígitos numéricos do usuário.
     */
    public int getPin() {
        return pin;
    }

    /**
     * @return o e-mail do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return true se o usuário for do tipo Administrador (pode registrar reposição, aka. é do CACo)
     */
    public boolean isAdmin() {
        return false;
    }

    /**
     * @return o saldo do usuário. Deverá ser recalculado após qualquer nova transação.
     */
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

    /**
     * Classe que controla o código de alteração de senha, invalidando-o caso o número de tentativas seja ultrapassado.
     */
    public class CodigoAlteracao {

        private static final int QUANTIDADE_MAXIMA = 3;

        private final int codigo;
        private int tentativas = 0;

        private CodigoAlteracao() {
            this.codigo = gerador.nextInt(100_000 - 10_000) + 10_000;
        }

        /**
         * @return true se o usuário pode realizar outra tentativa
         */
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
