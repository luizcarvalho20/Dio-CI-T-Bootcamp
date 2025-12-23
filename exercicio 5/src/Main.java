import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    // Constantes
    private static final BigDecimal LIMITE_FIXO_CHEQUE_ESPECIAL = new BigDecimal("50.00");
    private static final BigDecimal LIMITE_PERCENTUAL_CHEQUE_ESPECIAL = new BigDecimal("0.50");
    private static final BigDecimal TAXA_CHEQUE_ESPECIAL = new BigDecimal("0.20");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CRIACAO DA CONTA ===");
        BigDecimal depositoInicial = lerValorMonetario(sc, "Informe o deposito inicial (R$): ");

        ContaBancaria conta = new ContaBancaria(depositoInicial);

        System.out.println("\nConta criada com sucesso!");
        System.out.println("Saldo inicial: R$ " + conta.getSaldo());
        System.out.println("Limite cheque especial: R$ " + conta.getLimiteChequeEspecial());

        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao(sc);

            switch (opcao) {
                case 1 -> System.out.println("Saldo atual: R$ " + conta.getSaldo());
                case 2 -> conta.exibirStatusChequeEspecial();
                case 3 -> {
                    BigDecimal valor = lerValorMonetario(sc, "Valor do deposito (R$): ");
                    conta.depositar(valor);
                }
                case 4 -> {
                    BigDecimal valor = lerValorMonetario(sc, "Valor do saque (R$): ");
                    conta.sacar(valor);
                }
                case 5 -> {
                    BigDecimal valor = lerValorMonetario(sc, "Valor do boleto (R$): ");
                    conta.pagarBoleto(valor);
                }
                case 6 -> {
                    if (conta.estaUsandoChequeEspecial()) {
                        System.out.println("A conta ESTA usando cheque especial.");
                        System.out.println("Valor usado: R$ " + conta.getValorUsadoChequeEspecialAtual());
                    } else {
                        System.out.println("A conta NAO esta usando cheque especial.");
                    }
                }
                case 0 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1 - Consultar saldo");
        System.out.println("2 - Consultar cheque especial");
        System.out.println("3 - Depositar dinheiro");
        System.out.println("4 - Sacar dinheiro");
        System.out.println("5 - Pagar boleto");
        System.out.println("6 - Verificar uso do cheque especial");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private static int lerOpcao(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.nextLine();
            System.out.print("Opcao invalida. Digite novamente: ");
        }
        int opcao = sc.nextInt();
        sc.nextLine();
        return opcao;
    }

    private static BigDecimal lerValorMonetario(Scanner sc, String mensagem) {
        System.out.print(mensagem);
        while (!sc.hasNextBigDecimal()) {
            sc.nextLine();
            System.out.print("Valor invalido. Digite novamente: ");
        }
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine(); 
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    // ===================== CLASSE CONTA =====================
    static class ContaBancaria {
        private BigDecimal saldo;
        private final BigDecimal limiteChequeEspecial;

        // Taxa pendente de 20% do valor usado quando entrou no cheque especial
        private boolean taxaPendente;
        private BigDecimal valorTaxaPendente;

        public ContaBancaria(BigDecimal depositoInicial) {
            validarValorPositivo(depositoInicial, "Deposito inicial");

            this.saldo = depositoInicial;
            this.limiteChequeEspecial = calcularLimiteChequeEspecial(depositoInicial);

            this.taxaPendente = false;
            this.valorTaxaPendente = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public BigDecimal getLimiteChequeEspecial() {
            return limiteChequeEspecial;
        }

        public boolean estaUsandoChequeEspecial() {
            return saldo.compareTo(BigDecimal.ZERO) < 0;
        }

        public BigDecimal getValorUsadoChequeEspecialAtual() {
            if (!estaUsandoChequeEspecial()) return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            return saldo.abs().setScale(2, RoundingMode.HALF_UP);
        }

        public BigDecimal getLimiteTotalDisponivel() {
            return saldo.add(limiteChequeEspecial).setScale(2, RoundingMode.HALF_UP);
        }

        public void exibirStatusChequeEspecial() {
            System.out.println("Limite cheque especial: R$ " + limiteChequeEspecial);
            System.out.println("Usado (atual): R$ " + getValorUsadoChequeEspecialAtual());
            System.out.println("Total disponivel (saldo + limite): R$ " + getLimiteTotalDisponivel());
            System.out.println("Taxa pendente: R$ " + (taxaPendente ? valorTaxaPendente : "0.00"));
        }

        public void depositar(BigDecimal valor) {
            validarValorPositivo(valor, "Deposito");

            saldo = saldo.add(valor);
            System.out.println("Deposito realizado: R$ " + valor);

            cobrarTaxaSePossivel();
        }

        public void sacar(BigDecimal valor) {
            validarValorPositivo(valor, "Saque");

            if (valor.compareTo(getLimiteTotalDisponivel()) > 0) {
                System.out.println("Saldo insuficiente (considerando cheque especial).");
                return;
            }

            saldo = saldo.subtract(valor);
            System.out.println("Saque realizado: R$ " + valor);

            registrarUsoChequeEspecialSeAplicavel();
            cobrarTaxaSePossivel();
        }

        public void pagarBoleto(BigDecimal valor) {
            validarValorPositivo(valor, "Boleto");

            if (valor.compareTo(getLimiteTotalDisponivel()) > 0) {
                System.out.println("Saldo insuficiente (considerando cheque especial) para pagar o boleto.");
                return;
            }

            saldo = saldo.subtract(valor);
            System.out.println("Boleto pago: R$ " + valor);

            registrarUsoChequeEspecialSeAplicavel();
            cobrarTaxaSePossivel();
        }

        private static BigDecimal calcularLimiteChequeEspecial(BigDecimal depositoInicial) {
            if (depositoInicial.compareTo(new BigDecimal("500.00")) <= 0) {
                return LIMITE_FIXO_CHEQUE_ESPECIAL.setScale(2, RoundingMode.HALF_UP);
            }
            return depositoInicial.multiply(LIMITE_PERCENTUAL_CHEQUE_ESPECIAL)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        private void registrarUsoChequeEspecialSeAplicavel() {
            // Se entrou no cheque especial, gera taxa pendente 1 vez (20% do valor usado naquele momento)
            if (estaUsandoChequeEspecial() && !taxaPendente) {
                BigDecimal usado = getValorUsadoChequeEspecialAtual();
                valorTaxaPendente = usado.multiply(TAXA_CHEQUE_ESPECIAL).setScale(2, RoundingMode.HALF_UP);
                taxaPendente = true;

                System.out.println("ATENCAO: Cheque especial em uso.");
                System.out.println("Taxa de 20% gerada e ficara pendente: R$ " + valorTaxaPendente);
            }
        }

        private void cobrarTaxaSePossivel() {
            // "Assim que possível": quando o saldo for suficiente para pagar a taxa pendente
            if (taxaPendente && saldo.compareTo(valorTaxaPendente) >= 0) {
                saldo = saldo.subtract(valorTaxaPendente);
                System.out.println("Taxa do cheque especial cobrada: R$ " + valorTaxaPendente);

                taxaPendente = false;
                valorTaxaPendente = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
            }
        }

        private static void validarValorPositivo(BigDecimal valor, String campo) {
            if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(campo + " deve ser maior que zero.");
            }
        }
    }
}
