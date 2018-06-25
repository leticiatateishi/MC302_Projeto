package br.unicamp.laricaco;

import br.unicamp.laricaco.usuario.Usuario;
import br.unicamp.laricaco.utilidades.LariCACoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JanelaLogin extends JFrame {

    private final Main main;
    private final JanelaInicial janelaInicial;

    private JTextField campoLogin;
    private JPasswordField campoSenha;

    public JanelaLogin(Main main, JanelaInicial janelaInicial) {
        super("Login");
        this.janelaInicial = janelaInicial;

        this.main = main;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //Nao queremos que os usuarios fechem o programa


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel login = new JLabel("RA: ");
        login.setAlignmentX(CENTER_ALIGNMENT);
        campoLogin = new JTextField(8);
        login.setLabelFor(campoLogin);

        JLabel senha = new JLabel("Senha: ");
        senha.setAlignmentX(CENTER_ALIGNMENT);
        campoSenha = new JPasswordField(8);
        senha.setLabelFor(campoSenha);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton entrar = new JButton("Entrar");
        entrar.addActionListener(new Entrar());
        entrar.setMnemonic(KeyEvent.VK_ENTER);
        botoes.add(entrar);

        JButton trocarSenha = new JButton("Esqueci PIN");
        trocarSenha.addActionListener(new EsqueciSenha());
        botoes.add(trocarSenha);


        panel.add(login);
        panel.add(campoLogin);
        panel.add(senha);
        panel.add(campoSenha);
        panel.add(botoes);
        getContentPane().add(panel);
        this.setVisible(true);
    }

    public class Entrar implements ActionListener {

        int ra, pin;
        Usuario usuario;

        @Override
        public void actionPerformed(ActionEvent e) {
            ra = Integer.parseInt(campoLogin.getText());
            pin = Integer.parseInt(String.valueOf(campoSenha.getPassword()));

            usuario = main.getGerenciadorUsuario().getUsuario(ra);
            if (usuario != null && usuario.getPin() == pin) {
                criarJanelaPrincipal();
            } else if (usuario == null) {
                JOptionPane.showMessageDialog(JanelaLogin.this, "Insira Usuario", "Usuario", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(JanelaLogin.this, "Senha Incorreta", "Senha", JOptionPane.ERROR_MESSAGE);
                campoSenha.setText("");
            }
        }

        public void criarJanelaPrincipal() {
            JanelaPrincipal janela = new JanelaPrincipal(main, usuario, this);
            JanelaLogin.this.setVisible(false);
            janela.pack();
            janela.setVisible(true);
            janela.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    janela.dispose();
                    try {
                        main.salvar();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(
                                JanelaLogin.this,
                                "Falha ao salvar banco de dados no arquivo!",
                                "Erro!",
                                JOptionPane.WARNING_MESSAGE
                        );
                        e1.printStackTrace();
                    }
                    campoLogin.setText("");
                    campoSenha.setText("");
//                    JanelaLogin.this.setVisible(true);
                    janelaInicial.close();
                }
            });
        }
    }

    class EsqueciSenha implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Usuario usuario = main.getGerenciadorUsuario().getUsuario(Integer.parseInt(campoLogin.getText()));
            if (usuario != null) {
                try {
                    int codigo = usuario.pedirTrocaSenha(JOptionPane.showInputDialog(
                            JanelaLogin.this,
                            usuario.getPergunta() + "?",
                            "Recuperação de senha",
                            JOptionPane.INFORMATION_MESSAGE
                    ));
                    JOptionPane.showMessageDialog(
                            JanelaLogin.this,
                            "Mostre esse código junto com seu RA ao administrador: " + codigo,
                            "Sucesso!",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (LariCACoException e1) {
                    JOptionPane.showMessageDialog(
                            JanelaLogin.this, e1.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


}
