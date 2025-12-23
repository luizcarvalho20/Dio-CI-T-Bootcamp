import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MaquinaBanho maquina = new MaquinaBanho();

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao(sc);

            switch (opcao) {
                case 1 -> maquina.colocarPet();
                case 2 -> maquina.retirarPet();
                case 3 -> maquina.darBanho();
                case 4 -> maquina.abastecerAgua();
                case 5 -> maquina.abastecerShampoo();
                case 6 -> maquina.verificarNivelAgua();
                case 7 -> maquina.verificarNivelShampoo();
                case 8 -> maquina.verificarPetNaMaquina();
                case 9 -> maquina.limparMaquina();
                case 0 -> System.out.println("Encerrando sistema...");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU - MAQUINA DE BANHO PETSHOP ===");
        System.out.println("1 - Colocar pet na maquina");
        System.out.println("2 - Retirar pet da maquina");
        System.out.println("3 - Dar banho no pet");
        System.out.println("4 - Abastecer agua (+2 litros)");
        System.out.println("5 - Abastecer shampoo (+2 litros)");
        System.out.println("6 - Verificar nivel de agua");
        System.out.println("7 - Verificar nivel de shampoo");
        System.out.println("8 - Verificar se ha pet na maquina");
        System.out.println("9 - Limpar maquina");
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

    // ===================== CLASSE MAQUINA =====================
    static class MaquinaBanho {

        private int agua;     // litros
        private int shampoo;  // litros
        private boolean petNaMaquina;
        private boolean petLimpo;
        private boolean maquinaSuja;

        private static final int MAX_AGUA = 30;
        private static final int MAX_SHAMPOO = 10;

        public MaquinaBanho() {
            this.agua = 0;
            this.shampoo = 0;
            this.petNaMaquina = false;
            this.petLimpo = false;
            this.maquinaSuja = false;
        }

        public void colocarPet() {
            if (petNaMaquina) {
                System.out.println("Ja existe um pet na maquina.");
                return;
            }

            if (maquinaSuja) {
                System.out.println("A maquina esta suja. E necessario limpar antes de colocar outro pet.");
                return;
            }

            petNaMaquina = true;
            petLimpo = false;
            System.out.println("Pet colocado na maquina.");
        }

        public void retirarPet() {
            if (!petNaMaquina) {
                System.out.println("Nao ha pet na maquina.");
                return;
            }

            petNaMaquina = false;

            if (!petLimpo) {
                maquinaSuja = true;
                System.out.println("Pet retirado sem banho. A maquina ficou suja.");
            } else {
                System.out.println("Pet retirado limpo. Maquina liberada.");
            }
        }

        public void darBanho() {
            if (!petNaMaquina) {
                System.out.println("Nao ha pet na maquina para dar banho.");
                return;
            }

            if (agua < 10 || shampoo < 2) {
                System.out.println("Recursos insuficientes para o banho.");
                System.out.println("Necessario: 10L de agua e 2L de shampoo.");
                return;
            }

            agua -= 10;
            shampoo -= 2;
            petLimpo = true;

            System.out.println("Banho realizado com sucesso!");
        }

        public void abastecerAgua() {
            if (agua + 2 > MAX_AGUA) {
                System.out.println("Capacidade maxima de agua atingida.");
                return;
            }

            agua += 2;
            System.out.println("Agua abastecida. Nivel atual: " + agua + " litros.");
        }

        public void abastecerShampoo() {
            if (shampoo + 2 > MAX_SHAMPOO) {
                System.out.println("Capacidade maxima de shampoo atingida.");
                return;
            }

            shampoo += 2;
            System.out.println("Shampoo abastecido. Nivel atual: " + shampoo + " litros.");
        }

        public void verificarNivelAgua() {
            System.out.println("Nivel de agua: " + agua + " litros.");
        }

        public void verificarNivelShampoo() {
            System.out.println("Nivel de shampoo: " + shampoo + " litros.");
        }

        public void verificarPetNaMaquina() {
            if (petNaMaquina) {
                System.out.println("Ha um pet na maquina.");
            } else {
                System.out.println("Nao ha pet na maquina.");
            }
        }

        public void limparMaquina() {
            if (!maquinaSuja) {
                System.out.println("A maquina ja esta limpa.");
                return;
            }

            if (agua < 3 || shampoo < 1) {
                System.out.println("Recursos insuficientes para limpar a maquina.");
                System.out.println("Necessario: 3L de agua e 1L de shampoo.");
                return;
            }

            agua -= 3;
            shampoo -= 1;
            maquinaSuja = false;

            System.out.println("Maquina limpa com sucesso.");
        }
    }
}
