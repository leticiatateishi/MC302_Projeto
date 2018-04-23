import java.util.ArrayList;

public enum Produtos {

    TRENTO(1.25, 16),
    PACOCA(0.25, 50),
    PIRULITO(0.25, 60);

    private ArrayList<QuantidadeProduto> produtos;

    double preco;
    int quantidadePorCaixa;
    Produtos(double preco, int quantidadePorCaixa){
        this.preco = preco;
    }

    public int getQuantidadePorCaixa() {
        return quantidadePorCaixa;
    }
}
