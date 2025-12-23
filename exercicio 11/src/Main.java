import java.util.Scanner;

interface CanalMensagem {
    void enviarMensagem(String mensagem);
    String getNome();
}

class SmsService implements CanalMensagem {
    @Override public void enviarMensagem(String mensagem) {
        System.out.println("[SMS] Enviando: " + mensagem);
    }
    @Override public String getNome() { return "SMS"; }
}

class EmailService implements CanalMensagem {
    @Override public void enviarMensagem(String mensagem) {
        System.out.println("[E-mail] Enviando: " + mensagem);
    }
    @Override public String getNome() { return "E-mail"; }
}

class RedesSociaisService implements CanalMensagem {
    @Override public void enviarMensagem(String mensagem) {
        System.out.println("[Redes Sociais] Publicando: " + mensagem);
    }
    @Override public String getNome() { return "Redes Sociais"; }
}

class WhatsAppService implements CanalMensagem {
    @Override public void enviarMensagem(String mensagem) {
        System.out.println("[WhatsApp] Enviando: " + mensagem);
    }
    @Override public String getNome() { return "WhatsApp"; }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao;
        do {
            System.out.println("\n=== MENU - MARKETING ===");
            System.out.println("1 - Enviar por SMS");
            System.out.println("2 - Enviar por E-mail");
            System.out.println("3 - Enviar por Redes Sociais");
            System.out.println("4 - Enviar por WhatsApp");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInt(sc);

            if (opcao == 0) break;

            System.out.print("Digite a mensagem: ");
            String mensagem = sc.nextLine();

            if (mensagem.isBlank()) {
                System.out.println("Mensagem invalida.");
                continue;
            }

            CanalMensagem canal = switch (opcao) {
                case 1 -> new SmsService();
                case 2 -> new EmailService();
                case 3 -> new RedesSociaisService();
                case 4 -> new WhatsAppService();
                default -> null;
            };

            if (canal == null) {
                System.out.println("Opcao invalida.");
            } else {
                System.out.println("Canal escolhido: " + canal.getNome());
                canal.enviarMensagem(mensagem);
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
}
