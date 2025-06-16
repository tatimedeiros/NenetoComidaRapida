import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("""
                ***********
                 \uD83E\uDD16NENETO
                ***********
                """);

        Menus.setMenuInicial(new String[]{
                "Quero fazer login",
                "Entrar sem login",
                "Sair"
        });

        String lanches = Menus.setMenuLanches();
        String bebidas = Menus.setMenuBebidas();
        String sobremesas = Menus.setMenuSobremesas();

        scanner.close();

        System.out.println(Menus.cardapioEscolhidoMenu(lanches, bebidas, sobremesas));
    }
}