package br.unicamp.laricaco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.Flow;

public class JanelaPrincipal extends JFrame {

    private JTextField quantiaDeposito;
    private Usuario usuario;
    private JLabel ra, saldo;
    private JCheckBox cartaoCredito, cartaoDebito, dinheiro;
    private float total;
    private JLabel totalLabel;

    public JanelaPrincipal(Usuario usuario) {

        super("LariCACo!");
        this.usuario = usuario;
        total = 0;
        totalLabel = new JLabel("R$ 0.00");

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        JPanel dados = new JPanel();
        dados.setLayout(new BoxLayout(dados, BoxLayout.Y_AXIS));
        ra = new JLabel("RA: " + usuario.getRA());
        dados.add(ra);
        saldo = new JLabel("Saldo: " + usuario.getSaldo());
        dados.add(saldo);
        dados.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JPanel deposito = new JPanel(new FlowLayout());
        JButton botaoDepositar = new JButton("Creditar");
        quantiaDeposito = new JTextField(10);
        botaoDepositar.addActionListener(new Creditar());
        deposito.add(botaoDepositar);
        deposito.add(quantiaDeposito);

        JPanel checkBoxes = new JPanel(new FlowLayout());
        CheckBoxHandler tratador = new CheckBoxHandler();
        cartaoCredito = new JCheckBox("Cartao De Credito");
        cartaoDebito = new JCheckBox("Cartao De Debito");
        dinheiro = new JCheckBox("Dinheiro");
        cartaoCredito.addItemListener(tratador);
        cartaoDebito.addItemListener(tratador);
        dinheiro.addItemListener(tratador);
        checkBoxes.add(cartaoCredito);
        checkBoxes.add(cartaoDebito);
        checkBoxes.add(dinheiro);

        JPanel produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));
        JScrollPane produtosScroll = new JScrollPane(produtosPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        produtosScroll.setPreferredSize(new Dimension(500, 300));

        for (Produto p : Produto.getProdutos()) {

            JLabel qntEmEstoque = new JLabel(p.getNome() + " R$" + p.getPrecoVenda() +
                                            "                                    " + p.getEstoque());
            JPanel produtoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            produtoPanel.add(qntEmEstoque);
            JTextField quantidade = new JTextField(8);
            JButton botaoCarrinho = new JButton(new ImageIcon("images/carrinho.png"));
            botaoCarrinho.addActionListener(e -> {
                int qnt = Integer.parseInt(quantidade.getText());
                for (int i = 0; i < qnt; i++) {
                    usuario.getCarrinho().adicionarProduto(p);
                }
                total += p.getPrecoVenda() * qnt;
                totalLabel.setText("R$ " + Float.toString(total));
                quantidade.setText("");
                qntEmEstoque.setText((Integer.parseInt(qntEmEstoque.getText()) - qnt) + "");

                // NAO PODE FINALIZAR SE N TEM DINHEIRO
                // SUBTRAIR DO ESTOQUE
                // NAO PODE ADD AO CARRINHO SE NAO TIVER NO ESTOQUE
            });

            produtoPanel.add(quantidade);
            produtoPanel.add(botaoCarrinho);
            produtosPanel.add(produtoPanel);
        }

        JPanel finalizar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        finalizar.add(totalLabel);
        finalizar.add(new JButton("Esvaziar Carrinho")); //volta os itens pro estoque
        finalizar.add(new JButton("Finalizar Compra")); //subtrai do estoque de fato


        getContentPane().add(dados);
        getContentPane().add(deposito);
        getContentPane().add(checkBoxes);
        getContentPane().add(produtosScroll);
        getContentPane().add(finalizar);

        if (usuario instanceof UsuarioAdministrador) {
            JButton administrar = new JButton("Administrar");
            getContentPane().add(administrar);
            administrar.addActionListener(e -> {
                JanelaAdministrador janela = new JanelaAdministrador();
                JanelaPrincipal.this.setVisible(false);
                janela.pack();
                janela.setVisible(true);
                janela.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        janela.dispose();
                        JanelaPrincipal.this.getWindowListeners()[0].windowClosing(e);
                    }
                });

            });
        }

    }

    class Creditar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!quantiaDeposito.getText().equals("")) {

                usuario.creditar(Float.parseFloat(quantiaDeposito.getText()));
                quantiaDeposito.setText("");
                saldo.setText("Saldo: " + usuario.getSaldo());
            }

        }
    }

    class Debitar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (!quantiaDeposito.getText().equals("")) {

                //????
                quantiaDeposito.setText("");
                saldo.setText("Saldo: " + usuario.getSaldo());
            }

        }
    }

    class CheckBoxHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {

        }
    }

}