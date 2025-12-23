import java.util.Scanner;

abstract class Relogio {
    private int hora;
    private int minuto;
    private int segundo;

    public int getHora() { return hora; }
    public void setHora(int hora) {
        validarHora(hora);
        this.hora = hora;
    }

    public int getMinuto() { return minuto; }
    public void setMinuto(int minuto) {
        if (minuto < 0 || minuto > 59) throw new IllegalArgumentException("Minuto invalido.");
        this.minuto = minuto;
    }

    public int getSegundo() { return segundo; }
    public void setSegundo(int segundo) {
        if (segundo < 0 || segundo > 59) throw new IllegalArgumentException("Segundo invalido.");
        this.segundo = segundo;
    }

    public String formatar() {
        return String.format("%02d:%02d:%02d", hora, minuto, segundo);
    }

    // Método que cada implementação define sua regra de hora válida
    protected abstract void validarHora(int hora);

    // Método que recebe qualquer relógio e ajusta o relógio atual usando os dados do outro
    public void ajustar(Relogio outro) {
        if (outro == null) throw new IllegalArgumentException("Relogio de origem nao pode ser nulo.");

        // extrai
        int h = outro.getHora();
        int m = outro.getMinuto();
        int s = outro.getSegundo();

        // aplica no relógio atual (validações da implementação atual)
        setHora(h);
        setMinuto(m);
        setSegundo(s);
    }
}

class RelogioBrasileiro extends Relogio {
    @Override
    protected void validarHora(int hora) {
        if (hora < 0 || hora > 23) throw new IllegalArgumentException("Hora invalida para Relogio Brasileiro (0-23).");
    }
}

class RelogioAmericano extends Relogio {
    @Override
    protected void validarHora(int hora) {
        // Enunciado: não existem horas de 13 até 24 (então 0-12)
        if (hora < 0 || hora > 12) throw new IllegalArgumentException("Hora invalida para Relogio Americano (0-12).");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Relogio br = new RelogioBrasileiro();
        Relogio us = new RelogioAmericano();

        // Inicializa com 0
        br.setHora(0); br.setMinuto(0); br.setSegundo(0);
        us.setHora(0); us.setMinuto(0); us.setSegundo(0);

        int opcao;
        do {
            System.out.println("\n=== MENU - RELOGIOS ===");
            System.out.println("1 - Setar horario no Relogio Brasileiro");
            System.out.println("2 - Setar horario no Relogio Americano");
            System.out.println("3 - Mostrar horarios");
            System.out.println("4 - Ajustar Brasileiro a partir do Americano");
            System.out.println("5 - Ajustar Americano a partir do Brasileiro");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInt(sc);

            try {
                switch (opcao) {
                    case 1 -> setarHorario(sc, br, "Brasileiro");
                    case 2 -> setarHorario(sc, us, "Americano");
                    case 3 -> {
                        System.out.println("BR: " + br.formatar());
                        System.out.println("US: " + us.formatar());
                    }
                    case 4 -> {
                        br.ajustar(us);
                        System.out.println("Relogio Brasileiro ajustado com base no Americano.");
                    }
                    case 5 -> {
                        us.ajustar(br);
                        System.out.println("Relogio Americano ajustado com base no Brasileiro.");
                    }
                    case 0 -> System.out.println("Encerrando...");
                    default -> System.out.println("Opcao invalida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void setarHorario(Scanner sc, Relogio r, String nome) {
        System.out.println("\n--- Setar horario (" + nome + ") ---");
        System.out.print("Hora: ");
        int h = lerInt(sc);
        System.out.print("Minuto: ");
        int m = lerInt(sc);
        System.out.print("Segundo: ");
        int s = lerInt(sc);

        r.setHora(h);
        r.setMinuto(m);
        r.setSegundo(s);

        System.out.println("Horario definido: " + r.formatar());
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
}
