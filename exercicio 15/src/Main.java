import java.util.*;

public class Main {

    static String apenasNumeros(String s) {
        return s.replaceAll("\\D", "");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Informe o número de telefone (com ou sem formatação):");
        String entrada = sc.nextLine();

        String n = apenasNumeros(entrada);
        String tipo, saida;

        switch (n.length()) {
            case 8:
                tipo = "Telefone fixo sem DDD";
                saida = n.substring(0,4) + "-" + n.substring(4);
                break;
            case 10:
                tipo = "Telefone fixo com DDD";
                saida = "(" + n.substring(0,2) + ")" + n.substring(2,6) + "-" + n.substring(6);
                break;
            case 9:
                tipo = "Celular sem DDD";
                saida = n.substring(0,5) + "-" + n.substring(5);
                break;
            case 11:
                tipo = "Celular com DDD";
                saida = "(" + n.substring(0,2) + ")" + n.substring(2,7) + "-" + n.substring(7);
                break;
            default:
                System.out.println("Número inválido.");
                return;
        }

        System.out.println("Tipo identificado:");
        System.out.println(tipo);
        System.out.println("Número formatado:");
        System.out.println(saida);

        sc.close();
    }
}
