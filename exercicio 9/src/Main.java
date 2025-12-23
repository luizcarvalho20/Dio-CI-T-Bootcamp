import java.util.Scanner;

abstract class Usuario {
    private String nome;
    private String email;
    private String senha;
    private boolean logado;

    protected Usuario(String nome, String email, String senha) {
        setNome(nome);
        setEmail(email);
        setSenha(senha);
        this.logado = false;
    }

    public abstract boolean isAdministrador();

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome invalido.");
        this.nome = nome;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email invalido.");
        this.email = email;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.isBlank()) throw new IllegalArgumentException("Senha invalida.");
        this.senha = senha;
    }

    protected boolean validarSenha(String senhaDigitada) {
        return this.senha.equals(senhaDigitada);
    }

    public boolean isLogado() { return logado; }

    public void realizarLogin(String senhaDigitada) {
        if (logado) {
            System.out.println("Usuario ja esta logado.");
            return;
        }
        if (validarSenha(senhaDigitada)) {
            logado = true;
            System.out.println("Login realizado com sucesso.");
        } else {
            System.out.println("Senha incorreta.");
        }
    }

    public void realizarLogoff() {
        if (!logado) {
            System.out.println("Usuario ja esta deslogado.");
            return;
        }
        logado = false;
        System.out.println("Logoff realizado com sucesso.");
    }

    public void alterarDados(String novoNome, String novoEmail) {
        if (!logado) {
            System.out.println("Necessario estar logado para alterar dados.");
            return;
        }
        setNome(novoNome);
        setEmail(novoEmail);
        System.out.println("Dados atualizados.");
    }

    public void alterarSenha(String senhaAtual, String novaSenha) {
        if (!logado) {
            System.out.println("Necessario estar logado para alterar senha.");
            return;
        }
        if (!validarSenha(senhaAtual)) {
            System.out.println("Senha atual incorreta.");
            return;
        }
        setSenha(novaSenha);
        System.out.println("Senha alterada com sucesso.");
    }
}

class Gerente extends Usuario {
    public Gerente(String nome, String email, String senha) {
        super(nome, email, senha);
    }
    @Override public boolean isAdministrador() { return true; }

    public void gerarRelatorioFinanceiro() {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        System.out.println("Relatorio financeiro gerado.");
    }

    public void consultarVendas() {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        System.out.println("Consultando vendas (visao gerente).");
    }
}

class Vendedor extends Usuario {
    private int quantidadeVendas;

    public Vendedor(String nome, String email, String senha) {
        super(nome, email, senha);
        this.quantidadeVendas = 0;
    }
    @Override public boolean isAdministrador() { return false; }

    public int getQuantidadeVendas() { return quantidadeVendas; }

    public void realizarVenda() {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        quantidadeVendas++;
        System.out.println("Venda realizada! Total de vendas: " + quantidadeVendas);
    }

    public void consultarVendas() {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        System.out.println("Total de vendas do vendedor: " + quantidadeVendas);
    }
}

class Atendente extends Usuario {
    private double valorEmCaixa;

    public Atendente(String nome, String email, String senha) {
        super(nome, email, senha);
        this.valorEmCaixa = 0.0;
    }
    @Override public boolean isAdministrador() { return false; }

    public double getValorEmCaixa() { return valorEmCaixa; }

    public void receberPagamentos(double valor) {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        if (valor <= 0) { System.out.println("Valor invalido."); return; }
        valorEmCaixa += valor;
        System.out.printf("Pagamento recebido. Caixa: R$ %.2f%n", valorEmCaixa);
    }

