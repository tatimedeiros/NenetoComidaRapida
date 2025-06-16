import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Inputs {
    static Scanner scanner = new Scanner(System.in);

    static int receberInputNum() {
        int inputNum = scanner.nextInt();
        scanner.nextLine();
        return inputNum;
    }

    static int validarInputNum(String itensMenu, int qtdItensMenu) {
        boolean isInputValido = false;
        int inputNum = 0;

        do {
            System.out.println("\n" + itensMenu);
            try {
                inputNum = receberInputNum();
                if (inputNum > 0 && inputNum <= qtdItensMenu) {
                    isInputValido = true;
                } else {
                    System.out.printf("\n***** %d não é uma opção válida! *****\n", inputNum);
                }
            } catch (InputMismatchException e) {
                System.out.println("\n⚠\uFE0F Entrada inválida!");
                System.out.println("***** Letras e símbolos não são aceitos! *****");
                scanner.nextLine();
            }
        } while (!isInputValido);
        return inputNum;
    }

    private static String formatarCpfComHifen(String cpf) {
        return cpf.replaceFirst("(\\d{9})(\\d{2})", "$1-$2");
    }

    public enum TipoEntrada {
        NOME, CPF, SENHA, PERSONALIZADO, INTEIRO, DATA
    }

    public static String validarInputStr(String mensagem, TipoEntrada tipo, String regexPersonalizado, int maxLength) {
        String inputStr = "";
        boolean isInputValido = false;

        do {
            System.out.println("\n" + mensagem);
            try {
                inputStr = scanner.nextLine().trim();

                if (inputStr.equalsIgnoreCase("s")) {
                    System.out.println("\nFinalizando...");
                    System.exit(0);
                }

                if (inputStr.length() > maxLength) {
                    System.out.printf("\n⚠️ A entrada deve ter no máximo %d caracteres.\n", maxLength);
                    continue;
                }

                switch (tipo) {
                    case NOME:
                        isInputValido = inputStr.matches("^[a-zA-ZÀ-ÿ\\s]+$");
                        if (!isInputValido) System.out.println("\n⚠️ Digite apenas letras.");
                        break;

                    case CPF:
                        isInputValido = inputStr.matches("^\\d{11}$|^\\d{9}-\\d{2}$");

                        if (!isInputValido) {
                            System.out.println("\n⚠️ CPF inválido. Use 12345678912 ou 123456789-12. Ou digite 's' para Sair");
                        } else if (inputStr.matches("^\\d{11}$")) {
                            inputStr = formatarCpfComHifen(inputStr); // novo formato
                        }
                        break;

                    case SENHA:
                        isInputValido = !inputStr.isEmpty();
                        if (!isInputValido) System.out.println("\n⚠️ A senha não pode estar vazia.");
                        break;

                    case PERSONALIZADO:
                        isInputValido = inputStr.matches(regexPersonalizado);
                        if (!isInputValido) System.out.println("\n⚠️ Entrada inválida para o padrão personalizado.");
                        break;

                    case INTEIRO:
                        isInputValido = inputStr.matches("^-?\\d+$");
                        if (!isInputValido) System.out.println("\n⚠️ Digite um número inteiro válido.");
                        break;

                    case DATA:
                        isInputValido = validarData(inputStr);
                        if (!isInputValido) System.out.println("\n⚠️ Formato de data inválido. Use dd/MM/yyyy.");
                        break;
                }

            } catch (Exception e) {
                System.out.println("\n⚠️ Erro na leitura da entrada.");
                scanner.nextLine();
            }

        } while (!isInputValido);

        return inputStr;
    }

    public static String validarInputStr(String mensagem, TipoEntrada tipo, int maxLength) {
        return validarInputStr(mensagem, tipo, "", maxLength);
    }

    protected static boolean validarData(String data) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
