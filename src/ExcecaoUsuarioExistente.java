public class ExcecaoUsuarioExistente extends Exception {
    ExcecaoUsuarioExistente(){
        super("O usuário já existe e não é administrador.");
    }
}
