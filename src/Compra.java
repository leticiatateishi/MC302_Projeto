import java.util.Date;

/**
 * Classe que representa a transação de comprar produtos a partir do estoque.
 */
public class Compra extends TransacaoEstoque {

    /**
     * Transação que altera estoque de Produto automaticamente.
     *
     * @param usuario o usuário realizando a transação
     * @param data    a data da transação
     */
    public Compra(Usuario usuario, Date data) {
        super(usuario, data);
    }

    /**
     * Função confere se algum produto dessa espécie já foi inserido na lista para depois adicionar a quantidade
     * desejada na transação se for menor que a quantidade disponível em estoque.
     *
     * @param produto    tipo do produto a ser adicionado à transação
     * @param quantidade quantidade do produto a ser adicionado à transação, ** DEVE SER POSITIVO **
     * @return true se a adição ocorreu como esperado, alterando a transação. False se a quantidade de produtos é
     * negativa ou se não há unidades suficiente em estoque.
     */
    @Override
    public boolean adicionarProduto(Produto produto, int quantidade) {
        return quantidade <= produto.getEstoque() && super.adicionarProduto(produto, quantidade);
    }

    public String toString(){
        return "Compra realizada na data" +getData()+ " por " +getUsuario();
    }
}
