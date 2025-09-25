import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Home extends JFrame {
    private JTable tabelaTarefas;
    private DefaultTableModel modeloTabelaTarefas;
    private JTextField txtTitulo, txtDescricao, txtDataInicioPrevista, txtDataFimPrevista;
    private JComboBox<String> cmbStatus;
    private JComboBox<Projeto> cmbProjetoVinculado;
    private JComboBox<Usuario> cmbResponsavel;
    private JButton btnNovoTarefa, btnSalvarTarefa, btnExcluirTarefa;
    private UsuarioDAO usuarioDAO;
    private ProjetoDAO projetoDAO;
    private TarefaDAO tarefaDAO;
    private List<Tarefas> listaDeTarefasAtual;

    public Home() {
        this.usuarioDAO = new UsuarioDAO();
        this.projetoDAO = new ProjetoDAO();
        this.tarefaDAO = new TarefaDAO();

        setTitle("Sistema - Gestão de Projetos");
        setSize(1200, 700);
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

        TelaUsuarios telaUsuarios = new TelaUsuarios();
        abas.addTab("Usuários", telaUsuarios);
        CadastroProjeto telaProjetos = new CadastroProjeto();
        abas.addTab("Projetos", telaProjetos);

        JPanel painelTarefas = criarPainelTarefas();
        abas.addTab("Tarefas", painelTarefas);

        Relatorio telaRelatorios = new Relatorio();
        abas.addTab("Relatórios", telaRelatorios);

        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);

        preencherTabelaTarefas();

        abas.addChangeListener(e -> {
            if (abas.getSelectedIndex() == 3) {
                atualizarCombosTarefas();
            }
        });

        setVisible(true);
    }

    private JPanel criarPainelTarefas() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição de Tarefas"));

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTitulo = new JTextField(15);
        painelCampos.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDescricao = new JTextField();
        painelCampos.add(txtDescricao, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Projeto:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbProjetoVinculado = new JComboBox<>();
        painelCampos.add(cmbProjetoVinculado, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Responsável:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbResponsavel = new JComboBox<>();
        painelCampos.add(cmbResponsavel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbStatus = new JComboBox<>(new String[]{"Pendente", "Em execução", "Concluída"});
        painelCampos.add(cmbStatus, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Início Previsto (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataInicioPrevista = new JTextField();
        painelCampos.add(txtDataInicioPrevista, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Fim Previsto (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataFimPrevista = new JTextField();
        painelCampos.add(txtDataFimPrevista, gbc);

        painelCadastro.add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNovoTarefa = new JButton("Novo");
        btnSalvarTarefa = new JButton("Salvar");
        btnExcluirTarefa = new JButton("Excluir");
        painelBotoes.add(btnNovoTarefa);
        painelBotoes.add(btnSalvarTarefa);
        painelBotoes.add(btnExcluirTarefa);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        painelCadastro.setPreferredSize(new Dimension(400, 0));
        painelPrincipal.add(painelCadastro, BorderLayout.EAST);

        btnNovoTarefa.addActionListener(e -> limparFormularioTarefas());
        btnSalvarTarefa.addActionListener(e -> salvarTarefa());
        btnExcluirTarefa.addActionListener(e -> excluirTarefa());

        return painelPrincipal;
    }

    private void atualizarCombosTarefas() {
        cmbProjetoVinculado.removeAllItems();
        List<Projeto> projetos = projetoDAO.listarTodos();
        for (Projeto p : projetos) {
            cmbProjetoVinculado.addItem(p);
        }

        cmbResponsavel.removeAllItems();
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            cmbResponsavel.addItem(u);
        }
    }

    private void salvarTarefa() {
        try {
            Projeto projeto = (Projeto) cmbProjetoVinculado.getSelectedItem();
            Usuario responsavel = (Usuario) cmbResponsavel.getSelectedItem();
            if (projeto == null || responsavel == null) {
                JOptionPane.showMessageDialog(this, "Selecione um projeto e um responsável.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            Tarefas novaTarefa = new Tarefas(
                    0,
                    txtTitulo.getText(),
                    txtDescricao.getText(),
                    projeto,
                    responsavel,
                    (String) cmbStatus.getSelectedItem(),
                    LocalDate.parse(txtDataInicioPrevista.getText(), formatter),
                    LocalDate.parse(txtDataFimPrevista.getText(), formatter)
            );

            tarefaDAO.salvar(novaTarefa);
            JOptionPane.showMessageDialog(this, "Tarefa salva com sucesso!");
            limparFormularioTarefas();
            preencherTabelaTarefas();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar tarefa: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void preencherTabelaTarefas() {
        modeloTabelaTarefas.setRowCount(0);
        this.listaDeTarefasAtual = tarefaDAO.listarTodas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Tarefas t : listaDeTarefasAtual) {
            Object[] linha = {
                    t.getTitulo(),
                    t.getProjetoVinculado().getNome(),
                    t.getResponsavel().getNome(),
                    t.getStatus(),
                    t.getDataInicioPrevista() != null ? t.getDataInicioPrevista().format(formatter) : "",
                    t.getDataFimPrevista() != null ? t.getDataFimPrevista().format(formatter) : ""
            };
            modeloTabelaTarefas.addRow(linha);
        }
    }

    private void limparFormularioTarefas() {
        tabelaTarefas.clearSelection();
        txtTitulo.setText("");
        txtDescricao.setText("");
        if(cmbProjetoVinculado.getItemCount() > 0) cmbProjetoVinculado.setSelectedIndex(0);
        if(cmbResponsavel.getItemCount() > 0) cmbResponsavel.setSelectedIndex(0);
        cmbStatus.setSelectedIndex(0);
        txtDataInicioPrevista.setText("");
        txtDataFimPrevista.setText("");
    }

    private void excluirTarefa() {
        int linhaSelecionada = tabelaTarefas.getSelectedRow();
        if (linhaSelecionada != -1) {
            int confirmacao = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja excluir esta tarefa?", "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                Tarefas tarefaParaExcluir = listaDeTarefasAtual.get(linhaSelecionada);
                tarefaDAO.excluir(tarefaParaExcluir.getId());
                limparFormularioTarefas();
                preencherTabelaTarefas();
                JOptionPane.showMessageDialog(this, "Tarefa excluída com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma tarefa na tabela para excluir.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}