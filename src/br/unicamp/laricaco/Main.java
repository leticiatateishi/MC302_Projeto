package br.unicamp.laricaco;

public class Main {

    public static void main(String[] args) {
        /*
         * Cadastro do usuário administrador no sistema.
         */

        GerenciadorUsuario gerenciador = new GerenciadorUsuario();
        UsuarioAdministrador administrador = new UsuarioAdministrador(186154, 1212, "rafael@gmail.com");

        Produto amendoim = new Produto("Amendoim", 0.5f, 17.5f, 50);
        Produto pirulito = new Produto("Pirulito", 0.25f, 10f, 50);
        Produto pacoca = new Produto("Pacoca", 0.5f, 25f, 60);
        ProdutoEspecial trento = new ProdutoEspecial("Trento", 1.25f, 15f, 16);
        Produto trentoBranco = trento.adicionarVariacaoProduto("branco");
        Produto trentoAmargo = trento.adicionarVariacaoProduto("amargo");

        Reposicao reposicao1 = administrador.fazerReposicao();
        reposicao1.adicionarProduto(amendoim, 2);
        reposicao1.adicionarProduto(pirulito, 3);
        reposicao1.adicionarProduto(pacoca, 1);

        System.out.println("Produtos apos a primeira reposicao:\n" + amendoim + pirulito + pacoca + trentoAmargo
                + trentoBranco + "\n");

        /*
         * Cadastro de outros usuários no sistema.
         */
        Usuario usuario1 = new Usuario(201454, 1020, "leticia@gmail.com");
        Usuario usuario2 = new Usuario(198625, 1234, "gustavo@gmail.com");

        usuario1.creditar(20.0f);
        System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + ": R$" + usuario1.getSaldo());

        Compra compra1 = usuario1.fazerCompra();

        /*
         * Adicionamos a compra dois produtos existentes no estoque.
         */
        compra1.adicionarProduto(pirulito, 2);
        compra1.adicionarProduto(pacoca, 5);

        /*
         * Tentamos adicionar um produto nao existente no estoque.
         */
        if (!compra1.adicionarProduto(trentoAmargo, 2)) {
            System.out.println("Trento amargo nao disponivel no estoque.");
        }

        System.out.println(compra1);

        System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + " apos a compra: R$" + usuario1.getSaldo()
                + "\n");

        /*
         * Adicionamos uma variante do produto especial trento.
         */
        Reposicao reposicao2 = administrador.fazerReposicao();
        if (reposicao2.adicionarProduto(trentoAmargo, 4)) {
            System.out.println("Trento adicionado ao estoque.\n");
        }

        Compra compra2 = usuario2.fazerCompra();
        compra2.adicionarProduto(trentoAmargo, 3);
        System.out.println(compra2);
        System.out.println("Saldo do usuário com R.A. " + usuario2.getRA() + " apos a compra: R$" + usuario2.getSaldo());
        usuario2.creditar(5.0f);
        System.out.println("Saldo do usuário com R.A. " + usuario2.getRA() + " apos deposito de R$5,00: R$"
                + usuario2.getSaldo());

        System.out.println("\nProdutos apos compras e reposicoes:\n" + amendoim + pirulito + pacoca + trentoAmargo
                + trentoBranco);

        System.out.println("\nPara ser consistente, a alteração de senha deve retornar false e true:");
        System.out.println(usuario1.alterarSenha(1021, 5555));
        System.out.println(usuario1.alterarSenha(1020, 5555));

        JanelaLogin janela = new JanelaLogin();
        janela.pack();
        janela.setVisible(true);
    }
}
