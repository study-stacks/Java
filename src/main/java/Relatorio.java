import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.List;

public class Relatorio extends JPanel {

    private JTextArea areaRelatorio;
    private JComboBox<String> cmbTipoRelatorio;
    private JComboBox<String> cmbFiltroStatus;

    private ProjetoDAO projetoDAO;
    private TarefaDAO tarefaDAO;

    public Relatorio() {
        this.projetoDAO = new ProjetoDAO();
        this.tarefaDAO = new TarefaDAO();

        setLayout(new BorderLayout(10, 10));

        areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

        JPanel painelFiltros = new JPanel(new FlowLayout());
        cmbTipoRelatorio = new JComboBox<>(new String[]{"Projetos", "Tarefas"});
        cmbFiltroStatus = new JComboBox<>(new String[]{
                "Todos", "Planejado", "Em andamento", "Concluído",
                "Pendente", "Em execução", "Concluída"
        });
        JButton btnGerar = new JButton("Gerar");
        JButton btnExportar = new JButton("Exportar TXT");
        JButton btnImprimir = new JButton("Imprimir");

        painelFiltros.add(new JLabel("Relatório:"));
        painelFiltros.add(cmbTipoRelatorio);
        painelFiltros.add(new JLabel("Status:"));
        painelFiltros.add(cmbFiltroStatus);
        painelFiltros.add(btnGerar);
        painelFiltros.add(btnExportar);
        painelFiltros.add(btnImprimir);

        add(painelFiltros, BorderLayout.NORTH);

        btnGerar.addActionListener(e -> gerarRelatorio());
        btnExportar.addActionListener(e -> exportar());
        btnImprimir.addActionListener(e -> imprimir());
    }

    private void gerarRelatorio() {
        areaRelatorio.setText("");
        String tipo = (String) cmbTipoRelatorio.getSelectedItem();
        String filtro = (String) cmbFiltroStatus.getSelectedItem();

        if ("Projetos".equals(tipo)) {
            List<Projeto> listaProjetos = projetoDAO.listarTodos();
            areaRelatorio.append("===== RELATÓRIO DE PROJETOS =====\n");
            for (Projeto p : listaProjetos) {
                if ("Todos".equals(filtro) || p.getStatus().equals(filtro)) {
                    areaRelatorio.append("Projeto: " + p.getNome() + "\n");
                    areaRelatorio.append("Responsável: " +
                            (p.getResponsavel() != null ? p.getResponsavel().getNome() : "") + "\n");
                    areaRelatorio.append("Status: " + p.getStatus() + "\n");
                    areaRelatorio.append("Período: " + p.getDataInicio() + " até " + p.getDataFim() + "\n");
                    areaRelatorio.append("Descrição: " + p.getDescricao() + "\n");
                    areaRelatorio.append("---------------------------------\n");
                }
            }
        } else {
            List<Tarefas> listaTarefas = tarefaDAO.listarTodas();
            areaRelatorio.append("===== RELATÓRIO DE TAREFAS =====\n");
            for (Tarefas t : listaTarefas) {
                if ("Todos".equals(filtro) || t.getStatus().equals(filtro)) {
                    areaRelatorio.append("Tarefa: " + t.getTitulo() + "\n");
                    areaRelatorio.append("Projeto: " + t.getProjetoVinculado().getNome() + "\n");
                    areaRelatorio.append("Responsável: " + t.getResponsavel().getNome() + "\n");
                    areaRelatorio.append("Status: " + t.getStatus() + "\n");
                    areaRelatorio.append("Previsto: " + t.getDataInicioPrevista() + " até " + t.getDataFimPrevista() + "\n");
                    areaRelatorio.append("Real: " + t.getDataInicioReal() + " até " + t.getDataFimReal() + "\n");
                    areaRelatorio.append("---------------------------------\n");
                }
            }
        }
    }

    private void exportar() {
        try (FileWriter fw = new FileWriter("relatorio.txt")) {
            fw.write(areaRelatorio.getText());
            JOptionPane.showMessageDialog(this, "Relatório exportado para relatorio.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
        }
    }

    private void imprimir() {
        try {
            areaRelatorio.print();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage());
        }
    }
}