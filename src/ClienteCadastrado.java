public class ClienteCadastro {
    static void acessarCadastro() {
        System.out.println("\nAcessando cadastro...");

        String cpfCadastro = Inputs.validarInputStr("CPF: ", Inputs.TipoEntrada.CPF, 12);
        String senhaCadastro = Inputs.validarInputStr("SENHA: ", Inputs.TipoEntrada.SENHA, 10);

        BancoDeDados.autenticarClienteBancoDeDados(cpfCadastro, senhaCadastro);
    }

    static boolean iniciarSessao() {
        System.out.println("cliente cadastro");
        return true;
    }
}
