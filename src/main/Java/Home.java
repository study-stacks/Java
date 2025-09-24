import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    private List<Projetos> listaProjetos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Tarefas> listaTarefas = new ArrayList<>();

    // Componentes para a aba de Tarefas
    private JTable tabelaTarefas;
    private DefaultTableModel modeloTabelaTarefas;
    private JTextField txtTitulo, txtDescricao, txtDataInicioPrevista, txtDataFimPrevista, txtDataInicioReal, txtDataFimReal;
    private JComboBox<String> cmbStatus;
    private JComboBox<Projetos> cmbProjetoVinculado;
    private JComboBox<Usuario> cmbResponsavel;
    private JButton btnNovoTarefa, btnSalvarTarefa, btnExcluirTarefa;

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

        // Aba Início
        JPanel abaInicio = new JPanel();
        abaInicio.add(new JLabel("Você está na página inicial!"));
        abas.addTab("Início", abaInicio);

        // Aba Usuários (Corrigido)
        TelaUsuarios telaUsuarios = new TelaUsuarios();
        abas.addTab("Usuários", telaUsuarios);

        // --- ABA DE PROJETOS ---
        JPanel painelProjetos = new JPanel(new BorderLayout(10, 10));
        painelProjetos.setBorder(BorderFactory.createTitledBorder("Gerenciamento de Projetos"));

// Tabela de projetos
        String[] colunasProjetos = {"Nome", "Responsável", "Descrição", "Status", "Início", "Fim"};
        DefaultTableModel modeloTabelaProjetos = new DefaultTableModel(null, colunasProjetos) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabelaProjetos = new JTable(modeloTabelaProjetos);
        tabelaProjetos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollProjetos = new JScrollPane(tabelaProjetos);
        painelProjetos.add(scrollProjetos, BorderLayout.CENTER);

// Painel de cadastro de projetos
        JPanel painelCadastroProjetos = new JPanel(new BorderLayout(10, 10));
        painelCadastroProjetos.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição de Projetos"));

        JPanel painelCamposProjetos = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtNomeProjeto = new JTextField();
        JTextArea txtDescricaoProjeto = new JTextArea(3, 20);
        JComboBox<Usuario> cmbResponsavelProjeto = new JComboBox<>();
        JComboBox<String> cmbStatusProjeto = new JComboBox<>(new String[]{"Planejado", "Em andamento", "Concluído"});
        JTextField txtDataInicioProjeto = new JTextField();
        JTextField txtDataFimProjeto = new JTextField();

        painelCamposProjetos.add(new JLabel("Nome do Projeto:"));
        painelCamposProjetos.add(txtNomeProjeto);

        painelCamposProjetos.add(new JLabel("Responsável:"));
        painelCamposProjetos.add(cmbResponsavelProjeto);

        painelCamposProjetos.add(new JLabel("Descrição:"));
        painelCamposProjetos.add(new JScrollPane(txtDescricaoProjeto));

        painelCamposProjetos.add(new JLabel("Status:"));
        painelCamposProjetos.add(cmbStatusProjeto);

        painelCamposProjetos.add(new JLabel("Data Início (dd/MM/yyyy):"));
        painelCamposProjetos.add(txtDataInicioProjeto);

        painelCamposProjetos.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        painelCamposProjetos.add(txtDataFimProjeto);

        painelCadastroProjetos.add(painelCamposProjetos, BorderLayout.NORTH);

// Botões
        JPanel painelBotoesProjetos = new JPanel(new FlowLayout());
        JButton btnNovoProjeto = new JButton("Novo");
        JButton btnSalvarProjeto = new JButton("Salvar");
        JButton btnExcluirProjeto = new JButton("Excluir");
        painelBotoesProjetos.add(btnNovoProjeto);
        painelBotoesProjetos.add(btnSalvarProjeto);
        painelBotoesProjetos.add(btnExcluirProjeto);
        painelCadastroProjetos.add(painelBotoesProjetos, BorderLayout.SOUTH);

        painelProjetos.add(painelCadastroProjetos, BorderLayout.EAST);
        abas.addTab("Projetos", painelProjetos);

