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

    private List<Projeto> listaProjetos;
    private List<Usuario> listaUsuarios;

    public CadastroProjeto(List<Projeto> listaProjetos, List<Usuario> listaUsuarios) {
        this.listaProjetos = listaProjetos;
        this.listaUsuarios = listaUsuarios;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gerenciamento de Projetos"));

        String[] colunas = {"ID", "Nome", "Responsável", "Descrição", "Status", "Início", "Fim"};
        modeloTabelaProjetos = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProjetos = new JTable(modeloTabelaProjetos);
        tabelaProjetos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaProjetos);
        add(scroll, BorderLayout.CENTER);

        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição de Projetos"));

        JPanel painelCampos = new JPanel(new GridLayout(0, 2, 5, 5));

        painelCampos.add(new JLabel("Nome do Projeto:"));
        txtNomeProjeto = new JTextField();
        painelCampos.add(txtNomeProjeto);

        painelCampos.add(new JLabel("Responsável:"));
        cmbResponsavelProjeto = new JComboBox<>();
        painelCampos.add(cmbResponsavelProjeto);

        painelCampos.add(new JLabel("Descrição:"));
        txtDescricaoProjeto = new JTextField();
        painelCampos.add(txtDescricaoProjeto);

        painelCampos.add(new JLabel("Status:"));
        cmbStatusProjeto = new JComboBox<>(new String[]{"Planejado", "Em andamento", "Concluído"});
        painelCampos.add(cmbStatusProjeto);

        painelCampos.add(new JLabel("Data Início (dd/MM/yyyy):"));
        txtDataInicioProjeto = new JTextField();
        painelCampos.add(txtDataInicioProjeto);

        painelCampos.add(new JLabel("Data Fim (dd/MM/yyyy):"));
        txtDataFimProjeto = new JTextField();
        painelCampos.add(txtDataFimProjeto);

        painelCadastro.add(painelCampos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnNovoProjeto = new JButton("Novo");
        btnSalvarProjeto = new JButton("Salvar");
        btnExcluirProjeto = new JButton("Excluir");

        painelBotoes.add(btnNovoProjeto);
        painelBotoes.add(btnSalvarProjeto);
        painelBotoes.add(btnExcluirProjeto);

        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCadastro, BorderLayout.EAST);

        btnNovoProjeto.addActionListener(e -> limparFormulario());
        btnSalvarProjeto.addActionListener(e -> salvarProjeto());
        btnExcluirProjeto.addActionListener(e -> excluirProjeto());

        atualizarResponsaveis();
        carregarProjetosFixos();
        preencherTabela();
    }

    private void carregarProjetosFixos() {
        if (listaProjetos.isEmpty()) {
            Usuario responsavelPadrao = listaUsuarios.isEmpty() ? new Usuario("0", "Admin", "", "", "", "", "Admin") : listaUsuarios.get(0);

            listaProjetos.add(new Projeto(1, "Projeto A", "Descrição A", "01/09/2025", "30/09/2025", responsavelPadrao, "Planejado"));
            listaProjetos.add(new Projeto(2, "Projeto B", "Descrição B", "05/09/2025", "10/10/2025", responsavelPadrao, "Em andamento"));
        }
    }

    public void atualizarResponsaveis() {
        if (cmbResponsavelProjeto == null) return;
        cmbResponsavelProjeto.removeAllItems();
        for (Usuario u : listaUsuarios) {
            cmbResponsavelProjeto.addItem(u);
        }
        if (cmbResponsavelProjeto.getItemCount() > 0) {
            cmbResponsavelProjeto.setSelectedIndex(0);
        }
    }

    private void preencherTabela() {
        modeloTabelaProjetos.setRowCount(0);
        for (Projeto p : listaProjetos) {
            Object[] linha = {
                    p.getId(),
                    p.getNome(),
                    p.getResponsavel().getNome(),
                    p.getDescricao(),
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
        cmbResponsavelProjeto.setSelectedIndex(-1);
        cmbStatusProjeto.setSelectedIndex(0);
        tabelaProjetos.clearSelection();
    }

    private void salvarProjeto() {
        try {
            String nome = txtNomeProjeto.getText();
            String descricao = txtDescricaoProjeto.getText();
            String dataInicio = txtDataInicioProjeto.getText();
            String dataFim = txtDataFimProjeto.getText();
            Usuario responsavel = (Usuario) cmbResponsavelProjeto.getSelectedItem();
            String status = (String) cmbStatusProjeto.getSelectedItem();

            int linhaSelecionada = tabelaProjetos.getSelectedRow();
            if (linhaSelecionada == -1) {
                Projeto novo = new Projeto(listaProjetos.size() + 1, nome, descricao, dataInicio, dataFim, responsavel, status);
                listaProjetos.add(novo);
                JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso!");
            } else {
                Projeto existente = listaProjetos.get(linhaSelecionada);
                existente.setNome(nome);
                existente.setDescricao(descricao);
                existente.setDataInicio(dataInicio);
                existente.setDataFim(dataFim);
                existente.setResponsavel(responsavel);
                existente.setStatus(status);
                JOptionPane.showMessageDialog(this, "Projeto atualizado com sucesso!");
            }
            limparFormulario();
            preencherTabela();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar projeto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProjeto() {
        int linhaSelecionada = tabelaProjetos.getSelectedRow();
        if (linhaSelecionada != -1) {
            Projeto selecionado = listaProjetos.get(linhaSelecionada);

            if (selecionado.getId() <= 2) {
                JOptionPane.showMessageDialog(this, "Projetos fixos não podem ser excluídos!");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este projeto?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                listaProjetos.remove(linhaSelecionada);
                limparFormulario();
                preencherTabela();
                JOptionPane.showMessageDialog(this, "Projeto excluído com sucesso!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um projeto para excluir.");
        }
    }
}
