import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe intermediária entre Transacao e Produtos, permitindo uma relação bidirecional entre usuário e produtos.
 */
/*
 * Essa classe dispensa QuantidadeProdutos, já que não temos uma relação muito complexa para precisar de mais de uma
 * variável (inteiro quantidade).
 */
public abstract class TransacaoEstoque extends Transacao {

    /**
     * Dicionário de chave Produto e quantidade de produto, assim podemos controlar melhor a quantidade de itens
     * comprados/repostos sem classes complexas de adicionar e remover.
     */
    protected final HashMap<Produto, Integer> produtos = new HashMap<>();

    /**
     * Transação que altera estoque de Produto automaticamente.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     */
    /*
     * Esse construtor deve ser inicializado pelo usuário (e usuário administrador), assim o método em Usuário adiciona
     * à sua própria lista de transações.
     */
    public TransacaoEstoque(Usuario usuario, Date data) {
        super(usuario, data);
        /* Adicionamos a transação à lista estática em Produto (o controle/histórico de estoque) */
        Produto.adicionarTransacao(this);
    }

    /**
     * Calcula o valor individual do produto nessa transação. Possui fórmula quantidade de unidades * preço unitário.
     *
     * @param produto produto a ter valor calculado
     * @return valor das unidades
     */
    public abstract double getValor(Produto produto);

    @Override
    public double getValor() {
        double valor = 0.0D;
        for (Produto produto : Produto.getProdutos()) {
            valor += getValor(produto);
        }
        return valor;
    }

    /**
     * Função confere se algum produto dessa espécie já foi inserido na lista para depois adicionar a quantidade
     * desejada na transação.
     *
     * @param produto    tipo do produto a ser adicionado à transação
     * @param quantidade quantidade do produto a ser adicionado à transação, ** DEVE SER POSITIVO **
     * @return true se a adição ocorreu como esperado, alterando a transação. False se a quantidade de produtos é
     * negativa.
     */
    public boolean adicionarProduto(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            return false;
        }
        produtos.put(produto, quantidade + produtos.getOrDefault(produto, 0));
        return true;
    }

    /**
     * Remove uma quantidade de produtos da transação conferindo se esse produto existe na transação.
     *
     * @param produto    tipo do produto a ser removido da transação
     * @param quantidade quantidade do produto a ser removido da transação. ** DEVE SER POSITIVO **
     * @return true se o produto foi removido com sucesso, false se a transação não foi alterada pois não há itens
     * suficientes para remover.
     */
    public boolean removerProduto(Produto produto, int quantidade) {
        if (quantidade > 0 && produtos.getOrDefault(produto, 0) >= quantidade) {
            produtos.put(produto, produtos.get(produto) - quantidade);
            return true;
        }
        return false;
    }

    /**
     * Confere a quantidade de produto na transação.
     *
     * @param produto produto a ser conferido
     * @return quantidade de produto na transação.
     */
    public int quantidadeProduto(Produto produto) {
        return produtos.getOrDefault(produto, 0);
    }
}
