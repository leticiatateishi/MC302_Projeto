import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Produto{
	
	private final String nome;
	private float precoVenda, precoCompra;
	private int qntCaixa;
	private static final ArrayList<TransacaoEstoque> transacoes = new ArrayList<TransacaoEstoque>();
	private static final HashSet<Produto> produtos = new HashSet<Produto>();
	
	public Produto(String nome,float PV,float PC,int QC) {
		this.nome = nome;
		this.precoVenda = PV;
		this.precoCompra = PC;
		this.qntCaixa = QC;
	}
	
	public int getEstoque(){
		int estoque=0;
		for(TransacaoEstoque i:transacoes) {
			if (i instanceof Compra) {
				estoque = estoque -((Compra) i).quantidadeProduto(this);
			}
			else if (i instanceof Reposicao) {
				estoque = estoque + ((Reposicao) i).quantidadeProduto(this);
			}
		}
		return estoque;
	}
	public static void adicionarTransacao(TransacaoEstoque aux) {
		transacoes.add(aux);
	}
	
	public static Set<Produto> getProdutos(){
		return produtos;
	}
	
	public String getNome() {
		return nome;
	}
	public float getPrecoVenda() {
		return precoVenda;
	}
	public void setPrecoVenda(float precoVenda) {
		this.precoVenda = precoVenda;
	}
	public float getPrecoCompra() {
		return precoCompra;
	}
	public void setPrecoCompra(float precoCompra) {
		this.precoCompra = precoCompra;
	}
	public int getQntCaixa() {
		return qntCaixa;
	}
	public void setQntCaixa(int qntCaixa) {
		this.qntCaixa = qntCaixa;
	}
	@Override
	public int hashCode() {
		return nome.hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof Produto && ((Produto) o).getNome().equals(nome);
	}
	
}
	