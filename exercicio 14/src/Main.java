import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Informe a operação desejada (+ para soma ou - para subtração):");
        String operacao = sc.nextLine().trim();

        System.out.println("Informe os números separados por vírgula (ex: 10,2,3):");
        String[] valores = sc.nextLine().split(",");

        double resultado;
        try {
            resultado = Double.parseDouble(valores[0].trim());

            for (int i = 1; i < valores.length; i++) {
                double v = Double.parseDouble(valores[i].trim());
                if (operacao.equals("+")) resultado += v;
                else if (operacao.equals("-")) resultado -= v;
                else {
                    System.out.println("Operação inválida.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
            return;
        }

        System.out.println("Resultado da operação:");
        System.out.println(resultado == (long) resultado ? (long) resultado : resultado);
        sc.close();
    }
}
