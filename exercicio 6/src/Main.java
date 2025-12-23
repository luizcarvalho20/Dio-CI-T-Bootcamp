import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Carro carro = new Carro();

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao(sc);

            switch (opcao) {
                case 1 -> carro.ligar();
                case 2 -> carro.desligar();
                case 3 -> carro.acelerar();
                case 4 -> carro.diminuirVelocidade();
                case 5 -> carro.virarEsquerda();
                case 6 -> carro.virarDireita();
                case 7 -> carro.exibirStatus();
                case 8 -> {
                    System.out.println("1 - Subir marcha");
                    System.out.println("2 - Reduzir marcha");
                    System.out.print("Escolha: ");
                    int escolha = lerOpcao(sc);
                    if (escolha == 1) carro.subirMarcha();
                    else if (escolha == 2) carro.reduzirMarcha();
                    else System.out.println("Opcao invalida.");
                }
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU - CONTROLE DO CARRO ===");
        System.out.println("1 - Ligar carro");
        System.out.println("2 - Desligar carro");
        System.out.println("3 - Acelerar (+1 km/h)");
        System.out.println("4 - Diminuir velocidade (-1 km/h)");
        System.out.println("5 - Virar para esquerda");
        System.out.println("6 - Virar para direita");
        System.out.println("7 - Verificar status (ligado, marcha, velocidade)");
        System.out.println("8 - Trocar marcha");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static int lerOpcao(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Entrada invalida. Digite novamente: ");
        }
        int opcao = sc.nextInt();
        sc.nextLine();
        return opcao;
    }

    // ===================== CLASSE CARRO =====================
    static class Carro {
        private boolean ligado;
        private int marcha;     // 0 a 6
        private int velocidade; // 0 a 120

        public Carro() {
            this.ligado = false;
            this.marcha = 0;
            this.velocidade = 0;
        }

        public void ligar() {
            if (ligado) {
                System.out.println("O carro ja esta ligado.");
                return;
            }
            ligado = true;
            System.out.println("Carro ligado.");
        }

        public void desligar() {
            if (!ligado) {
                System.out.println("O carro ja esta desligado.");
                return;
            }

            // Regra: só pode desligar em ponto morto e 0 km/h
            if (marcha == 0 && velocidade == 0) {
                ligado = false;
                System.out.println("Carro desligado.");
            } else {
                System.out.println("Nao e possivel desligar: o carro deve estar em marcha 0 e velocidade 0 km/h.");
            }
        }

        public void acelerar() {
            if (!validarCarroLigado()) return;

            // Regra: marcha 0 não acelera
            if (marcha == 0) {
                System.out.println("Nao e possivel acelerar em ponto morto (marcha 0).");
                return;
            }

            if (velocidade == 120) {
                System.out.println("Velocidade maxima atingida (120 km/h).");
                return;
            }

            int novaVelocidade = velocidade + 1;

            // Regra: velocidade precisa estar dentro do intervalo da marcha
            if (!velocidadeValidaParaMarcha(marcha, novaVelocidade)) {
                System.out.println("A velocidade nao e permitida para a marcha atual. Troque a marcha antes de acelerar.");
                return;
            }

            velocidade = novaVelocidade;
            System.out.println("Acelerou. Velocidade atual: " + velocidade + " km/h");
        }

        public void diminuirVelocidade() {
            if (!validarCarroLigado()) return;

            if (velocidade == 0) {
                System.out.println("O carro ja esta parado (0 km/h).");
                return;
            }

            int novaVelocidade = velocidade - 1;

            // Diminuir pode acontecer sempre (até 0), mas a marcha deve respeitar a velocidade
            velocidade = novaVelocidade;

            // Se a velocidade ficar incompatível com a marcha atual, não força troca automática:
            // apenas alerta (padrão didático CI&T/DIO).
            if (marcha != 0 && !velocidadeValidaParaMarcha(marcha, velocidade)) {
                System.out.println("Velocidade atual ficou fora do limite da marcha. Reduza a marcha.");
            }

            System.out.println("Diminuiu. Velocidade atual: " + velocidade + " km/h");
        }

        public void virarEsquerda() {
            if (!validarCarroLigado()) return;

            if (velocidade >= 1 && velocidade <= 40) {
                System.out.println("Virando para a esquerda...");
            } else {
                System.out.println("Nao e possivel virar: velocidade deve estar entre 1 e 40 km/h.");
            }
        }

        public void virarDireita() {
            if (!validarCarroLigado()) return;

            if (velocidade >= 1 && velocidade <= 40) {
                System.out.println("Virando para a direita...");
            } else {
                System.out.println("Nao e possivel virar: velocidade deve estar entre 1 e 40 km/h.");
            }
        }

        public void exibirStatus() {
            System.out.println("\n=== STATUS DO CARRO ===");
            System.out.println("Ligado: " + (ligado ? "Sim" : "Nao"));
            System.out.println("Marcha: " + marcha);
            System.out.println("Velocidade: " + velocidade + " km/h");
        }

        public void subirMarcha() {
            if (!validarCarroLigado()) return;

            if (marcha == 6) {
                System.out.println("O carro ja esta na 6ª marcha.");
                return;
            }

            int novaMarcha = marcha + 1;

            // Regra: não pode pular marcha (aqui sempre +1)
            // Regra: velocidade deve ser compatível com a nova marcha
            if (novaMarcha != 0 && !velocidadeValidaParaMarcha(novaMarcha, velocidade)) {
                System.out.println("Nao e possivel subir marcha: a velocidade atual nao e compativel com a nova marcha.");
                return;
            }

            marcha = novaMarcha;
            System.out.println("Subiu para a marcha " + marcha + ".");
        }

        public void reduzirMarcha() {
            if (!validarCarroLigado()) return;

            if (marcha == 0) {
                System.out.println("O carro ja esta em ponto morto (marcha 0).");
                return;
            }

            int novaMarcha = marcha - 1;

            // Regra: não pode pular marcha (aqui sempre -1)
            // Se for para marcha 0, só faz sentido se estiver parado (didático e seguro)
            if (novaMarcha == 0 && velocidade != 0) {
                System.out.println("Nao e possivel ir para ponto morto com o carro em movimento. Reduza a velocidade ate 0.");
                return;
            }

            // Se novaMarcha > 0, precisa ser compatível com velocidade atual
            if (novaMarcha > 0 && !velocidadeValidaParaMarcha(novaMarcha, velocidade)) {
                System.out.println("Nao e possivel reduzir marcha: a velocidade atual nao e compativel com a nova marcha.");
                return;
            }

            marcha = novaMarcha;
            System.out.println("Reduziu para a marcha " + marcha + ".");
        }

        private boolean validarCarroLigado() {
            if (!ligado) {
                System.out.println("O carro esta desligado. Ligue o carro para executar essa operacao.");
                return false;
            }
            return true;
        }

        private boolean velocidadeValidaParaMarcha(int marcha, int velocidade) {
            // Marcha 0: não acelera (mas pode estar em 0 km/h)
            if (marcha == 0) return velocidade == 0;

            return switch (marcha) {
                case 1 -> velocidade >= 0 && velocidade <= 20;
                case 2 -> velocidade >= 21 && velocidade <= 40;
                case 3 -> velocidade >= 41 && velocidade <= 60;
                case 4 -> velocidade >= 61 && velocidade <= 80;
                case 5 -> velocidade >= 81 && velocidade <= 100;
                case 6 -> velocidade >= 101 && velocidade <= 120;
                default -> false;
            };
        }
    }
}
