package br.unicamp.laricaco;

import br.unicamp.laricaco.estoque.GerenciadorEstoque;
import br.unicamp.laricaco.estoque.Reposicao;
import br.unicamp.laricaco.usuario.UsuarioAdministrador;
import br.unicamp.laricaco.utilidades.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class JanelaAdministrador extends JFrame {

    String[] produtoAdicionado = new String[5];
    Reposicao transacaoReposicao;
    DefaultTableModel model = new DefaultTableModel();

    JTextField nomeCampo = new JTextField(10);
    JTextField produtosCaixaCampo = new JTextField(10);
    JTextField quantidadeCaixasCampo = new JTextField(10);
    JTextField precoCaixaCampo = new JTextField(10);

    UsuarioAdministrador usuario;
    GerenciadorEstoque gerenciadorEstoque;

    public JanelaAdministrador(UsuarioAdministrador usuario, GerenciadorEstoque gerenciadorEstoque) {
        super("Usuário administrador");
        this.usuario = usuario;
        this.gerenciadorEstoque = gerenciadorEstoque;

        setSize(500, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        setContentPane(panel);

        JPanel estatisticas = new JPanel();
        JLabel label = new JLabel("Estatísticas");
        label.setAlignmentX(CENTER_ALIGNMENT);
        estatisticas.add(label);
        estatisticas.setLayout(new BoxLayout(estatisticas, BoxLayout.Y_AXIS));
        JScrollPane estatisticasScroll = new JScrollPane(estatisticas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        estatisticasScroll.setPreferredSize(new Dimension(300, 300));

        JLabel ultimaReposicao = new JLabel("Data da última reposição: " + gerenciadorEstoque.ultimaReposição());
        JLabel ultimaCompra = new JLabel("Data da última compra: " + gerenciadorEstoque.ultimaCompra());
        JLabel maiorEstoque = new JLabel("Produto com maior estoque: " + gerenciadorEstoque.produtoComMaiorEstoque());
        JLabel maisVendido = new JLabel("Produto mais vendido: " + gerenciadorEstoque.produtoMaisVendido().getNome() +
                " (" + gerenciadorEstoque.produtoMaisVendido().getQuantidadeVendida() + " unidades)");

        estatisticas.add(ultimaReposicao);
        estatisticas.add(ultimaCompra);
        estatisticas.add(maiorEstoque);
        estatisticas.add(maisVendido);

        getContentPane().add(estatisticasScroll);

        JPanel reposicao = new JPanel();
        reposicao.setLayout(new BoxLayout(reposicao, BoxLayout.Y_AXIS));
        JLabel label2 = new JLabel("Reposição");
        reposicao.add(label2);
        label2.setAlignmentX(CENTER_ALIGNMENT);
        JTable tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);

        Object[] colunas = {"Nome", "Produtos por caixa", "Quantidade de caixas", "Preço da caixa", "Preço de venda"};
        model.setColumnIdentifiers(colunas);
        tabela.setModel(model);
        tabela.setRowHeight(25);

        JPanel nomePainel = new JPanel();
        nomePainel.setLayout(new FlowLayout());

        JPanel produtosCaixaPainel = new JPanel();
        produtosCaixaPainel.setLayout(new FlowLayout());

        JPanel quantidadeCaixasPainel = new JPanel();
        quantidadeCaixasPainel.setLayout(new FlowLayout());

        JPanel precoCaixaPainel = new JPanel();
        precoCaixaPainel.setLayout(new FlowLayout());

        JLabel nome = new JLabel("Nome:");
        JLabel produtosCaixa = new JLabel("Quantidade de produtos por caixa:");
        JLabel quantidadeCaixas = new JLabel("Quantidade de caixas:");
        JLabel precoCaixa = new JLabel("Preço da caixa:");


        nomePainel.add(nome);
        nomePainel.add(nomeCampo);

        produtosCaixaPainel.add(produtosCaixa);
        produtosCaixaPainel.add(produtosCaixaCampo);

        quantidadeCaixasPainel.add(quantidadeCaixas);
        quantidadeCaixasPainel.add(quantidadeCaixasCampo);

        precoCaixaPainel.add(precoCaixa);
        precoCaixaPainel.add(precoCaixaCampo);

        JButton adicionarReposicao = new JButton("Adicionar");
        adicionarReposicao.setMnemonic(KeyEvent.VK_ENTER);

        adicionarReposicao.addActionListener(new AdicionarReposicao());

        reposicao.add(scroll);
        reposicao.add(nomePainel);
        reposicao.add(produtosCaixaPainel);
        reposicao.add(quantidadeCaixasPainel);
        reposicao.add(precoCaixaPainel);
        reposicao.add(adicionarReposicao);
        panel.add(reposicao);


    }

    private class AdicionarReposicao implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            produtoAdicionado[0] = nomeCampo.getText();
            produtoAdicionado[1] = produtosCaixaCampo.getText();
            produtoAdicionado[2] = quantidadeCaixasCampo.getText();
            produtoAdicionado[3] = precoCaixaCampo.getText();
            produtoAdicionado[4] = String.valueOf(Float.valueOf(precoCaixaCampo.getText()) / Float.valueOf(produtosCaixaCampo.getText()));

            model.addRow(produtoAdicionado);

            try {
                if (transacaoReposicao == null) {
                    transacaoReposicao = usuario.fazerReposicao();
                }
                transacaoReposicao.adicionarProduto(gerenciadorEstoque.getOuCriarProduto(produtoAdicionado[0],
                        Float.valueOf(produtoAdicionado[4]), Float.valueOf(produtoAdicionado[3]),
                        Integer.parseInt(produtoAdicionado[1])),
                        Integer.parseInt(produtoAdicionado[2]));
            } catch (LariCACoException e) {
            }

            nomeCampo.setText("");
            produtosCaixaCampo.setText("");
            quantidadeCaixasCampo.setText("");
            precoCaixaCampo.setText("");
        }
    }
}
