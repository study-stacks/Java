import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CadastroProjeto extends JPanel {

    private JTable tabelaProjetos;
    private DefaultTableModel modeloTabelaProjetos;
    private JTextField txtNomeProjeto, txtDescricaoProjeto, txtDataInicioProjeto, txtDataFimProjeto;
    private JComboBox<Usuario> cmbResponsavelProjeto;
    private JComboBox<String> cmbStatusProjeto;
    private JButton btnNovoProjeto, btnSalvarProjeto, btnExcluirProjeto;
    private ProjetoDAO projetoDAO;
    private UsuarioDAO usuarioDAO;

    public CadastroProjeto() {
        this.projetoDAO = new ProjetoDAO();
        this.usuarioDAO = new UsuarioDAO();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] colunas = {"ID", "Nome", "Responsável", "Status", "Início", "Fim"};
        modeloTabelaProjetos = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProjetos = new JTable(modeloTabelaProjetos);
        tabelaProjetos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tabelaProjetos), BorderLayout.CENTER);

        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição"));

        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCampos.add(new JLabel("Nome do Projeto:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNomeProjeto = new JTextField(15);
        painelCampos.add(txtNomeProjeto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Responsável:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbResponsavelProjeto = new JComboBox<>();
        painelCampos.add(cmbResponsavelProjeto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDescricaoProjeto = new JTextField();
        painelCampos.add(txtDescricaoProjeto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Status:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cmbStatusProjeto = new JComboBox<>(new String[]{"Planejado", "Em andamento", "Concluído"});
        painelCampos.add(cmbStatusProjeto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Data Início (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataInicioProjeto = new JTextField();
        painelCampos.add(txtDataInicioProjeto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        painelCampos.add(new JLabel("Data Fim (dd/MM/yyyy):"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataFimProjeto = new JTextField();
        painelCampos.add(txtDataFimProjeto, gbc);

        painelCadastro.add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnNovoProjeto = new JButton("Novo");
        btnSalvarProjeto = new JButton("Salvar");
        btnExcluirProjeto = new JButton("Excluir");
        painelBotoes.add(btnNovoProjeto);
        painelBotoes.add(btnSalvarProjeto);
        painelBotoes.add(btnExcluirProjeto);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        painelCadastro.setPreferredSize(new Dimension(400, 0));
        add(painelCadastro, BorderLayout.EAST);

        btnNovoProjeto.addActionListener(e -> limparFormulario());
        btnSalvarProjeto.addActionListener(e -> salvarProjeto());
        btnExcluirProjeto.addActionListener(e -> excluirProjeto());

        atualizarResponsaveis();
        preencherTabela();
    }

    public void atualizarResponsaveis() {
        cmbResponsavelProjeto.removeAllItems();
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        for (Usuario u : usuarios) {
            cmbResponsavelProjeto.addItem(u);
        }
    }

    private void preencherTabela() {
        modeloTabelaProjetos.setRowCount(0);
        List<Projeto> projetos = projetoDAO.listarTodos();
        for (Projeto p : projetos) {
            Object[] linha = {
                    p.getId(),
                    p.getNome(),
                    p.getResponsavel().getNome(),
                    p.getStatus(),
                    p.getDataInicio(),
                    p.getDataFim()
            };
            modeloTabelaProjetos.addRow(linha);
        }
    }

    private void limparFormulario() {
        txtNomeProjeto.setText("");
        txtDescricaoProjeto.setText("");
        txtDataInicioProjeto.setText("");
        txtDataFimProjeto.setText("");
        if (cmbResponsavelProjeto.getItemCount() > 0) {
            cmbResponsavelProjeto.setSelectedIndex(0);
        }
        cmbStatusProjeto.setSelectedIndex(0);
        tabelaProjetos.clearSelection();
    }

    private void salvarProjeto() {
        try {
            Usuario responsavel = (Usuario) cmbResponsavelProjeto.getSelectedItem();
            if (responsavel == null) {
                JOptionPane.showMessageDialog(this, "Por favor, selecione um responsável.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Projeto novo = new Projeto(
                    0,
                    txtNomeProjeto.getText(),
                    txtDescricaoProjeto.getText(),
                    txtDataInicioProjeto.getText(),
                    txtDataFimProjeto.getText(),
                    responsavel,
                    (String) cmbStatusProjeto.getSelectedItem()
            );
            projetoDAO.salvar(novo);
            JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso!");
            limparFormulario();
            preencherTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void excluirProjeto() {
        int linhaSelecionada = tabelaProjetos.getSelectedRow();
        if (linhaSelecionada != -1) {
            int idProjeto = (int) modeloTabelaProjetos.getValueAt(linhaSelecionada, 0);
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                projetoDAO.excluir(idProjeto);
                limparFormulario();
                preencherTabela();
                JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir.");
        }
    }
}