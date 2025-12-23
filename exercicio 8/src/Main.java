import java.util.Scanner;

abstract class Ingresso {
    private final String nomeFilme;
    private final boolean dublado;
    private final double valorBase;

    protected Ingresso(String nomeFilme, boolean dublado, double valorBase) {
        if (nomeFilme == null || nomeFilme.isBlank()) throw new IllegalArgumentException("Nome do filme invalido.");
        if (valorBase <= 0) throw new IllegalArgumentException("Valor base deve ser > 0.");
        this.nomeFilme = nomeFilme;
        this.dublado = dublado;
        this.valorBase = valorBase;
    }

    public String getNomeFilme() { return nomeFilme; }
    public boolean isDublado() { return dublado; }
    public double getValorBase() { return valorBase; }

    public abstract double getValorReal();

    public String getTipoAudio() {
        return dublado ? "Dublado" : "Legendado";
    }
}

class IngressoInteira extends Ingresso {
    public IngressoInteira(String nomeFilme, boolean dublado, double valorBase) {
        super(nomeFilme, dublado, valorBase);
    }
    @Override
    public double getValorReal() {
        return getValorBase();
    }
}

class IngressoMeia extends Ingresso {
    public IngressoMeia(String nomeFilme, boolean dublado, double valorBase) {
        super(nomeFilme, dublado, valorBase);
    }
    @Override
    public double getValorReal() {
        return getValorBase() / 2.0;
    }
}

class IngressoFamilia extends Ingresso {
    private final int pessoas;

    public IngressoFamilia(String nomeFilme, boolean dublado, double valorBase, int pessoas) {
        super(nomeFilme, dublado, valorBase);
        if (pessoas <= 0) throw new IllegalArgumentException("Quantidade de pessoas deve ser > 0.");
        this.pessoas = pessoas;
    }

    public int getPessoas() { return pessoas; }

    @Override
    public double getValorReal() {
        double total = getValorBase() * pessoas;
        if (pessoas > 3) total *= 0.95; // 5% desconto
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n=== MENU - INGRESSOS ===");
            System.out.println("1 - Ingresso Inteira");
            System.out.println("2 - Ingresso Meia");
            System.out.println("3 - Ingresso Familia");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = lerInt(sc);

            if (opcao == 0) break;

            System.out.print("Nome do filme: ");
            String filme = sc.nextLine();

            System.out.print("Audio (1-Dublado / 2-Legendado): ");
            int audio = lerInt(sc);
            boolean dublado = (audio == 1);

            System.out.print("Valor base (R$): ");
            double valor = lerDouble(sc);

            Ingresso ingresso;

            try {
                if (opcao == 1) {
                    ingresso = new IngressoInteira(filme, dublado, valor);
                } else if (opcao == 2) {
                    ingresso = new IngressoMeia(filme, dublado, valor);
                } else if (opcao == 3) {
                    System.out.print("Quantidade de pessoas: ");
                    int pessoas = lerInt(sc);
                    ingresso = new IngressoFamilia(filme, dublado, valor, pessoas);
                } else {
                    System.out.println("Opcao invalida.");
                    continue;
                }

                System.out.println("\n--- RESUMO ---");
                System.out.println("Filme: " + ingresso.getNomeFilme());
                System.out.println("Audio: " + ingresso.getTipoAudio());
                System.out.printf("Valor real: R$ %.2f%n", ingresso.getValorReal());

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
