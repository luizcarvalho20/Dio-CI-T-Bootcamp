import java.util.Scanner;

abstract class Produto {
    private final String nome;
    private final double preco;

    protected Produto(String nome, double preco) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome invalido.");
        if (preco <= 0) throw new IllegalArgumentException("Preco deve ser maior que zero.");
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public double getPreco() { return preco; }

    public abstract double calcularImposto();
    public abstract String getTipo();
}

class Alimentacao extends Produto {
    public Alimentacao(String nome, double preco) { super(nome, preco); }
    @Override public double calcularImposto() { return getPreco() * 0.01; }
    @Override public String getTipo() { return "Alimentacao"; }
}

class SaudeBemEstar extends Produto {
    public SaudeBemEstar(String nome, double preco) { super(nome, preco); }
    @Override public double calcularImposto() { return getPreco() * 0.015; }
    @Override public String getTipo() { return "Saude e bem-estar"; }
}

class Vestuario extends Produto {
    public Vestuario(String nome, double preco) { super(nome, preco); }
    @Override public double calcularImposto() { return getPreco() * 0.025; }
    @Override public String getTipo() { return "Vestuario"; }
}

class Cultura extends Produto {
    public Cultura(String nome, double preco) { super(nome, preco); }
    @Override public double calcularImposto() { return getPreco() * 0.04; }
    @Override public String getTipo() { return "Cultura"; }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n=== MENU - TRIBUTOS ===");
            System.out.println("1 - Alimentacao (1%)");
            System.out.println("2 - Saude e bem-estar (1.5%)");
            System.out.println("3 - Vestuario (2.5%)");
            System.out.println("4 - Cultura (4%)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInt(sc);

            if (opcao == 0) break;

            System.out.print("Nome do produto: ");
            String nome = sc.nextLine();

            System.out.print("Preco do produto (R$): ");
            double preco = lerDouble(sc);

            try {
                Produto produto = switch (opcao) {
                    case 1 -> new Alimentacao(nome, preco);
                    case 2 -> new SaudeBemEstar(nome, preco);
                    case 3 -> new Vestuario(nome, preco);
                    case 4 -> new Cultura(nome, preco);
                    default -> null;
                };

                if (produto == null) {
                    System.out.println("Opcao invalida.");
                    continue;
                }

                double imposto = produto.calcularImposto();
                double total = produto.getPreco() + imposto;

                System.out.println("\n--- RESUMO ---");
                System.out.println("Tipo: " + produto.getTipo());
                System.out.println("Produto: " + produto.getNome());
                System.out.printf("Preco: R$ %.2f%n", produto.getPreco());
                System.out.printf("Imposto: R$ %.2f%n", imposto);
                System.out.printf("Total: R$ %.2f%n", total);

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
