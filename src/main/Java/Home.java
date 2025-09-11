import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    private List<Projetos> listaProjetos = new ArrayList<>();

    public Home() {
        setTitle("Sistema - Home");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titulo = new JLabel("Bem-vindo ao Sistema", JLabel.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Abas
        JTabbedPane abas = new JTabbedPane();

        // Aba Início
        JPanel abaInicio = new JPanel();
        abaInicio.add(new JLabel("Você está na página inicial!"));
        abas.addTab("Início", abaInicio);;

        // Aba Usuários
        JPanel abaUsuario = new JPanel();
        abaUsuario.add(new JLabel("Gerenciamento de usuários aqui."));
        abas.addTab("Usuários", abaUsuario);;

        // Aba Projetos
        JPanel painelProjetos = new JPanel(new BorderLayout());
        JTextArea areaProjetos = new JTextArea();
        painelProjetos.add(new JScrollPane(areaProjetos), BorderLayout.CENTER);

        JButton btnMostrarProjetos = new JButton("Mostrar Projetos");
        painelProjetos.add(btnMostrarProjetos, BorderLayout.SOUTH);

        abas.addTab("Projetos", painelProjetos);

        // Botão Mostrar Projetos -> exibe no JTextArea
        btnMostrarProjetos.addActionListener(e -> {
            areaProjetos.setText(""); // limpa
            for (Projetos p : listaProjetos) {
                areaProjetos.append(p + "\n");
            }
        });

        // Aba Equipe
        JPanel abaEquipe = new JPanel();
        abaEquipe.add(new JLabel("Área de equipes aqui."));
        abas.addTab("Equipe", abaEquipe);;

        // Aba Tarefas
        JPanel abaTarefas = new JPanel();
        abaTarefas.add(new JLabel("Gerenciamento de tarefas aqui."));
        abas.addTab("Tarefas", abaTarefas);;

        // Aba Relatórios
        JPanel abaRelatorios = new JPanel(new BorderLayout());
        JTextArea areaRelatorio = new JTextArea();
        abaRelatorios.add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

// Painel de botões
        JPanel painelBotoesRelatorio = new JPanel(new FlowLayout());
        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        JButton btnImprimirRelatorio = new JButton("Imprimir Relatório");

        painelBotoesRelatorio.add(btnGerarRelatorio);
        painelBotoesRelatorio.add(btnImprimirRelatorio);

        abaRelatorios.add(painelBotoesRelatorio, BorderLayout.SOUTH);

// Ação de gerar relatório
        btnGerarRelatorio.addActionListener(e -> {
            areaRelatorio.setText("===== RELATÓRIO DE PROJETOS =====\n");
            if (listaProjetos.isEmpty()) {
                areaRelatorio.append("Nenhum projeto cadastrado.\n");
            } else {
                for (Projetos p : listaProjetos) {
                    areaRelatorio.append(p + "\n");
                }
            }
            areaRelatorio.append("=================================\n");
        });

// Ação de imprimir relatório
        btnImprimirRelatorio.addActionListener(e -> {
            try {
                boolean complete = areaRelatorio.print(); // usa o print nativo do JTextArea
                if (complete) {
                    JOptionPane.showMessageDialog(this, "Relatório enviado para impressão!");
                } else {
                    JOptionPane.showMessageDialog(this, "Impressão cancelada!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage());
            }
        });

        abas.addTab("Relatórios", abaRelatorios);


        // Aba Sair
        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);

        add(painelPrincipal);

        // Adiciona as abas ao painel principal
        painelPrincipal.add(abas, BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        add(painelPrincipal);

        // Carrega alguns projetos de exemplo
        carregarProjetosExemplo();

        setVisible(true);
    }
    // Exemplos de projetos
    private void carregarProjetosExemplo() {
        listaProjetos.add(new Projetos("Projeto A", "Descrição A", "01/09/2025", "30/09/2025", "Planejado", "Caio"));
        listaProjetos.add(new Projetos("Projeto B", "Descrição B", "05/09/2025", "10/10/2025", "Em andamento", "Caio"));
    }
}
