package br.unicamp.laricaco.cliente;

import java.io.*;
import java.net.*;

public class Cliente {

    /* Controle de conexão */
    private final Thread clienteThread;

    private Cliente() {
        /* Criamos a thread que fala com o servidor */
        clienteThread = new Thread(new ConexaoServidor(), "Conexão");
    }

    private void conectar() {
        clienteThread.start();
    }

    public static void main(String[] args) {
        JanelaLogin janela = new JanelaLogin();
        janela.pack();
        janela.setVisible(true);

//        Cliente cliente = new Cliente();
//        cliente.conectar();
//        try {
//            cliente.clienteThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Tchau!");
    }

    private class ConexaoServidor implements Runnable {

        @Override
        public void run() {
            try {
                Socket socket = new Socket("localhost", 1323);
                socket.setKeepAlive(true);

                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                int integer = 0;
                while (!socket.isClosed()) {
                    /* Enviamos pacotes da fila */
                    outputStream.writeInt(integer++);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    /* Interpretamos pacotes */

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
