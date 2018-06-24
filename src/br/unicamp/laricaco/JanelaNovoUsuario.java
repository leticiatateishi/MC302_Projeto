package br.unicamp.laricaco;

import br.unicamp.laricaco.utilidades.LariCACoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaNovoUsuario extends JFrame {

    private final Main main;
    private JTextField campoLogin, campoPergunta, campoResposta;
    private JPasswordField campoSenha, campoSenha2;

    public JanelaNovoUsuario(Main main) {
        super("Criar Usuario");

        this.main = main;

        JPanel novoUsuario = new JPanel();
        novoUsuario.setLayout(new BoxLayout(novoUsuario, BoxLayout.Y_AXIS));

        JLabel RA = new JLabel("Adicione seu número de RA:");
        RA.setAlignmentX(CENTER_ALIGNMENT);
        campoLogin = new JTextField(7);
        RA.setLabelFor(campoLogin);


        JLabel Pin = new JLabel("Escolha uma senha:");
        Pin.setAlignmentX(CENTER_ALIGNMENT);
        campoSenha = new JPasswordField(5);
        Pin.setLabelFor(campoSenha);


        JLabel Pin2 = new JLabel("Confirme sua senha:");
        Pin2.setAlignmentX(CENTER_ALIGNMENT);
        campoSenha2 = new JPasswordField(5);
        Pin2.setLabelFor(campoSenha2);

        JLabel Pergunta = new JLabel("Crie uma pergunta de recuperação de senha:");
        Pergunta.setAlignmentX(CENTER_ALIGNMENT);
        campoPergunta = new JTextField(30);
        campoPergunta.setToolTipText("Não adicione o ponto de interrogação");
        Pergunta.setLabelFor(campoPergunta);

        JLabel Resposta = new JLabel(" Defina uma resposta:");
        Resposta.setAlignmentX(CENTER_ALIGNMENT);
        campoResposta = new JTextField(30);
        Resposta.setLabelFor(campoResposta);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));


        JButton criarNovoUsuario = new JButton("Finalizar criação");
        criarNovoUsuario.addActionListener(new NovoUsuario());
        botoes.add(criarNovoUsuario);

        novoUsuario.add(RA);
        novoUsuario.add(campoLogin);
        novoUsuario.add(Pin);
        novoUsuario.add(campoSenha);
        novoUsuario.add(Pin2);
        novoUsuario.add(campoSenha2);
        novoUsuario.add(Pergunta);
        novoUsuario.add(campoPergunta);
        novoUsuario.add(Resposta);
        novoUsuario.add(campoResposta);
        novoUsuario.add(botoes);

        getContentPane().add(novoUsuario);
        this.setVisible(true);

    }

    class NovoUsuario implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if (Integer.parseInt(String.valueOf(campoSenha2.getPassword())) !=
                        Integer.parseInt(String.valueOf(campoSenha.getPassword()))) {
                    throw new LariCACoException("As senhas não coincidem!");
                }

                main.getGerenciadorUsuario().adicionarUsuario(
                        Integer.parseInt(campoLogin.getText()),
                        Integer.parseInt(String.valueOf(campoSenha.getPassword())),
                        campoPergunta.getText(),
                        campoResposta.getText()
                );
                JOptionPane.showMessageDialog(JanelaNovoUsuario.this, "Usuário criado com sucesso!", "Parabéns!",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(JanelaNovoUsuario.this, e1.getMessage(), "Erro!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

