package br.unicamp.laricaco.servidor;

import br.unicamp.laricaco.estoque.*;
import br.unicamp.laricaco.usuario.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Servidor {

    /* Controladores de threads e rede */
    private final ThreadGroup threadGroup;
    private final ExecutorService threadPool;
    private final Thread serverThread;
    private final ServerSocket serverSocket;

    private volatile boolean pararConexoes = false;

    /* Controladores do programa */
    private final GerenciadorUsuario gerenciadorUsuario;
    private final GerenciadorEstoque gerenciadorEstoque;

    private Servidor() throws IOException {
        /* Carregamos o programa */
        gerenciadorUsuario = new GerenciadorUsuario();
        gerenciadorEstoque = new GerenciadorEstoque();

        /* Criamos o socket de conexões e o thread */
        serverSocket = new ServerSocket(1323);
        serverThread = new Thread(new AbrirConexoes(), "Listener conexões");

        /* Criamos a pool de threads para receber informações de rede */
        threadGroup = new ThreadGroup("Conexões");
        threadPool = Executors.newCachedThreadPool(runnable -> new Thread(threadGroup, runnable));
    }

    public GerenciadorUsuario getGerenciadorUsuario() {
        return gerenciadorUsuario;
    }

    public GerenciadorEstoque getGerenciadorEstoque() {
        return gerenciadorEstoque;
    }

    private void atender() {
        System.out.println("Atendendo conexões...");
        serverThread.start();
    }

    private void fechar() throws InterruptedException, IOException {
        System.out.println("Saindo...");
        pararConexoes = true;
        serverSocket.close();
        serverThread.join();
        threadPool.shutdownNow();
        System.out.println("Threads fechados");
    }

    public static void main(String[] args) {
        try {

            Servidor servidor = new Servidor();
            servidor.atender();
            System.out.println("Digite exit para sair");
            Scanner scannerComandos = new Scanner(System.in);
            while (true) {
                String linha = scannerComandos.nextLine();

                if (linha.toLowerCase().startsWith("exit")) {
                    servidor.fechar();
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Tchau!");
    }

    private class AbrirConexoes implements Runnable {

        @Override
        public void run() {
            while (!pararConexoes && !serverSocket.isClosed()) {
                try {
                    /* Travamos a thread para receber conexões */
                    Socket socket = serverSocket.accept();

                    /* Adicionamos o socket na lista de conexões a serem atendidas na thread principal */
                    threadPool.submit(new AtenderConexao(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class AtenderConexao implements Runnable {

        private final Socket socket;

        public AtenderConexao(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                /* Configuramos a socket */
                socket.setKeepAlive(true);
                socket.setSoTimeout(3000); /* 3 mil milisegundos, 3 segundos */

                /* Criamos as streams */
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                while (!pararConexoes && !socket.isClosed()) {
                    /* Enviamos os pacotes da lista */
                    outputStream.flush();

                    /* Interpretamos os pacotes */

                    /* Colocamos o pacote na thread principal do programa */

                }
            } catch (EOFException ignored) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