    public void fecharCaixa() {
        if (!isLogado()) { System.out.println("Necessario login."); return; }
        System.out.printf("Caixa fechado. Total: R$ %.2f%n", valorEmCaixa);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Objetos exemplo (padrão exercício)
        Gerente gerente = new Gerente("Gerente", "gerente@email.com", "123");
        Vendedor vendedor = new Vendedor("Vendedor", "vendedor@email.com", "123");
        Atendente atendente = new Atendente("Atendente", "atendente@email.com", "123");

        int opcao;
        do {
            System.out.println("\n=== MENU - USUARIOS ===");
            System.out.println("1 - Acessar Gerente");
            System.out.println("2 - Acessar Vendedor");
            System.out.println("3 - Acessar Atendente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = lerInt(sc);

            switch (opcao) {
                case 1 -> menuGerente(sc, gerente);
                case 2 -> menuVendedor(sc, vendedor);
                case 3 -> menuAtendente(sc, atendente);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void menuGerente(Scanner sc, Gerente g) {
        int op;
        do {
            System.out.println("\n--- GERENTE --- Admin: " + g.isAdministrador());
            System.out.println("1 - Login");
            System.out.println("2 - Logoff");
            System.out.println("3 - Alterar dados");
            System.out.println("4 - Alterar senha");
            System.out.println("5 - Gerar relatorio financeiro");
            System.out.println("6 - Consultar vendas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt(sc);

            switch (op) {
                case 1 -> { System.out.print("Senha: "); g.realizarLogin(sc.nextLine()); }
                case 2 -> g.realizarLogoff();
                case 3 -> {
                    System.out.print("Novo nome: "); String n = sc.nextLine();
                    System.out.print("Novo email: "); String e = sc.nextLine();
                    try { g.alterarDados(n, e); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 4 -> {
                    System.out.print("Senha atual: "); String sAtual = sc.nextLine();
                    System.out.print("Nova senha: "); String sNova = sc.nextLine();
                    try { g.alterarSenha(sAtual, sNova); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 5 -> g.gerarRelatorioFinanceiro();
                case 6 -> g.consultarVendas();
                case 0 -> {}
                default -> System.out.println("Opcao invalida.");
            }

        } while (op != 0);
    }

    private static void menuVendedor(Scanner sc, Vendedor v) {
        int op;
        do {
            System.out.println("\n--- VENDEDOR --- Admin: " + v.isAdministrador());
            System.out.println("1 - Login");
            System.out.println("2 - Logoff");
            System.out.println("3 - Alterar dados");
            System.out.println("4 - Alterar senha");
            System.out.println("5 - Realizar venda");
            System.out.println("6 - Consultar vendas");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt(sc);

            switch (op) {
                case 1 -> { System.out.print("Senha: "); v.realizarLogin(sc.nextLine()); }
                case 2 -> v.realizarLogoff();
                case 3 -> {
                    System.out.print("Novo nome: "); String n = sc.nextLine();
                    System.out.print("Novo email: "); String e = sc.nextLine();
                    try { v.alterarDados(n, e); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 4 -> {
                    System.out.print("Senha atual: "); String sAtual = sc.nextLine();
                    System.out.print("Nova senha: "); String sNova = sc.nextLine();
                    try { v.alterarSenha(sAtual, sNova); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 5 -> v.realizarVenda();
                case 6 -> v.consultarVendas();
                case 0 -> {}
                default -> System.out.println("Opcao invalida.");
            }

        } while (op != 0);
    }

    private static void menuAtendente(Scanner sc, Atendente a) {
        int op;
        do {
            System.out.println("\n--- ATENDENTE --- Admin: " + a.isAdministrador());
            System.out.println("1 - Login");
            System.out.println("2 - Logoff");
            System.out.println("3 - Alterar dados");
            System.out.println("4 - Alterar senha");
            System.out.println("5 - Receber pagamentos");
            System.out.println("6 - Fechar caixa");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            op = lerInt(sc);

            switch (op) {
                case 1 -> { System.out.print("Senha: "); a.realizarLogin(sc.nextLine()); }
                case 2 -> a.realizarLogoff();
                case 3 -> {
                    System.out.print("Novo nome: "); String n = sc.nextLine();
                    System.out.print("Novo email: "); String e = sc.nextLine();
                    try { a.alterarDados(n, e); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 4 -> {
                    System.out.print("Senha atual: "); String sAtual = sc.nextLine();
                    System.out.print("Nova senha: "); String sNova = sc.nextLine();
                    try { a.alterarSenha(sAtual, sNova); } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
                }
                case 5 -> {
                    System.out.print("Valor (R$): ");
                    double valor = lerDouble(sc);
                    a.receberPagamentos(valor);
                }
                case 6 -> a.fecharCaixa();
                case 0 -> {}
                default -> System.out.println("Opcao invalida.");
            }

        } while (op != 0);
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
