import java.sql.*;

public class BancoDeDados {

    static final String URL = "jdbc:mysql://127.0.0.1:3306/nenetodb";
    static final String USUARIO = "root";
    static final String SENHA = "1234";

    public static void autenticarClienteBancoDeDados(String cpf, String senha) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ? AND senha = ?";

        try (Connection conn = DriverManager.getConnection(
                BancoDeDados.URL,
                BancoDeDados.USUARIO,
                BancoDeDados.SENHA
        );
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1); // <-- aqui está a correção!
                confirmarClienteBandoDeDados(count > 0);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        confirmarClienteBandoDeDados(false);
    }

    public static void confirmarClienteBandoDeDados(boolean isCadastro) {
        if (isCadastro) {
            System.out.println("\nLogin realizado com sucesso!");
            ClienteCadastrado.iniciarSessaoClienteCadastrado();
        } else {
            System.out.println("\nAcesso inválido!" +
                    "Vamos iniciar uma sessão como usuário anônimo...");
            ClienteAnonimo.iniciarSessaoClienteAnonimo();
        }
    }
}