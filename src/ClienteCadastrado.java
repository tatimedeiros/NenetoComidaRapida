import java.sql.*;

public class ClienteCadastrado {
    static void acessarCadastro() {
        System.out.println("\nAcessando cadastro...");

        String cpfCadastro = Inputs.validarInputStr("CPF: ", Inputs.TipoEntrada.CPF, 12);
        String senhaCadastro = Inputs.validarInputStr("SENHA: ", Inputs.TipoEntrada.SENHA, 10);

        BancoDeDados.autenticarClienteBancoDeDados(cpfCadastro, senhaCadastro);
    }

    static void iniciarSessaoClienteCadastrado() {
        System.out.println("\nExibindo o cardápio...");
    }

    public static void processoCadastroOuLogin() {
        String cpf = Inputs.validarInputStr("CPF (12345678912 ou 123456789-12):", Inputs.TipoEntrada.CPF, null, 12);

        String sqlConsulta = "SELECT senha, nome FROM clientes WHERE cpf = ?";

        try (Connection conn = DriverManager.getConnection(BancoDeDados.URL, BancoDeDados.USUARIO, BancoDeDados.SENHA);
             PreparedStatement stmt = conn.prepareStatement(sqlConsulta)) {

            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("\n***** CPF já cadastrado! *****");
                    String senhaBanco = rs.getString("senha");
                    String nome = rs.getString("nome");

                    String senhaInput = Inputs.validarInputStr("Senha:", Inputs.TipoEntrada.SENHA, null, 10);

                    if (senhaInput.equals(senhaBanco)) {
                        System.out.println("\n✅ Login realizado com sucesso! Bem vindo, " + nome);
                        ClienteCadastrado.iniciarSessaoClienteCadastrado();
                    } else {
                        System.out.println("\n⚠️ Senha incorreta!");
                        ClienteAnonimo.iniciarSessaoClienteAnonimo();
                    }

                } else {
                    /*System.out.println("\n***** Este CPF não está atrelado a nenhuma conta! *****");*/
                    System.out.println("\nVamos iniciar o seu cadastro...");
                    String nome = Inputs.validarInputStr("Nome:", Inputs.TipoEntrada.NOME, null, 100);
                    String senha = Inputs.validarInputStr("Senha:", Inputs.TipoEntrada.SENHA, null, 10);

                    String sqlInsercao = "INSERT INTO clientes (nome, cpf, senha, data_cadastro) VALUES (?, ?, ?, CURRENT_DATE)";
                    try (PreparedStatement stmtInsercao = conn.prepareStatement(sqlInsercao)) {
                        stmtInsercao.setString(1, nome);
                        stmtInsercao.setString(2, cpf);
                        stmtInsercao.setString(3, senha);

                        int rowsInserted = stmtInsercao.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("\n✅ Cadastro realizado com sucesso! Bem vindo, " + nome);
                            ClienteCadastrado.iniciarSessaoClienteCadastrado();
                        } else {
                            System.out.println("\n❌ Falha ao cadastrar cliente.");
                            ClienteAnonimo.iniciarSessaoClienteAnonimo();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("\n❌ Erro ao conectar com o banco de dados...: " + e.getMessage());
            ClienteAnonimo.iniciarSessaoClienteAnonimo();
        }
    }
}