import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o primeiro número: ");
        int inicio = scanner.nextInt();

        System.out.print("Digite o segundo número (maior que o primeiro): ");
        int fim = scanner.nextInt();

        if (fim <= inicio) {
            System.out.println("Erro: o segundo número deve ser maior que o primeiro.");
            scanner.close();
            return;
        }

        System.out.print("Digite 1 para PAR ou 2 para ÍMPAR: ");
        int opcao = scanner.nextInt();

        System.out.println("\nResultado:");

        for (int i = fim; i >= inicio; i--) {

            if (opcao == 1 && i % 2 == 0) {
                System.out.println(i);
            }
            else if (opcao == 2 && i % 2 != 0) {
                System.out.println(i);
            }
        }

        scanner.close();
    }
}
