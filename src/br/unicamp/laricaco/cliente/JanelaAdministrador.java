package br.unicamp.laricaco.cliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaAdministrador extends JFrame{


    public JanelaAdministrador(){
        super("Usuário administrador");
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JButton button = new JButton("Fazer reposição");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        JPanel estatisticas = new JPanel();
        estatisticas.setLayout(new BoxLayout(estatisticas, BoxLayout.Y_AXIS));
        JScrollPane estatisticasScroll = new JScrollPane(estatisticas, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                         JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(estatisticas);
        panel.add(button);
        setContentPane(panel);

    }
}
