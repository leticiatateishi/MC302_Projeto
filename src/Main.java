public class Main {
    public static void main(String[] args){

        /**
         * Cadastro do usuário administrador no sistema.
         */
        UsuarioAdministrador administrador = new UsuarioAdministrador(186154, 1212, "rafael@gmail.com");

        Produto amendoim = new Produto("Amendoim", 0.5, 17.5, 50);
        Produto pirulito = new Produto("Pirulito", 0.25, 10, 50);
        Produto pacoca = new Produto("Pacoca", 0.5, 25, 60);
        // Criar trento e refrigerante como produtos especiais

        administrador.fazerReposicao();
        // Precisamos passar o produto e a quantidade de caixas compradas no metodo fazerReposicao

        /**
         * Cadastro de outros usuários no sistema.
         */
        Usuario usuario1 = new Usuario(201454, 1020, "leticia@gmail.com");
        Usuario usuario2 = new Usuario(198625, 1234, "gustavo@gmail.com");

        usuario1.creditar(20.0D);
        System.out.println("Saldo do usuário com R.A. " +usuario1.getRA()+ ": " +usuario1.getSaldo());
        Compra compra1 = usuario1.fazerCompra();
        compra1.adicionarProduto()


    }
}