// === ABA RELATÓRIOS ===
        JPanel abaRelatorios = new JPanel(new BorderLayout(10, 10));
        JTextArea areaRelatorio = new JTextArea();
        areaRelatorio.setEditable(false);
        abaRelatorios.add(new JScrollPane(areaRelatorio), BorderLayout.CENTER);

// Filtros
        JPanel painelFiltros = new JPanel(new FlowLayout());
        JComboBox<String> cmbTipoRelatorio = new JComboBox<>(new String[]{"Projetos", "Tarefas"});
        JComboBox<String> cmbFiltroStatus = new JComboBox<>(new String[]{"Todos", "Planejado", "Em andamento", "Concluído", "Pendente", "Em execução"});
        JButton btnGerarRelatorio = new JButton("Gerar");
        JButton btnExportar = new JButton("Exportar TXT");
        JButton btnImprimir = new JButton("Imprimir");

        painelFiltros.add(new JLabel("Relatório:"));
        painelFiltros.add(cmbTipoRelatorio);
        painelFiltros.add(new JLabel("Status:"));
        painelFiltros.add(cmbFiltroStatus);
        painelFiltros.add(btnGerarRelatorio);
        painelFiltros.add(btnExportar);
        painelFiltros.add(btnImprimir);

        abaRelatorios.add(painelFiltros, BorderLayout.NORTH);

