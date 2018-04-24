import java.util.ArrayList;

public class Compra extends Transacao {

    /**
     * Da mesma maneira que em Usuário, podemos utilizar um HashSet para evitar duplicates de produtos e sim alterar a
     * quantidade deles. No entanto, isso pode ser ignorado (podemos utilizar uma ArrayList e o histórico de produtos
     * ficará parecido com o de um super-mercado, com 2 leites no início da compra, 1 leite no meio e o quarto e último
     * leite no fim do cupom).
     *
     * Se optarmos por um "cupom fiscal" ordenado e bonitinho, precisamos conferir mais coisas e definir a igualdade de
     * QuantidadeProduto (sobreescrever hashCode e equals).
     */
    private final ArrayList<QuantidadeProduto> produtos = new ArrayList<>();

    public Compra(Usuario usuario) {
        super(usuario);
    }

    @Override
    public double getValor() {
        double valor = 0.0;
        for (QuantidadeProduto qp: produtos){
            valor += qp.getProduto().getPreco() * qp.getQuantidade();
        }
        return valor;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean adicionarProduto(Produto produto, int quantidade){
        if (produtos.add(new QuantidadeProduto(produto, quantidade)))
            return true;
        return false;
    }

    public boolean removerProduto(Produto produto, int quantidade){
        for (QuantidadeProduto qp: produtos){
            if (qp.getProduto().equals(produto) && qp.getQuantidade() >= quantidade){
                qp.setQuantidade(qp.getQuantidade()-quantidade);
                return true;
            }
        }
        return false;
    }
}
