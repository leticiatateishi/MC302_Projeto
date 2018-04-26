public class Main {

    public static void main(String[] args) {
        /*
         * Cadastro do usuário administrador no sistema.
         */
        UsuarioAdministrador administrador = new UsuarioAdministrador(186154, 1212, "rafael@gmail.com");

        Produto amendoim = new Produto("Amendoim", 0.5f, 17.5f, 50);
        Produto pirulito = new Produto("Pirulito", 0.25f, 10f, 50);
        Produto pacoca = new Produto("Pacoca", 0.5f, 25f, 60);
        // Criar trento e refrigerante como produtos especiais

        Reposicao reposicao1 = administrador.fazerReposicao();
        reposicao1.adicionarProduto(amendoim, 2);
        reposicao1.adicionarProduto(pirulito, 3);
        reposicao1.adicionarProduto(pacoca, 1);

        System.out.println("Produtos apos a primeira reposicao:\n" + amendoim + pirulito + pacoca + "\n");

        /*
         * Cadastro de outros usuários no sistema.
         */
        Usuario usuario1 = new Usuario(201454, 1020, "leticia@gmail.com");
        Usuario usuario2 = new Usuario(198625, 1234, "gustavo@gmail.com");

        usuario1.creditar(20.0D);
        System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + ": R$" + usuario1.getSaldo());

        Compra compra1 = usuario1.fazerCompra();
        compra1.adicionarProduto(pirulito, 2);
        compra1.adicionarProduto(pacoca, 5);
        System.out.println(compra1);

        System.out.println("Saldo do usuário com R.A. " + usuario1.getRA() + " apos a compra: R$" + usuario1.getSaldo());
    }
}
