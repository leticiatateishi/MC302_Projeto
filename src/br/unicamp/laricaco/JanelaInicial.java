package br.unicamp.laricaco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JanelaInicial extends JFrame {
    private final Main main;

    public JanelaInicial(Main main) {
        super("LariCACo");

        this.main = main;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // Nao queremos que os usuarios fechem o programa

        JLabel aux = new JLabel();
        ImageIcon kermit = new ImageIcon("images/kermit.jpg");
        aux.setIcon(kermit);
        aux.setAlignmentX(CENTER_ALIGNMENT);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton entrar = new JButton("Já tenho uma conta");
        entrar.addActionListener(new TelaLogin());
        botoes.add(entrar);


        JButton novoUsuario = new JButton("Criar novo usuário");
        novoUsuario.addActionListener(new NovoUsuario());
        botoes.add(novoUsuario);

        panel.add(aux);
        panel.add(botoes);
        getContentPane().add(panel);
        this.setVisible(true);
    }

    public void close() {
        JanelaInicial.this.setVisible(true);
    }

    class TelaLogin implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JanelaLogin login = new JanelaLogin(main, JanelaInicial.this);
            JanelaInicial.this.setVisible(false);
            login.pack();
            login.setLocationRelativeTo(null);
            login.setVisible(true);
            login.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    login.dispose();
                    close();
                }
            });
        }
    }

    class NovoUsuario implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JanelaNovoUsuario novoUsuario = new JanelaNovoUsuario(main, JanelaInicial.this);
            JanelaInicial.this.dispose();
            novoUsuario.setLocationRelativeTo(null);
            novoUsuario.pack();
            novoUsuario.setVisible(true);
            novoUsuario.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    novoUsuario.dispose();
                    JanelaInicial.this.setVisible(true);
                }
            });
        }
    }
}
