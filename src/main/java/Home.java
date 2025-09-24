import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Home extends JFrame {
    private List<Projeto> listaProjetos = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Tarefas> listaTarefas = new ArrayList<>();

    private JTable tabelaTarefas;
    private DefaultTableModel modeloTabelaTarefas;
    private JTextField txtTitulo, txtDescricao, txtDataInicioPrevista, txtDataFimPrevista, txtDataInicioReal, txtDataFimReal;
    private JComboBox<String> cmbStatus;
    private JComboBox<Projeto> cmbProjetoVinculado;
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

        JPanel abaInicio = new JPanel();
        abaInicio.add(new JLabel("Você está na página inicial!"));
        abas.addTab("Início", abaInicio);

        TelaUsuarios telaUsuarios = new TelaUsuarios();
        abas.addTab("Usuários", telaUsuarios);
        CadastroProjeto telaProjetos = new CadastroProjeto(listaProjetos, listaUsuarios);
        abas.addTab("Projetos", telaProjetos);

        JPanel painelTarefas = criarPainelTarefas();
        abas.addTab("Tarefas", painelTarefas);

        Relatorio telaRelatorios = new Relatorio(listaProjetos, listaTarefas);
        abas.addTab("Relatórios", telaRelatorios);

        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);
        add(painelPrincipal);

        carregarDadosExemplo();
        telaUsuarios.setListaUsuarios(listaUsuarios);
        telaProjetos.atualizarResponsaveis();
        preencherTabelaTarefas();

        telaUsuarios.addPropertyChangeListener("usuariosAtualizados", evt -> {
            telaProjetos.atualizarResponsaveis();
            atualizarResponsaveisTarefas();
        });

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
        btnNovoTarefa = new JButton("Novo");
        btnSalvarTarefa = new JButton("Salvar");
        btnExcluirTarefa = new JButton("Excluir");
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
        listaUsuarios.add(new Usuario("1", "Admin", "000", "admin@e.com", "admin", "admin", "Administrador"));
        listaUsuarios.add(new Usuario("2", "Adriano", "333", "adriano@e.com", "adriano", "colaborador", "Colaborador"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        listaTarefas.add(new Tarefas("Tarefa 1", "Implementar login", listaProjetos.get(0), listaUsuarios.get(0), "Concluída",
                LocalDate.parse("15/09/2025", formatter), LocalDate.parse("18/09/2025", formatter)));
        listaTarefas.add(new Tarefas("Tarefa 2", "Criar interface de cadastro", listaProjetos.get(1), listaUsuarios.get(1), "Em execução",
                LocalDate.parse("20/09/2025", formatter), LocalDate.parse("25/09/2025", formatter)));

        for (Projeto p : listaProjetos) {
            cmbProjetoVinculado.addItem(p);
        }
        for (Usuario u : listaUsuarios) {
            cmbResponsavel.addItem(u);
        }
    }

    private void preencherTabelaTarefas() {
        modeloTabelaTarefas.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Tarefas t : listaTarefas) {
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
            Projeto projeto = (Projeto) cmbProjetoVinculado.getSelectedItem();
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

    private void atualizarResponsaveisTarefas() {
        cmbResponsavel.removeAllItems();
        for (Usuario u : listaUsuarios) {
            cmbResponsavel.addItem(u);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Home());
    }
}
