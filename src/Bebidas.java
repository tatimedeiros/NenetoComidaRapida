import java.sql.*;
import java.util.ArrayList;

public class Bebidas {
    public static String[] listarBebidasComoMenu() {
        ArrayList<String> bebidasFormatadas = new ArrayList<>();

        String sql = "SELECT nome, preço FROM bebidas";

        try (Connection conn = DriverManager.getConnection(BancoDeDados.URL, BancoDeDados.USUARIO, BancoDeDados.SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                double preco = rs.getDouble("preço");

                String linha = nome + " " + " R$ " + String.format("%.2f", preco);
                bebidasFormatadas.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bebidasFormatadas.toArray(new String[0]);
    }
}