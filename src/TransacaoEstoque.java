import java.util.Date;
import java.util.EnumMap;

/**
 * Classe intermediária entre Transacao e Produtos, permitindo uma relação bidirecional entre usuário e produtos.
 */
/*
 * Essa classe dispensa QuantidadeProdutos, já que não temos uma relação muito complexa para precisar de mais de uma
 * variável (inteiro quantidade).
 */
public class TransacaoEstoque extends Transacao {

    /**
     * Dicionário de chave Produto e quantidade de produto, assim podemos controlar melhor a quantidade de itens
     * comprados/repostos sem classes complexas de adicionar e remover.
     */
    /*
     * Mais informações: https://docs.oracle.com/javase/10/docs/api/java/util/EnumMap.html
     */
    private final EnumMap<Produto, Integer> produtos = new EnumMap<>(Produto.class);

    private final Tipo tipo;

    /**
     * Transação que altera estoque de Produto automaticamente. O tipo da transação REPOSICAO implica que a quantidade
     * tratada é em caixas.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     * @param tipo    tipo de transação (reposição ou compra)
     */
    /*
     * Esse construtor deve ser inicializado pelo usuário (e usuário administrador), assim o método em Usuário adiciona
     * à sua própria lista de transações. O tipo REPOSICAO irá trabalhar apenas com quantidade de caixas, então é
     * necessário lembrar no cálculo de estoque de algum Produto disso.
     */
    public TransacaoEstoque(Usuario usuario, Date data, Tipo tipo) {
        super(usuario, data);
        this.tipo = tipo;
        /* Adicionamos a transação à lista estática em Produto (o controle/histórico de estoque) */
        Produto.adicionarTransacao(this);
    }

    /**
     * @return o tipo da transação
     */
    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public double getValor() {
        double valor = 0.0D;
        /* Fazer um loop entre cada par <produto, quantidade> */
        return valor;
    }

    /**
     * Confere a quantidade de produto na transação. Se o tipo for REPOSICAO, trata-se do número de caixas.
     *
     * @param produto produto a ser conferido
     * @return quantidade de produto na transação.
     * @see Produto#getQuantidadePorCaixa() para o cálculo de unidades a partir de caixa no tipo REPOSICAO.
     */
    /*
     * Intellij IDEA permite você clicar em links utilizando @see ;)
     */
    public int checarQuantidade(Produto produto) {
        return 0;
    }

    /**
     * Função confere se algum produto dessa espécie já foi inserido na lista para depois adicionar a quantidade
     * desejada na transação. Trata-se da quantidade de caixas caso o tipo for REPOSICAO.
     *
     * @param produto    tipo do produto a ser adicionado à transação
     * @param quantidade quantidade do produto a ser adicionado à transação, ** DEVE SER POSITIVO **
     * @return true se a adição ocorreu como esperado, false se a quantidade de produtos é negativa e a transação não
     * foi alterada.
     */
    public boolean adicionarProduto(Produto produto, int quantidade) {
        return false;
    }

    /**
     * Remove uma quantidade de produtos da transação conferindo se esse produto existe na transação. Se o tipo for
     * REPOSICAO, trata-se da quantidade de caixas e não de unidades.
     *
     * @param produto    tipo do produto a ser removido da transação
     * @param quantidade quantidade do produto a ser removido da transação. ** DEVE SER POSITIVO **
     * @return true se o produto foi removido com sucesso, false se a transação não foi alterada pois não há itens
     * suficientes para remover.
     */
    public boolean removerProduto(Produto produto, int quantidade) {
        return false;
    }

    public enum Tipo {
        COMPRA,
        REPOSICAO
    }
}
