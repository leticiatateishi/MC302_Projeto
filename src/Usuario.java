import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Classe que guarda as informações do usuário. A ideia é conseguir determinar o saldo do usuário através da soma de
 * todas as transações. Ou seja, objetos do tipo Transacao irão formar a linha do tempo do usuário no LariCACo.
 * <p>
 * Será possível determinar quais usuários compraram quais itens e quando, quem está com saldo negativo ou positivo.
 */
public class Usuario {

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
    private final static HashSet<Usuario> usuarios = new HashSet<>();

    /**
     * Variável que guardará as transações executadas pelo usuário. Deverá ser mantida a ordem, ou seja, mais novo deve
     * vir primeiro ou por último. Isso deve ser definido na implementação.
     */
    protected final ArrayList<Transacao> transacoes = new ArrayList<>();

    /* Atributos do objeto que devem ser constantes */
    /**
     * Registro acadêmico (RA) do usuário
     */
    private final int ra;

    /* Atributos do objeto que podem variar e, portanto, não devem servir de  */
    /**
     * PIN (senha de 4 digitos numéricos) do usuário.
     */
    private int pin;
    /**
     * E-mail do usuário, será usado para pedidos de troca de senha.
     */
    private String email;

    /* Atributos que possuem valor padrão */
    /**
     * A variável esqueciSenha só irá ser usada por aqueles que pedem pra mudar a senha. É um String (ao invés de int)
     * pois pode assumir valor null e nos permite sobrecarregar o método alterarSenha, permitindo quem saiba a senha
     * alterá-la sem verificar o e-mail.
     */
    private String esqueciSenha = null;

    /**
     * Construtor padrão da classe usuário. Deverá salvar o aluno no banco de dados e, se já existe, lançar um erro
     * como IllegalArgumentException, informando a chamada do construtor que o RA é inválido. O trecho "throws" implica
     * que toda chamada para um novo usuário precisará tratar o caso em que exista o erro, seja avisando o usuário por
     * uma janela, seja quebrando o programa (caso que acontece se a exceção não é tratada). A importância da unicidade
     * de cada usuário é importante o suficiente para lançarmos esse tipo de erro.
     * <p>
     * Para tratar a exceção nas chamadas de new Usuario, utilize o escopo "try-catch".
     * Para fins de teste, acredito que podemos remover o trecho "throws", pois podemos ignorar isso até a versão de
     * "produção".
     * <p>
     * Podemos tratar o PIN como mesma importância que um RA, lançando um erro que pode ser considerado da mesma forma
     * ou, para diferenciar, lançar IllegalStateException para o RA (interpretamos estado sendo incorreto no sentido em
     * que entramos num estado em que o RA está duplicado) e IllegalArgumentException para a senha no formato inválido.
     *
     * @param ra    registro acadêmico do usuário
     * @param pin   senha de 4 dígitos numéricos (deveremos conferir antes de criar o usuário o formato da senha no caso
     *              em que não lançamos o erro para o Pin errado)
     * @param email e-mail do usuário
     * @throws IllegalArgumentException caso o RA já exista na lista de RAs registrados.
     */
    public Usuario(int ra, int pin, String email) /*throws IllegalArgumentException, IllegalStateException*/ {
        this.ra = ra;
        this.pin = pin;
        this.email = email;
        /* Adicionamos o usuário à lista de usuários */
        usuarios.add(this);
    }

    /**
     * A função deverá enviar um e-mail para o usuário com um código que deverá ser registrado em "esqueciSenha", de
     * maneira que o usuário consiga criar uma nova senha a partir do e-mail.
     */
    public void pedirTrocaSenha() {
        Random randomNumbers = new Random(System.nanoTime());
        Scanner sc = new Scanner(System.in);

        esqueciSenha = Integer.toString(randomNumbers.nextInt());

        // Mandar o codigo no email

        System.out.println("Insira o codigo recebido no e-mail:");
        int tentativas = 0;
        while (!alterarSenha(sc.next()) && ++tentativas < 3) {
            System.out.println("Codigo errado, tente de novo:");
        }
        if (tentativas == 3) {
            System.out.println("Limite de tentativas excedido!");
        }
    }


    /**
     * Alteramos a senha do usuário caso o código seja idêntico ao esqueciSenha.
     *
     * @param codigo código enviado ao usuário para permitir a alteração de senha sem saber a senha atual.
     *               //@param nova   senha nova (devemos conefrir antes de chamar esse método se a senha
     * @return true no caso em que a senha foi alterada com sucesso. False no caso em que o código esteja incorreto ou
     * que o novo PIN seja inválido.
     */
    public boolean alterarSenha(String codigo) {
        Scanner sc = new Scanner(System.in);

        if (Objects.equals(codigo, esqueciSenha)) {
            System.out.println("Insira nova senha:");
            pin = sc.nextInt();
            return true;
        } else return false;
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
    public void creditar(double valor) {
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
    public double getSaldo() {
        double saldo = 0.0D;

        for (Transacao transacao: transacoes){
            if (transacao instanceof Credito){
                saldo += transacao.getValor();
            } else if (transacao instanceof Compra){
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
        return "R.A.: " + getRA() + ", e-mail: " + getEmail() + "\n";
    }

    /**
     * @return conjunto não modificável de usuários
     * @see Collections#unmodifiableSet(Set) conjunto não modificável
     */
    public static Set<Usuario> getUsuarios() {
        return Collections.unmodifiableSet(usuarios);
    }
}
