import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    private List<Projetos> listaProjetos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Tarefas> listaTarefas = new ArrayList<>();

    private JTable tabelaTarefas;
    private DefaultTableModel modeloTabelaTarefas;
    private JTextField txtTitulo, txtDescricao, txtDataInicioPrevista, txtDataFimPrevista, txtDataInicioReal, txtDataFimReal;
    private JComboBox<String> cmbStatus;
    private JComboBox<Projetos> cmbProjetoVinculado;
    private JComboBox<Usuario> cmbResponsavel;

    public Home() {
        setTitle("Sistema - Gestão de Projetos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Bem-vindo ao Sistema de Gestão", JLabel.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();

        JPanel abaInicio = new JPanel();
        abaInicio.add(new JLabel("Você está na página inicial!"));
        abas.addTab("Início", abaInicio);

        JPanel abaUsuario = new JPanel();
        abaUsuario.add(new JLabel("Gerenciamento de usuários aqui."));
        abas.addTab("Usuários", abaUsuario);

        JPanel painelProjetos = new JPanel(new BorderLayout());
        JTextArea areaProjetos = new JTextArea();
        painelProjetos.add(new JScrollPane(areaProjetos), BorderLayout.CENTER);
        JButton btnMostrarProjetos = new JButton("Mostrar Projetos");
        painelProjetos.add(btnMostrarProjetos, BorderLayout.SOUTH);
        abas.addTab("Projetos", painelProjetos);
        btnMostrarProjetos.addActionListener(e -> {
            areaProjetos.setText("");
            for (Projetos p : listaProjetos) {
                areaProjetos.append(p + "\n");
            }
        });

        JPanel abaEquipe = new JPanel();
        abaEquipe.add(new JLabel("Área de equipes aqui."));
        abas.addTab("Equipe", abaEquipe);

        JPanel abaTarefas = criarPainelTarefas();
        abas.addTab("Tarefas", abaTarefas);

        JPanel abaRelatorios = new JPanel(new BorderLayout());
        JTextArea areaRelatorio = new JTextArea();
        abaRelatorios.add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);
        JPanel painelBotoesRelatorio = new JPanel(new FlowLayout());
        JButton btnGerarRelatorio = new JButton("Gerar Relatório");
        JButton btnImprimirRelatorio = new JButton("Imprimir Relatório");
        painelBotoesRelatorio.add(btnGerarRelatorio);
        painelBotoesRelatorio.add(btnImprimirRelatorio);
        abaRelatorios.add(painelBotoesRelatorio, BorderLayout.SOUTH);
        btnGerarRelatorio.addActionListener(e -> {
             areaRelatorio.setText("===== RELATÓRIO DE PROJETOS =====\n");
             if (listaProjetos.isEmpty()) {
                 areaRelatorio.append("Nenhum projeto cadastrado.\n");
             } else {
                 for (Projetos p : listaProjetos) {
                     areaRelatorio.append(p.getTitulo() + "\n");
                 }
             }
             areaRelatorio.append("=================================\n");
        });
        btnImprimirRelatorio.addActionListener(e -> {
            try {
                boolean complete = areaRelatorio.print();
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

        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);

        carregarDadosExemplo();
        preencherTabelaTarefas();
        setVisible(true);
    }
    
    private JPanel criarPainelTarefas() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        
        String[] colunas = {"Título", "Projeto", "Responsável", "Status", "Início Previsto", "Fim Previsto"};
        modeloTabelaTarefas = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaTarefas = new JTable(modeloTabelaTarefas);
        tabelaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tabelaTarefas);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        tabelaTarefas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaTarefas.getSelectedRow() != -1) {
                exibirTarefaSelecionada();
            }
        });

        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição de Tarefas"));

        JPanel painelCampos = new JPanel(new GridLayout(0, 2, 5, 5));
        painelCampos.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painelCampos.add(txtTitulo);

        painelCampos.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        painelCampos.add(txtDescricao);

        painelCampos.add(new JLabel("Projeto:"));
        cmbProjetoVinculado = new JComboBox<>();
        painelCampos.add(cmbProjetoVinculado);
        
        painelCampos.add(new JLabel("Responsável:"));
        cmbResponsavel = new JComboBox<>();
        painelCampos.add(cmbResponsavel);

        painelCampos.add(new JLabel("Status:"));
        String[] statusOpcoes = {"Pendente", "Em execução", "Concluída"};
        cmbStatus = new JComboBox<>(statusOpcoes);
        painelCampos.add(cmbStatus);

        painelCampos.add(new JLabel("Início Previsto (dd/MM/yyyy):"));
        txtDataInicioPrevista = new JTextField();
        painelCampos.add(txtDataInicioPrevista);

        painelCampos.add(new JLabel("Fim Previsto (dd/MM/yyyy):"));
        txtDataFimPrevista = new JTextField();
        painelCampos.add(txtDataFimPrevista);

        painelCampos.add(new JLabel("Início Real (dd/MM/yyyy):"));
        txtDataInicioReal = new JTextField();
        painelCampos.add(txtDataInicioReal);

        painelCampos.add(new JLabel("Fim Real (dd/MM/yyyy):"));
        txtDataFimReal = new JTextField();
        painelCampos.add(txtDataFimReal);

        painelCadastro.add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnNovoTarefa = new JButton("Novo");
        JButton btnSalvarTarefa = new JButton("Salvar");
        JButton btnExcluirTarefa = new JButton("Excluir");
        painelBotoes.add(btnNovoTarefa);
        painelBotoes.add(btnSalvarTarefa);
        painelBotoes.add(btnExcluirTarefa);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        btnNovoTarefa.addActionListener(e -> limparFormularioTarefas());
        btnSalvarTarefa.addActionListener(e -> salvarTarefa());
        btnExcluirTarefa.addActionListener(e -> excluirTarefa());

        painelPrincipal.add(painelCadastro, BorderLayout.EAST);
        
        return painelPrincipal;
    }

    private void carregarDadosExemplo() {
        listaProjetos.add(new Projetos("Projeto A", "Descrição A", "01/09/2025", "30/09/2025", "Planejado", "Caio"));
        listaProjetos.add(new Projetos("Projeto B", "Descrição B", "05/09/2025", "10/10/2025", "Em andamento", "Caio"));
        
        listaUsuarios.add(new Usuario("1", "Admin", "000", "admin@e.com", "admin", "admin", "Admin"));
        listaUsuarios.add(new Usuario("2", "Adriano", "333", "adriano@e.com", "adriano", "colaborador", "Colaborador"));

        for (Projetos p : listaProjetos) {
            cmbProjetoVinculado.addItem(p);
        }
        for (Usuario u : listaUsuarios) {
            cmbResponsavel.addItem(u);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        listaTarefas.add(new Tarefas("Implementar Login", "Desenvolver a tela de login", listaProjetos.get(0), listaUsuarios.get(0), "Concluída", LocalDate.parse("15/09/2025", formatter), LocalDate.parse("18/09/2025", formatter)));
        listaTarefas.get(0).setDataInicioReal(LocalDate.parse("15/09/2025", formatter));
        listaTarefas.get(0).setDataFimReal(LocalDate.parse("17/09/2025", formatter));
        listaTarefas.add(new Tarefas("Criar CRUD", "Desenvolver o CRUD de usuários", listaProjetos.get(1), listaUsuarios.get(1), "Em execução", LocalDate.parse("20/09/2025", formatter), LocalDate.parse("25/09/2025", formatter)));
    }

    private void preencherTabelaTarefas() {
        modeloTabelaTarefas.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Tarefas t : listaTarefas) {
            Object[] linha = new Object[]{
                t.getTitulo(),
                t.getProjetoVinculado().getTitulo(),
                t.getResponsavel().getNome(),
                t.getStatus(),
                t.getDataInicioPrevista() != null ? t.getDataInicioPrevista().format(formatter) : "",
                t.getDataFimPrevista() != null ? t.getDataFimPrevista().format(formatter) : ""
            };
            modeloTabelaTarefas.addRow(linha);
        }
    }
    
    private void exibirTarefaSelecionada() {
        int linhaSelecionada = tabelaTarefas.getSelectedRow();
        if (linhaSelecionada != -1) {
            Tarefas tarefa = listaTarefas.get(linhaSelecionada);
            txtTitulo.setText(tarefa.getTitulo());
            txtDescricao.setText(tarefa.getDescricao());
            cmbProjetoVinculado.setSelectedItem(tarefa.getProjetoVinculado());
            cmbResponsavel.setSelectedItem(tarefa.getResponsavel());
            cmbStatus.setSelectedItem(tarefa.getStatus());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataInicioPrevista.setText(tarefa.getDataInicioPrevista().format(formatter));
            txtDataFimPrevista.setText(tarefa.getDataFimPrevista().format(formatter));
            txtDataInicioReal.setText(tarefa.getDataInicioReal() != null ? tarefa.getDataInicioReal().format(formatter) : "");
            txtDataFimReal.setText(tarefa.getDataFimReal() != null ? tarefa.getDataFimReal().format(formatter) : "");
        }
    }

    private void limparFormularioTarefas() {
        tabelaTarefas.clearSelection();
        txtTitulo.setText("");
        txtDescricao.setText("");
        cmbProjetoVinculado.setSelectedIndex(-1);
        cmbResponsavel.setSelectedIndex(-1);
        cmbStatus.setSelectedIndex(0);
        txtDataInicioPrevista.setText("");
        txtDataFimPrevista.setText("");
        txtDataInicioReal.setText("");
        txtDataFimReal.setText("");
    }

    private void salvarTarefa() {
        try {
            String titulo = txtTitulo.getText();
            String descricao = txtDescricao.getText();
            Projetos projeto = (Projetos) cmbProjetoVinculado.getSelectedItem();
            Usuario responsavel = (Usuario) cmbResponsavel.getSelectedItem();
            String status = (String) cmbStatus.getSelectedItem();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate inicioPrevisto = LocalDate.parse(txtDataInicioPrevista.getText(), formatter);
            LocalDate fimPrevisto = LocalDate.parse(txtDataFimPrevista.getText(), formatter);
            
            int linhaSelecionada = tabelaTarefas.getSelectedRow();
            if (linhaSelecionada == -1) {
                Tarefas novaTarefa = new Tarefas(titulo, descricao, projeto, responsavel, status, inicioPrevisto, fimPrevisto);
                listaTarefas.add(novaTarefa);
                JOptionPane.showMessageDialog(this, "Tarefa cadastrada com sucesso!");
            } else {
                Tarefas tarefaExistente = listaTarefas.get(linhaSelecionada);
                tarefaExistente.setTitulo(titulo);
                tarefaExistente.setDescricao(descricao);
                tarefaExistente.setProjetoVinculado(projeto);
                tarefaExistente.setResponsavel(responsavel);
                tarefaExistente.setStatus(status);
                tarefaExistente.setDataInicioPrevista(inicioPrevisto);
                tarefaExistente.setDataFimPrevista(fimPrevisto);
                if (!txtDataInicioReal.getText().isEmpty()) {
                    tarefaExistente.setDataInicioReal(LocalDate.parse(txtDataInicioReal.getText(), formatter));
                }
                if (!txtDataFimReal.getText().isEmpty()) {
                    tarefaExistente.setDataFimReal(LocalDate.parse(txtDataFimReal.getText(), formatter));
                }
                JOptionPane.showMessageDialog(this, "Tarefa atualizada com sucesso!");
            }
            limparFormularioTarefas();
            preencherTabelaTarefas();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Erro de formato de data. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirTarefa() {
        int linhaSelecionada = tabelaTarefas.getSelectedRow();
        if (linhaSelecionada != -1) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir esta tarefa?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                listaTarefas.remove(linhaSelecionada);
                limparFormularioTarefas();
                preencherTabelaTarefas();
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
}