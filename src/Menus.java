import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menus {
    static String exibirMenu(String[] itemMenu) {
        StringBuilder menuStr = new StringBuilder();

        for (int i = 0; i < itemMenu.length; i++) {
            String linha = "(" + (i + 1) + ") " + itemMenu[i];
            menuStr.append(linha).append("\n");
        }
        return menuStr.toString();
    }

    static int setMenu(String[] menu) {
        int qtdItensMenu = menu.length;
        String itensMenu = exibirMenu(menu);

        return Inputs.validarInputNum(itensMenu, qtdItensMenu);
    }

    static void setMenuInicial(String[] menu) {
        switch (setMenu(menu)) {
            case 1 -> {
                switch (setMenu(new String[]{
                        "Já tenho cadastro",
                        "Criar cadastro",
                        "Sair"
                })) {
                    case 1 -> ClienteCadastrado.acessarCadastro();
                    case 2 -> ClienteCadastrado.processoCadastroOuLogin();
                    default -> System.exit(0);
                }
            }
            case 2 -> ClienteAnonimo.iniciarSessaoClienteAnonimo();
            default -> System.exit(0);
        }
    }

    static String setMenuLanches() {
        System.out.print("""
                \n***********
                 \uD83E\uDD16LANCHES
                ***********
                """);

        String[] lanchesMenu = Lanches.listarLanchesComoMenu();
        int opcao = setMenu(lanchesMenu);

        String linhaEscolhida = lanchesMenu[opcao - 1];
        System.out.println("Você escolheu: " + linhaEscolhida);
        return "Lanche: " + linhaEscolhida;
    }

    static String setMenuBebidas() {
        System.out.print("""
                \n***********
                 \uD83E\uDD16BEBIDAS
                ***********
                """);

        String[] bebidasMenu = Bebidas.listarBebidasComoMenu();
        int opcao = setMenu(bebidasMenu);

        String linhaEscolhida = bebidasMenu[opcao - 1];
        System.out.println("Você escolheu: " + linhaEscolhida);
        return "Bebida: " + linhaEscolhida;
    }

    static String setMenuSobremesas() {
        System.out.print("""
                \n**************
                 \uD83E\uDD16SOBREMESAS
                **************
                """);

        String[] sobremesasMenu = Sobremesas.listarSobremesasComoMenu();
        int opcao = setMenu(sobremesasMenu);

        String linhaEscolhida = sobremesasMenu[opcao - 1];
        System.out.println("Você escolheu: " + linhaEscolhida);
        return "Sobremesa: " + linhaEscolhida;
    }

    public static double calcularTotalDoPedido(String resumoCardapio) {
        double total = 0.0;

        Pattern padrao = Pattern.compile("R\\$\\s?(\\d+[.,]?\\d*)");
        Matcher matcher = padrao.matcher(resumoCardapio);

        while (matcher.find()) {
            String valorStr = matcher.group(1).replace(",", ".");
            try {
                double valor = Double.parseDouble(valorStr);
                total += valor;
            } catch (NumberFormatException _) {}
        }
        return total;
    }

    static String cardapioEscolhidoMenu(String lanche, String bebida, String sobremesa) {
        String cardapioFinal = "\nCARDÁPIO FINAL:" +
                "\n\uD83D\uDD38" + lanche +
                "\n\uD83D\uDD38" + bebida +
                "\n\uD83D\uDD38" + sobremesa;

        double total = calcularTotalDoPedido(cardapioFinal);

        DecimalFormat formatoBrasil = new DecimalFormat("R$ #,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        System.out.println("\nVALOR TOTAL: " + formatoBrasil.format(total));

        return cardapioFinal;
    }
}