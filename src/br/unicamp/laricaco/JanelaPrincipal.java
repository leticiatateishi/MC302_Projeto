package br.unicamp.laricaco;

import br.unicamp.laricaco.estoque.Produto;
import br.unicamp.laricaco.estoque.ProdutoEspecial;
import br.unicamp.laricaco.estoque.TipoPagamento;
import br.unicamp.laricaco.usuario.Usuario;
import br.unicamp.laricaco.usuario.UsuarioAdministrador;
import br.unicamp.laricaco.utilidades.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JanelaPrincipal extends JFrame {

    private JTextField quantiaDeposito;
    private Usuario usuario;
    private JLabel ra, saldo;
    private JRadioButton cartaoCredito, cartaoDebito, dinheiro;
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

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        ButtonGroup radioButtons = new ButtonGroup();
        cartaoCredito = new JRadioButton("Cartao de Credito");
        cartaoDebito = new JRadioButton("Cartao de Debito");
        dinheiro = new JRadioButton("Dinheiro");
        radioButtons.add(dinheiro);
        radioButtons.add(cartaoCredito);
        radioButtons.add(cartaoDebito);
        buttonsPanel.add(dinheiro);
        buttonsPanel.add(cartaoCredito);
        buttonsPanel.add(cartaoDebito);

        JPanel produtosPanel = new JPanel();
        produtosPanel.setLayout(new BoxLayout(produtosPanel, BoxLayout.Y_AXIS));
        JScrollPane produtosScroll = new JScrollPane(
                produtosPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        produtosScroll.setPreferredSize(new Dimension(500, 300));


        ArrayList<EntradaProduto> entradaProdutos = new ArrayList<>();
        for (Produto p : main.getGerenciadorEstoque().getProdutos()) {
            if (!p.isEspecial()) {
                entradaProdutos.add(new EntradaProduto(p, produtosPanel));
            } else {
                for (Produto produto : ((ProdutoEspecial) p).getVariacoes()) {
                    entradaProdutos.add(new EntradaProduto(produto, produtosPanel));
                }
            }
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
            for (EntradaProduto entradaProduto : entradaProdutos) {
                entradaProduto.atualizarQntEmEstoque();
            }
        });

        finalizarCompra.addActionListener(e -> {
            try {
                usuario.getCarrinho().finalizarCompra();
                totalLabel.setText(("R$ 0.00"));
                saldo.setText("Saldo: " + usuario.getSaldo());
                if (usuario.getSaldo() < 0) {
                    JOptionPane.showMessageDialog(this, "Não compre fiado!", "Fiado", JOptionPane.WARNING_MESSAGE);
                }
            } catch (LariCACoException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        });


        getContentPane().add(dados);
        getContentPane().add(deposito);
        getContentPane().add(buttonsPanel);
        getContentPane().add(produtosScroll);
        getContentPane().add(finalizar);

        if (usuario.isAdministrador()) {
            JButton administrar = new JButton("Administrar");
            getContentPane().add(administrar);
            administrar.addActionListener(e -> {
                JanelaAdministrador janela = new JanelaAdministrador(
                        ((UsuarioAdministrador) usuario), main.getGerenciadorEstoque());
                JanelaPrincipal.this.setVisible(false);
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
        public void actionPerformed(ActionEvent e){

            TipoPagamento tipoPagamento;

            try {

                if (dinheiro.isSelected()) {
                    tipoPagamento = TipoPagamento.DINHEIRO;
                } else if (cartaoCredito.isSelected()) {
                    tipoPagamento = TipoPagamento.CARTAO_CREDITO;
                } else if (cartaoDebito.isSelected()) {
                    tipoPagamento = TipoPagamento.CARTAO_DEBITO;
                } else {
                    throw new LariCACoException("Escolha a forma de pagamento!");
                }

                if (!quantiaDeposito.getText().equals("")) {

                     usuario.creditar(Float.parseFloat(quantiaDeposito.getText()), tipoPagamento);
                     quantiaDeposito.setText("");
                     saldo.setText("Saldo: " + usuario.getSaldo());
                }
            }catch (LariCACoException ex){
                JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Pagamento", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class EntradaProduto {

        private final Produto p;
        private JLabel qntEmEstoque;

        EntradaProduto(Produto produto, JPanel produtosPanel) {
            this.p = produto;
            JLabel informacaoProduto = new JLabel(p.getNome() + " R$" + p.getPrecoVenda() +
                    "                                    ");
            qntEmEstoque = new JLabel("" + p.getEstoque());
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
                    JOptionPane.showMessageDialog(
                            JanelaPrincipal.this, e1.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                } finally {
                    quantidade.setText("");
                }
            });

            produtoPanel.add(quantidade);
            produtoPanel.add(botaoCarrinho);
            produtosPanel.add(produtoPanel);
        }

        public void atualizarQntEmEstoque() {
            qntEmEstoque.setText((p.getEstoque() - usuario.getCarrinho().quantidadeProduto(p)) + "");
        }
    }
}
