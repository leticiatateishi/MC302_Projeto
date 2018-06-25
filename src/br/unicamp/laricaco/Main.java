package br.unicamp.laricaco;

import br.unicamp.laricaco.estoque.*;
import br.unicamp.laricaco.usuario.GerenciadorUsuario;
import br.unicamp.laricaco.usuario.Usuario;
import br.unicamp.laricaco.usuario.UsuarioAdministrador;
import br.unicamp.laricaco.utilidades.LariCACoException;

import java.io.*;

public class Main {

    private GerenciadorEstoque gerenciadorEstoque;
    private GerenciadorUsuario gerenciadorUsuario;

    public Main() throws IOException {
        boolean falhaCarregar = false;
        try (DataInputStream inputStream =
                     new DataInputStream(new BufferedInputStream(new FileInputStream("database.db")))) {
            gerenciadorEstoque = GerenciadorEstoque.carregar(inputStream);
            gerenciadorUsuario = GerenciadorUsuario.carregar(this, inputStream);
        } catch (FileNotFoundException e) {
            gerenciadorEstoque = new GerenciadorEstoque();
            gerenciadorUsuario = new GerenciadorUsuario(this);
            falhaCarregar = true;
        }

        if (falhaCarregar) {
            criarPadrao();
        }
    }

    public void salvar() throws IOException {
        DataOutputStream outputStream =
                new DataOutputStream(new BufferedOutputStream(new FileOutputStream("database.db")));
        gerenciadorEstoque.salvar(outputStream);
        gerenciadorUsuario.salvar(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    public GerenciadorEstoque getGerenciadorEstoque() {
        return gerenciadorEstoque;
    }

    public GerenciadorUsuario getGerenciadorUsuario() {
        return gerenciadorUsuario;
    }

    private void criarPadrao() {
        /*
         * Cadastro do usuário administrador no sistema.
         */
        try {
            Produto amendoim = gerenciadorEstoque.criarProduto("Amendoim", 0.5f, 17.5f, 50);
            Produto pirulito = gerenciadorEstoque.criarProduto("Pirulito", 0.25f, 10f, 50);
            Produto pacoca = gerenciadorEstoque.criarProduto("Pacoca", 0.5f, 25f, 60);
            ProdutoEspecial trento = gerenciadorEstoque.criarProdutoEspecial("Trento", 1.25f, 15f, 16);
            Produto trentoBranco = trento.adicionarVariacaoProduto("branco");
            Produto trentoAmargo = trento.adicionarVariacaoProduto("amargo");

            UsuarioAdministrador administrador =
                    gerenciadorUsuario.adicionarAdministrador(1, 1212, "qual seu animal favorito", "mel");
            Reposicao reposicao1 = administrador.fazerReposicao();
            reposicao1.adicionarProduto(amendoim, 2);
            reposicao1.adicionarProduto(pirulito, 3);
            reposicao1.adicionarProduto(pacoca, 1);

//            System.out.println("Produtos apos a primeira reposicao:\n" + amendoim + pirulito + pacoca + trentoAmargo
//                    + trentoBranco + "\n");

            /*
             * Cadastro de outros usuários no sistema.
             */
            Usuario usuario1 = gerenciadorUsuario.adicionarUsuario(201454, 1020, "qual seu animal favorito", "mel");
            Usuario usuario2 = gerenciadorUsuario.adicionarUsuario(198625, 1234, "qual seu animal favorito", "mel");

            usuario1.creditar(20.0f, MetodoPagamento.CARTAO_CREDITO);
//            System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + ": R$" + usuario1.getSaldo());

            Carrinho carrinho1 = usuario1.getCarrinho();

            /*
             * Adicionamos a compra dois produtos existentes no estoque.
             */
            carrinho1.adicionarProduto(pirulito, 2);
            carrinho1.adicionarProduto(pacoca, 5);

            /*
             * Tentamos adicionar um produto nao existente no estoque.
             */
            try {
                carrinho1.adicionarProduto(trentoAmargo, 2);
            } catch (LariCACoException e) {
//                System.out.println("Trento amargo não disponível no estoque");
            }

//            System.out.println(carrinho1);
//            System.out.println(carrinho1.finalizarCompra());

//            System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + " apos a compra: R$" +
//                    usuario1.getSaldo() + "\n");

            /*
             * Adicionamos uma variante do produto especial trento.
             */
            Reposicao reposicao2 = administrador.fazerReposicao();
            try {
                reposicao2.adicionarProduto(trentoAmargo, 4);
//                System.out.println("Trento adicionado ao estoque.\n");
            } catch (LariCACoException e) {
                e.printStackTrace();
            }

            Carrinho carrinho = usuario2.getCarrinho();
            carrinho.adicionarProduto(trentoAmargo, 3);
            Compra compra2 = carrinho.finalizarCompra();

//            System.out.println(compra2);
//            System.out.println("Saldo do usuário com R.A. " + usuario2.getRA() + " apos a compra: R$" +
//                    usuario2.getSaldo());
            usuario2.creditar(5.0f, MetodoPagamento.DINHEIRO);
//            System.out.println("Saldo do usuário com R.A. " + usuario2.getRA() + " apos deposito de R$5,00: R$"
//                    + usuario2.getSaldo());


//            System.out.println("\nProdutos apos compras e reposicoes:\n" + amendoim + pirulito + pacoca + trentoAmargo
//                    + trentoBranco);

//            System.out.println("\nPara ser consistente, a alteração de senha deve retornar false e true:");
//            System.out.println(usuario1.alterarSenha(1021, 5555));
//            System.out.println(usuario1.alterarSenha(1020, 5555));
        } catch (LariCACoException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();

        JanelaInicial janela = new JanelaInicial(main);
        janela.pack();
        janela.setVisible(true);
    }
}
