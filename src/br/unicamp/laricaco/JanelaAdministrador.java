package br.unicamp.laricaco;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaAdministrador extends JFrame{


    public JanelaAdministrador(){

        super("Usuário administrador");
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
        panel.add(estatisticas);

        JPanel reposicao = new JPanel();
        reposicao.setLayout(new BoxLayout(reposicao, BoxLayout.Y_AXIS));
        JLabel label2 = new JLabel("Reposição");
        reposicao.add(label2);
        label2.setAlignmentX(CENTER_ALIGNMENT);
        JTable tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);

        Object[] colunas = {"Nome", "Produtos por caixa", "Quantidade de caixas", "Preço da caixa"};
        DefaultTableModel model = new DefaultTableModel();
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

        JTextField nomeCampo = new JTextField(10);
        JTextField produtosCaixaCampo = new JTextField(10);
        JTextField quantidadeCaixasCampo = new JTextField(10);
        JTextField precoCaixaCampo = new JTextField(10);

        nomePainel.add(nome);
        nomePainel.add(nomeCampo);

        produtosCaixaPainel.add(produtosCaixa);
        produtosCaixaPainel.add(produtosCaixaCampo);

        quantidadeCaixasPainel.add(quantidadeCaixas);
        quantidadeCaixasPainel.add(quantidadeCaixasCampo);

        precoCaixaPainel.add(precoCaixa);
        precoCaixaPainel.add(precoCaixaCampo);

        JButton adicionarReposicao = new JButton("Adicionar");
        String[] produtoAdicionado = new String[4];
        adicionarReposicao.addActionListener(actionEvent -> {
            produtoAdicionado[0] = nomeCampo.getText();
            produtoAdicionado[1] = produtosCaixaCampo.getText();
            produtoAdicionado[2] = quantidadeCaixasCampo.getText();
            produtoAdicionado[3] = precoCaixaCampo.getText();

            model.addRow(produtoAdicionado);

            nomeCampo.setText("");
            produtosCaixaCampo.setText("");
            quantidadeCaixasCampo.setText("");
            precoCaixaCampo.setText("");
        });

        reposicao.add(scroll);
        reposicao.add(nomePainel);
        reposicao.add(produtosCaixaPainel);
        reposicao.add(quantidadeCaixasPainel);
        reposicao.add(precoCaixaPainel);
        reposicao.add(adicionarReposicao);
        panel.add(reposicao);


    }

}
