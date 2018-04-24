import java.util.ArrayList;
import java.util.Date;

/**
 * Classe intermediária entre Transacao e Produtos, permitindo uma relação bidirecional entre usuário e produtos.
 */
public abstract class TransacaoEstoque extends Transacao {

    private final ArrayList<QuantidadeProduto> produtos = new ArrayList<>();

    public TransacaoEstoque(Usuario usuario, Date data) {
        super(usuario, data);
    }

    /* Copiar os métodos de Compra que a Letícia fez para adicionar e remover produtos */
}
