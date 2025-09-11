import java.util.List;

public class Relatorio {

    public static void gerarRelatorioProjetos(List<Projetos> projetos) {
        System.out.println("===== RELATÓRIO DE PROJETOS =====");
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto cadastrado.");
        } else {
            for (Projetos p : projetos) {
                System.out.println(p);
            }
        }
        System.out.println("=================================");
    }
}
