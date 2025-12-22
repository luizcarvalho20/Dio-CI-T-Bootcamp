import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o número inicial: ");
        int numeroInicial = scanner.nextInt();

        if (numeroInicial == 0) {
            System.out.println("O número inicial não pode ser zero.");
            scanner.close();
            return;
        }

        while (true) {
            System.out.print("Digite um número: ");
            int numero = scanner.nextInt();

            // Ignora números menores que o número inicial
            if (numero < numeroInicial) {
                System.out.println("Número ignorado (menor que o número inicial).");
                continue;
            }

            // Verifica o resto da divisão
            if (numero % numeroInicial != 0) {
                System.out.println("Resto diferente de zero. Encerrando o programa.");
                break;
            }

            System.out.println("Número aceito (divisão exata). Continuando...");
        }

        scanner.close();
    }
}
