import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Lanches {
    public static String[] listarLanchesComoMenu() {
        ArrayList<String> lanchesFormatados = new ArrayList<>();
        DecimalFormat formatarQuantidade = new DecimalFormat("0.#");

        String sql = "SELECT nome, quantidade_preço, medida, preço FROM lanches";

        try (Connection conn = DriverManager.getConnection(BancoDeDados.URL, BancoDeDados.USUARIO, BancoDeDados.SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                double qtd = rs.getDouble("quantidade_preço");
                String medida = rs.getString("medida");
                double preco = rs.getDouble("preço");

                String linha = nome + " " + formatarQuantidade.format(qtd) + " " + medida + " R$ " + String.format("%.2f", preco);
                lanchesFormatados.add(linha);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lanchesFormatados.toArray(new String[0]);
    }
}
