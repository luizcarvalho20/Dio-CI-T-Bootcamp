import java.util.*;

public class Main {

    static Object parseValor(String tipo, String valor) {
        switch (tipo.toLowerCase()) {
            case "texto": return valor;
            case "inteiro": return Integer.parseInt(valor);
            case "float": return Double.parseDouble(valor);
            case "booleano": return valor.equalsIgnoreCase("true");
            case "data": return valor;        // yyyy-mm-dd
            case "data_hora": return valor;   // yyyy-mm-dd HH:mm
            default: throw new RuntimeException("Tipo inválido");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Object> dados = new LinkedHashMap<>();

        System.out.println("Informe os dados no formato:");
        System.out.println("NOME_CAMPO;VALOR;TIPO;");
        System.out.println("Tipos aceitos: texto, inteiro, float, booleano, data, data_hora");
        System.out.println("Pressione ENTER em branco para finalizar.\n");

        while (true) {
            System.out.print("Digite um campo: ");
            String linha = sc.nextLine();

            if (linha.trim().isEmpty()) break;

            linha = linha.replace(";", "");
            String[] partes = linha.split(";", -1);
            if (partes.length < 3) {
                System.out.println("Formato inválido.");
                continue;
            }

            try {
                dados.put(partes[0], parseValor(partes[2], partes[1]));
                System.out.println("Campo adicionado com sucesso.\n");
            } catch (Exception e) {
                System.out.println("Erro ao processar o campo.\n");
            }
        }

        System.out.println("\nJSON:");
        System.out.println(dados);

        System.out.println("\nXML:");
        System.out.println("<dados>");
        for (var e : dados.entrySet())
            System.out.println("  <" + e.getKey() + ">" + e.getValue() + "</" + e.getKey() + ">");
        System.out.println("</dados>");

        System.out.println("\nYAML:");
        for (var e : dados.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());

        sc.close();
    }
}
