public class QuantidadeCaixa extends QuantidadeProduto {

    public QuantidadeCaixa(){}

    @Override
    public int getQuantidade(){
        return super.getQuantidade()*getProduto().getQuantidadePorCaixa();
    }
}