// Ações dos botões
        btnGerarRelatorio.addActionListener(e -> {
            areaRelatorio.setText("");
            String tipo = (String) cmbTipoRelatorio.getSelectedItem();
            String filtro = (String) cmbFiltroStatus.getSelectedItem();

            if ("Projetos".equals(tipo)) {
                areaRelatorio.append("===== RELATÓRIO DE PROJETOS =====\n");
                for (Projetos p : listaProjetos) {
                    if ("Todos".equals(filtro) || p.status.equals(filtro)) {
                        areaRelatorio.append("Projeto: " + p.getTitulo() + "\n");
                        areaRelatorio.append("Responsável: " + p.responsavel + "\n");
                        areaRelatorio.append("Status: " + p.status + "\n");
                        areaRelatorio.append("Período: " + p.dataInicio + " até " + p.dataFim + "\n");
                        areaRelatorio.append("Descrição: " + p.descricao + "\n");
                        areaRelatorio.append("---------------------------------\n");
                    }
                }
            } else {
                areaRelatorio.append("===== RELATÓRIO DE TAREFAS =====\n");
                for (Tarefas t : listaTarefas) {
                    if ("Todos".equals(filtro) || t.getStatus().equals(filtro)) {
                        areaRelatorio.append("Tarefa: " + t.getTitulo() + "\n");
                        areaRelatorio.append("Projeto: " + t.getProjetoVinculado().getTitulo() + "\n");
                        areaRelatorio.append("Responsável: " + t.getResponsavel().getNome() + "\n");
                        areaRelatorio.append("Status: " + t.getStatus() + "\n");
                        areaRelatorio.append("Previsto: " + t.getDataInicioPrevista() + " até " + t.getDataFimPrevista() + "\n");
                        areaRelatorio.append("Real: " + t.getDataInicioReal() + " até " + t.getDataFimReal() + "\n");
                        areaRelatorio.append("---------------------------------\n");
                    }
                }
            }
        });

        btnExportar.addActionListener(e -> {
            try {
                java.io.FileWriter fw = new java.io.FileWriter("relatorio.txt");
                fw.write(areaRelatorio.getText());
                fw.close();
                JOptionPane.showMessageDialog(this, "Relatório exportado para relatorio.txt");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
            }
        });

        btnImprimir.addActionListener(e -> {
            try {
                areaRelatorio.print();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao imprimir: " + ex.getMessage());
            }
        });

        abas.addTab("Relatórios", abaRelatorios);

        // --- ABA DE TAREFAS ---
        JPanel painelTarefas = criarPainelTarefas();
        abas.addTab("Tarefas", painelTarefas);

        // Aba Sair
        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);

        carregarDadosExemplo();
        telaUsuarios.setListaUsuarios(listaUsuarios);
        preencherTabelaTarefas();
        setVisible(true);
    }

    // Método para criar o painel de Tarefas
    private JPanel criarPainelTarefas() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        // Painel esquerdo com a tabela
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

        // Adiciona um listener para a tabela
        tabelaTarefas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaTarefas.getSelectedRow() != -1) {
                exibirTarefaSelecionada();
            }
        });

        // Painel direito com o formulário de cadastro/edição
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
        btnNovoTarefa = new JButton("Novo");
        btnSalvarTarefa = new JButton("Salvar");
        btnExcluirTarefa = new JButton("Excluir");
        painelBotoes.add(btnNovoTarefa);
        painelBotoes.add(btnSalvarTarefa);
        painelBotoes.add(btnExcluirTarefa);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        // Adiciona listeners para os botões
        btnNovoTarefa.addActionListener(e -> limparFormularioTarefas());
        btnSalvarTarefa.addActionListener(e -> salvarTarefa());
        btnExcluirTarefa.addActionListener(e -> excluirTarefa());

        painelPrincipal.add(painelCadastro, BorderLayout.EAST);

        return painelPrincipal;
    }

    private void carregarDadosExemplo() {
        // Carrega projetos de exemplo
        listaProjetos.add(new Projetos("Projeto A", "Descrição A", "01/09/2025", "30/09/2025", "Planejado", "Caio"));
        listaProjetos.add(new Projetos("Projeto B", "Descrição B", "05/09/2025", "10/10/2025", "Em andamento", "Caio"));

        // Carrega usuários de exemplo
        listaUsuarios.add(new Usuario("1", "Admin", "000", "admin@e.com", "admin", "admin", "Admin"));
        listaUsuarios.add(new Usuario("2", "Adriano", "333", "adriano@e.com", "adriano", "colaborador", "Colaborador"));

        // Adiciona os itens aos JComboBox
        for (Projetos p : listaProjetos) {
            cmbProjetoVinculado.addItem(p);
        }
        for (Usuario u : listaUsuarios) {
            cmbResponsavel.addItem(u);
        }

        // Carrega tarefas de exemplo
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        listaTarefas.add(new Tarefas("Tarefa 1", "Implementar login", listaProjetos.get(0), listaUsuarios.get(0), "Concluída", LocalDate.parse("15/09/2025", formatter), LocalDate.parse("18/09/2025", formatter)));
        listaTarefas.add(new Tarefas("Tarefa 2", "Criar interface de cadastro", listaProjetos.get(1), listaUsuarios.get(1), "Em execução", LocalDate.parse("20/09/2025", formatter), LocalDate.parse("25/09/2025", formatter)));
    }

    private void preencherTabelaTarefas() {
        modeloTabelaTarefas.setRowCount(0); // Limpa a tabela
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

            Tarefas novaTarefa;
            int linhaSelecionada = tabelaTarefas.getSelectedRow();
            if (linhaSelecionada == -1) {
                novaTarefa = new Tarefas(titulo, descricao, projeto, responsavel, status, inicioPrevisto, fimPrevisto);
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

    // Você precisará da classe Usuario em seu próprio arquivo: Usuario.java
    public static class Usuario {
        private String id;
        private String nome;
        private String cpf;
        private String email;
        private String login;
        private String cargo;
        private String perfil;

        public Usuario(String id, String nome, String cpf, String email, String login, String cargo, String perfil) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
            this.login = login;
            this.cargo = cargo;
            this.perfil = perfil;
        }

        public String getNome() {
            return nome;
        }

        public String getCpf() { return cpf; }
        public String getEmail() { return email; }
        public String getCargo() { return cargo; }
        public String getLogin() { return login; }
        public String getPerfil() { return perfil; }
        public String getId() { return id; }

        @Override
        public String toString() {
            return nome;
        }
    }

    // Você precisará da classe Projetos em seu próprio arquivo: Projetos.java
    public static class Projetos {
        private String titulo;
        private String descricao;
        private String dataInicio;
        private String dataFim;
        private String status;
        private String responsavel;

        public Projetos(String titulo, String descricao, String dataInicio, String dataFim, String status, String responsavel) {
            this.titulo = titulo;
            this.descricao = descricao;
            this.dataInicio = dataInicio;
            this.dataFim = dataFim;
            this.status = status;
            this.responsavel = responsavel;
        }

        public String getTitulo() {
            return titulo;
        }

        @Override
        public String toString() {
            return titulo;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
}