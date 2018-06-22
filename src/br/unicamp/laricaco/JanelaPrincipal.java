package br.unicamp.laricaco;

import br.unicamp.laricaco.estoque.Produto;
import br.unicamp.laricaco.estoque.ProdutoEspecial;
import br.unicamp.laricaco.usuario.Usuario;
import br.unicamp.laricaco.usuario.UsuarioAdministrador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JanelaPrincipal extends JFrame {

    private JTextField quantiaDeposito;
    private Usuario usuario;
    private JLabel ra, saldo;
    private JCheckBox cartaoCredito, cartaoDebito, dinheiro;
    private ArrayList<Produto> carrinho;
    private JLabel totalLabel;

    public JanelaPrincipal(Main main, Usuario usuario, JanelaLogin.Entrar entrar) {

        super("LariCACo!");
        this.usuario = usuario;
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

        //Para nao dar ruim com produto especial:
        ArrayList <Produto> produtos = new ArrayList<>();
        for (Produto p: main.getGerenciadorEstoque().getProdutos()){
            if(!p.isEspecial()){
                produtos.add(p);
            }
            else{
                produtos.addAll(((ProdutoEspecial) p).getVariacoes());
            }
        }

        ArrayList<JLabel> labels = new ArrayList<>();
        for (Produto p : produtos) {

            JLabel informacaoProduto = new JLabel(p.getNome() + " R$" + p.getPrecoVenda() +
                    "                                    ");
            JLabel qntEmEstoque = new JLabel("" + p.getEstoque());
            JPanel produtoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            produtoPanel.add(informacaoProduto);
            produtoPanel.add(qntEmEstoque);
            JTextField quantidade = new JTextField(8);
            JButton botaoCarrinho = new JButton(new ImageIcon("images/carrinho.png"));
            botaoCarrinho.addActionListener(e -> {
                int qnt = Integer.parseInt(quantidade.getText());
                try {
                    usuario.getCarrinho().adicionarProduto(p, qnt);
                    totalLabel.setText("R$ " + Float.toString(usuario.getCarrinho().getValor()));
                    qntEmEstoque.setText((Integer.parseInt(qntEmEstoque.getText()) - qnt) + "");
                } catch (LariCACoException e1) {
                    JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                } finally {
                    quantidade.setText("");
                }
            });

            produtoPanel.add(quantidade);
            produtoPanel.add(botaoCarrinho);
            produtosPanel.add(produtoPanel);
        }


        JPanel finalizar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        finalizar.add(totalLabel);
        JButton esvaziarCarrinho = new JButton("Esvaziar Carrinho");
        finalizar.add(esvaziarCarrinho); //volta os itens pro estoque
        JButton finalizarCompra = new JButton("Finalizar Compra");
        finalizar.add(finalizarCompra); //subtrai do estoque de fato

        esvaziarCarrinho.addActionListener(e -> {
            usuario.getCarrinho().esvaziarCarrinho();
            totalLabel.setText("R$ " + Float.toString(usuario.getCarrinho().getValor()));
            //voltar quantidade do disponivel
        });

        finalizarCompra.addActionListener(e -> {
            try {
                usuario.getCarrinho().finalizarCompra();
                totalLabel.setText(("R$ 0.00"));
                saldo.setText("Saldo: " + usuario.getSaldo());
                if(usuario.getSaldo() < 0){
                    JOptionPane.showMessageDialog(this, "Não compre fiado!", "Fiado", JOptionPane.WARNING_MESSAGE);
                    System.out.println(main.getGerenciadorEstoque().toString());
                }
            }catch (LariCACoException e1){
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        });


        getContentPane().add(dados);
        getContentPane().add(deposito);
        getContentPane().add(checkBoxes);
        getContentPane().add(produtosScroll);
        getContentPane().add(finalizar);

        if (usuario instanceof UsuarioAdministrador) {
            JButton administrar = new JButton("Administrar");
            administrar.setAlignmentX(CENTER_ALIGNMENT);
            getContentPane().add(administrar);
            administrar.addActionListener(e -> {
                JanelaAdministrador janela = new JanelaAdministrador((UsuarioAdministrador) usuario, main.getGerenciadorEstoque());
                JanelaPrincipal.this.setVisible(false);
                janela.setSize(700,500);
                janela.setVisible(true);
                janela.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        janela.dispose();
                        dispose();
                        entrar.criarJanelaPrincipal();
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
