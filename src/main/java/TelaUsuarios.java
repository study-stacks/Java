import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class TelaUsuarios extends JPanel {
    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtNome, txtCpf, txtEmail, txtCargo, txtLogin;
    private JPasswordField txtSenha;
    private JComboBox<String> boxPerfil;
    private JButton btnNovo, btnSalvar, btnExcluir;
    private UsuarioDAO usuarioDAO;
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public TelaUsuarios() {
        this.usuarioDAO = new UsuarioDAO();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Gerenciamento de Usuários"));

        String[] colunas = {"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"};
        modelo = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane rolagem = new JScrollPane(tabela);
        add(rolagem, BorderLayout.CENTER);

        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro/Edição de Usuários"));
        JPanel painelCampos = new JPanel(new GridLayout(0, 2, 5, 5));
        painelCampos.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painelCampos.add(txtNome);
        painelCampos.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        painelCampos.add(txtCpf);
        painelCampos.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painelCampos.add(txtEmail);
        painelCampos.add(new JLabel("Cargo:"));
        txtCargo = new JTextField();
        painelCampos.add(txtCargo);
        painelCampos.add(new JLabel("Login:"));
        txtLogin = new JTextField();
        painelCampos.add(txtLogin);
        painelCampos.add(new JLabel("Senha:"));
        txtSenha = new JPasswordField();
        painelCampos.add(txtSenha);
        painelCampos.add(new JLabel("Perfil:"));
        boxPerfil = new JComboBox<>(new String[]{"Administrador", "Gerente", "Colaborador", "Estagiário"});
        painelCampos.add(boxPerfil);
        painelCadastro.add(painelCampos, BorderLayout.NORTH);
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnNovo = new JButton("Novo");
        btnSalvar = new JButton("Salvar");
        btnExcluir = new JButton("Excluir");
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);
        add(painelCadastro, BorderLayout.EAST);

        btnSalvar.addActionListener(e -> salvarUsuario());
        btnExcluir.addActionListener(e -> excluirUsuario());
        btnNovo.addActionListener(e -> limparFormulario());

        atualizarTabela();
    }

    private void salvarUsuario() {
        Usuario novoUsuario = new Usuario(
                "0",
                txtNome.getText(),
                txtCpf.getText(),
                txtEmail.getText(),
                txtLogin.getText(),
                txtCargo.getText(),
                boxPerfil.getSelectedItem().toString()
        );
        usuarioDAO.salvar(novoUsuario);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!");
        changeSupport.firePropertyChange("usuariosAtualizados", null, usuarioDAO.listarTodos());
    }

    private void excluirUsuario() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            String idStr = (String) modelo.getValueAt(linha, 0);
            int idUsuario = Integer.parseInt(idStr);
            usuarioDAO.excluir(idUsuario);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
            changeSupport.firePropertyChange("usuariosAtualizados", null, usuarioDAO.listarTodos());
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
        }
    }

    private void limparFormulario() {
        txtNome.setText("");
        txtCpf.setText("");
        txtEmail.setText("");
        txtCargo.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
        boxPerfil.setSelectedIndex(0);
        tabela.clearSelection();
    }

    public void atualizarTabela() {
        modelo.setRowCount(0);
        List<Usuario> listaUsuarios = usuarioDAO.listarTodos();
        for (Usuario u : listaUsuarios) {
            Object[] dados = {
                    u.getId(),
                    u.getNome(),
                    u.getCpf(),
                    u.getEmail(),
                    u.getCargo(),
                    u.getLogin(),
                    u.getPerfil()
            };
            modelo.addRow(dados);
        }
    }

    public List<Usuario> getListaUsuarios() {
        return usuarioDAO.listarTodos();
    }

    @Override
    public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }
}