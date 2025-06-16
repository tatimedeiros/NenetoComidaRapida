import java.sql.*;
import java.util.ArrayList;

public class Sobremesas {
    public static String[] listarSobremesasComoMenu() {
        ArrayList<String> sobremesasFormatadas = new ArrayList<>();

        String sql = "SELECT nome, preço FROM sobremesas";

        try (Connection conn = DriverManager.getConnection(BancoDeDados.URL, BancoDeDados.USUARIO, BancoDeDados.SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preço");

                String linha = nome + " " + " R$ " + String.format("%.2f", preco);
                sobremesasFormatadas.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sobremesasFormatadas.toArray(new String[0]);
    }
}