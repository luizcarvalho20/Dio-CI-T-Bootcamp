import java.util.Scanner;

interface FormaGeometrica {
    double calcularArea();
    String getNome();
}

class Quadrado implements FormaGeometrica {
    private final double lado;

    public Quadrado(double lado) {
        if (lado <= 0) throw new IllegalArgumentException("Lado deve ser > 0.");
        this.lado = lado;
    }

    @Override public double calcularArea() {
        return lado * lado;
    }

    @Override public String getNome() { return "Quadrado"; }
}

class Retangulo implements FormaGeometrica {
    private final double base;
    private final double altura;

    public Retangulo(double base, double altura) {
        if (base <= 0 || altura <= 0) throw new IllegalArgumentException("Base e altura devem ser > 0.");
        this.base = base;
        this.altura = altura;
    }

    @Override public double calcularArea() {
        return base * altura;
    }

    @Override public String getNome() { return "Retangulo"; }
}

class Circulo implements FormaGeometrica {
    private final double raio;

    public Circulo(double raio) {
        if (raio <= 0) throw new IllegalArgumentException("Raio deve ser > 0.");
        this.raio = raio;
    }

    @Override public double calcularArea() {
        return Math.PI * raio * raio;
    }

    @Override public String getNome() { return "Circulo"; }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n=== MENU - AREA ===");
            System.out.println("1 - Quadrado");
            System.out.println("2 - Retangulo");
            System.out.println("3 - Circulo");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInt(sc);

            if (opcao == 0) break;

            try {
                FormaGeometrica forma = switch (opcao) {
                    case 1 -> {
                        System.out.print("Lado: ");
                        double lado = lerDouble(sc);
                        yield new Quadrado(lado);
                    }
                    case 2 -> {
                        System.out.print("Base: ");
                        double base = lerDouble(sc);
                        System.out.print("Altura: ");
                        double altura = lerDouble(sc);
                        yield new Retangulo(base, altura);
                    }
                    case 3 -> {
                        System.out.print("Raio: ");
                        double raio = lerDouble(sc);
                        yield new Circulo(raio);
                    }
                    default -> null;
                };

                if (forma == null) {
                    System.out.println("Opcao invalida.");
                    continue;
                }

                System.out.println("\n--- RESULTADO ---");
                System.out.println("Forma: " + forma.getNome());
                System.out.printf("Area: %.4f%n", forma.calcularArea());

            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (true);

        System.out.println("Encerrando...");
        sc.close();
    }

    private static int lerInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Entrada invalida. Digite novamente: ");
        }
        int v = sc.nextInt();
        sc.nextLine();
        return v;
    }

    private static double lerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            sc.nextLine();
            System.out.print("Valor invalido. Digite novamente: ");
        }
        double v = sc.nextDouble();
        sc.nextLine();
        return v;
    }
}
