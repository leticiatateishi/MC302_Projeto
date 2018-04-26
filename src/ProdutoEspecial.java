import java.util.HashSet;
import java.util.Set;

public class ProdutoEspecial extends Produto{
	
	private HashSet<Produto> produtoEspecial;
	
	public ProdutoEspecial(String nome, float PV, float PC, int QC){
		super(nome, PV, PC, QC);
		produtoEspecial = new HashSet<Produto>();
	}
	
	public Produto adicionarVariacaoProduto(String nome){
		Produto produto = new Produto(nome,getPrecoVenda(),getPrecoCusto(),getQntCaixa());
		produtoEspecial.add(produto);
		return produto;
	}
	
	public Set<Produto> getVariacoes() {
		return produtoEspecial;
	}
	
}
