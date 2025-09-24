import java.util.ArrayList;
import java.util.List;

public class CadastroProjeto {
    private static List<Projeto> projetos = new ArrayList<>();

    public static void adicionarProjeto(Projeto projeto) {
        projetos.add(projeto);
    }

    public static List<Projeto> getProjetos() {
        return new ArrayList<>(projetos);
    }

    public static boolean removerProjetoPorId(int id) {
        return projetos.removeIf(p -> p.getId() == id);
    }

    public static void inicializarDadosExemplo() {
        if (projetos.isEmpty()) {
            adicionarProjeto(new Projeto(1, "Projeto A", "Descrição A", "2023-10-01", "2023-10-15", "João", "Equipe 1", "Pendente"));
            adicionarProjeto(new Projeto(2, "Projeto B", "Descrição B", "2023-10-05", "2023-11-01", "Maria", "Equipe 2", "Em Execução"));
            adicionarProjeto(new Projeto(3, "Projeto C", "Descrição C", "2023-10-10", "2023-10-20", "Pedro", "Equipe 1", "Concluída"));
            adicionarProjeto(new Projeto(4, "Projeto D", "Descrição D", "2023-10-15", "2023-11-10", "Ana", "Equipe 3", "Pendente"));
        }
    }

    public static void limparProjetos() {
        projetos.clear();
    }
}