import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Projetos extends JPanel {
    JTable tabela;
    DefaultTableModel modelo;

    JTextField txtNome, txtDescricao, txtDataInicio, txtDataFim, txtResponsavel, txtEquipe;
    JComboBox<String> boxStatus;
    JButton btnSalvar, btnExcluir, btnAtualizar;

    public Projetos() {
        setLayout(null);

        String[] colunas = {"ID", "Nome", "Descrição", "Data Início", "Data Fim", "Responsável", "Equipe", "Status"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);

        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(10, 10, 850, 200);
        add(rolagem);

        JLabel l1 = new JLabel("Nome:");
        l1.setBounds(10, 230, 80, 25);
        add(l1);
        txtNome = new JTextField();
        txtNome.setBounds(90, 230, 200, 25);
        add(txtNome);

        JLabel l2 = new JLabel("Descrição:");
        l2.setBounds(310, 230, 80, 25);
        add(l2);
        txtDescricao = new JTextField();
        txtDescricao.setBounds(390, 230, 200, 25);
        add(txtDescricao);

        JLabel l3 = new JLabel("Data Início:");
        l3.setBounds(10, 270, 80, 25);
        add(l3);
        txtDataInicio = new JTextField();
        txtDataInicio.setBounds(90, 270, 100, 25);
        add(txtDataInicio);

        JLabel l4 = new JLabel("Data Fim:");
        l4.setBounds(200, 270, 80, 25);
        add(l4);
        txtDataFim = new JTextField();
        txtDataFim.setBounds(270, 270, 100, 25);
        add(txtDataFim);

        JLabel l5 = new JLabel("Responsável:");
        l5.setBounds(380, 270, 90, 25);
        add(l5);
        txtResponsavel = new JTextField();
        txtResponsavel.setBounds(470, 270, 150, 25);
        add(txtResponsavel);

        JLabel l6 = new JLabel("Equipe:");
        l6.setBounds(630, 270, 60, 25);
        add(l6);
        txtEquipe = new JTextField();
        txtEquipe.setBounds(690, 270, 150, 25);
        add(txtEquipe);

        JLabel l7 = new JLabel("Status:");
        l7.setBounds(10, 310, 80, 25);
        add(l7);
        boxStatus = new JComboBox<>(new String[]{"Pendente", "Em Execução", "Concluída"});
        boxStatus.setBounds(90, 310, 150, 25);
        add(boxStatus);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(300, 310, 100, 30);
        add(btnSalvar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(420, 310, 100, 30);
        add(btnExcluir);

        btnAtualizar = new JButton("Atualizar Tabela");
        btnAtualizar.setBounds(540, 310, 140, 30);
        add(btnAtualizar);

        carregarTabela();

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(Projetos.this, "O nome do projeto é obrigatório!");
                    return;
                }

                int novoId = CadastroProjeto.getProjetos().size() + 1;

                Projeto projeto = new Projeto(
                        novoId,
                        nome,
                        txtDescricao.getText(),
                        txtDataInicio.getText(),
                        txtDataFim.getText(),
                        txtResponsavel.getText(),
                        txtEquipe.getText(),
                        (String) boxStatus.getSelectedItem()
                );

                CadastroProjeto.adicionarProjeto(projeto);

                carregarTabela();

                limparCampos();

                JOptionPane.showMessageDialog(Projetos.this, "Projeto salvo com sucesso!");
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (linhaSelecionada != -1) {
                    int id = (int) modelo.getValueAt(linhaSelecionada, 0);
                    if (CadastroProjeto.removerProjetoPorId(id)) {
                        carregarTabela();
                        JOptionPane.showMessageDialog(Projetos.this, "Projeto excluído com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(Projetos.this, "Erro ao excluir o projeto.");
                    }
                } else {
                    JOptionPane.showMessageDialog(Projetos.this, "Selecione um projeto para excluir.");
                }
            }
        });

        btnAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabela();
                JOptionPane.showMessageDialog(Projetos.this, "Tabela atualizada!");
            }
        });
    }

    private void carregarTabela() {
        modelo.setRowCount(0);
        List<Projeto> projetos = CadastroProjeto.getProjetos();
        for (Projeto p : projetos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getDescricao(),
                    p.getDataInicio(),
                    p.getDataFim(),
                    p.getResponsavel(),
                    p.getEquipe(),
                    p.getStatus()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtDataInicio.setText("");
        txtDataFim.setText("");
        txtResponsavel.setText("");
        txtEquipe.setText("");
        boxStatus.setSelectedIndex(0);
    }
}