import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite seu peso (kg): ");
        double peso = scanner.nextDouble();

        System.out.print("Digite sua altura (em metros): ");
        double altura = scanner.nextDouble();

        double imc = peso / (altura * altura);

        System.out.printf("\nSeu IMC é: %.2f\n", imc);

        if (imc <= 18.5) {
            System.out.println("Classificação: Abaixo do peso");
        } else if (imc >= 18.6 && imc <= 24.9) {
            System.out.println("Classificação: Peso ideal");
        } else if (imc >= 25 && imc <= 29.9) {
            System.out.println("Classificação: Levemente acima do peso");
        } else if (imc >= 30 && imc <= 34.9) {
            System.out.println("Classificação: Obesidade grau 1");
        } else if (imc >= 35 && imc <= 49.9) {
            System.out.println("Classificação: Obesidade grau 2");
        } else {
            System.out.println("Classificação: Obesidade grau 3");
        }

        scanner.close();
    }
}
