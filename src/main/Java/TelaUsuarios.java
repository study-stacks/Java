import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaUsuarios extends JPanel {

    private JTable tabela;
    private DefaultTableModel modelo;
    private JTextField txtNome, txtCpf, txtEmail, txtCargo, txtLogin;
    private JPasswordField txtSenha;
    private JComboBox<String> boxPerfil;
    private JButton btnNovo, btnSalvar, btnExcluir;

    private List<Home.Usuario> listaUsuarios = new ArrayList<>();

    public TelaUsuarios() {
        setLayout(null);

        String[] colunas = {"ID", "Nome", "CPF", "Email", "Cargo", "Login", "Perfil"};
        modelo = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modelo);
        JScrollPane rolagem = new JScrollPane(tabela);
        rolagem.setBounds(10, 10, 500, 360);
        add(rolagem);

        JLabel l1 = new JLabel("Nome:");
        l1.setBounds(520, 10, 60, 25);
        add(l1);
        txtNome = new JTextField();
        txtNome.setBounds(580, 10, 180, 25);
        add(txtNome);

        JLabel l2 = new JLabel("CPF:");
        l2.setBounds(520, 45, 60, 25);
        add(l2);
        txtCpf = new JTextField();
        txtCpf.setBounds(580, 45, 180, 25);
        add(txtCpf);

        JLabel l3 = new JLabel("Email:");
        l3.setBounds(520, 80, 60, 25);
        add(l3);
        txtEmail = new JTextField();
        txtEmail.setBounds(580, 80, 180, 25);
        add(txtEmail);

        JLabel l4 = new JLabel("Cargo:");
        l4.setBounds(520, 115, 60, 25);
        add(l4);
        txtCargo = new JTextField();
        txtCargo.setBounds(580, 115, 180, 25);
        add(txtCargo);

        JLabel l5 = new JLabel("Login:");
        l5.setBounds(520, 150, 60, 25);
        add(l5);
        txtLogin = new JTextField();
        txtLogin.setBounds(580, 150, 180, 25);
        add(txtLogin);

        JLabel l6 = new JLabel("Senha:");
        l6.setBounds(520, 185, 60, 25);
        add(l6);
        txtSenha = new JPasswordField();
        txtSenha.setBounds(580, 185, 180, 25);
        add(txtSenha);

        JLabel l7 = new JLabel("Perfil:");
        l7.setBounds(520, 220, 60, 25);
        add(l7);
        boxPerfil = new JComboBox<>(new String[]{"Administrador", "Gerente", "Colaborador", "Estagiário"});
        boxPerfil.setBounds(580, 220, 180, 25);
        add(boxPerfil);

        btnNovo = new JButton("Novo");
        btnNovo.setBounds(520, 260, 80, 25);
        add(btnNovo);

        btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(610, 260, 80, 25);
        add(btnSalvar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(700, 260, 80, 25);
        add(btnExcluir);

        btnSalvar.addActionListener(e -> salvarUsuario());
        btnExcluir.addActionListener(e -> excluirUsuario());
        btnNovo.addActionListener(e -> limparFormulario());
    }

    public List<Home.Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Home.Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
        atualizarTabela();
    }

    private void salvarUsuario() {
        String id = String.valueOf(listaUsuarios.size() + 1);
        Home.Usuario novoUsuario = new Home.Usuario(
                id,
                txtNome.getText(),
                txtCpf.getText(),
                txtEmail.getText(),
                txtLogin.getText(),
                txtCargo.getText(),
                boxPerfil.getSelectedItem().toString()
        );
        listaUsuarios.add(novoUsuario);
        atualizarTabela();
        limparFormulario();
        JOptionPane.showMessageDialog(this, "Usuário salvo com sucesso!");
    }

    private void excluirUsuario() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            listaUsuarios.remove(linha);
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso!");
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
    }

    public void atualizarTabela() {
        modelo.setRowCount(0);
        for (Home.Usuario u : listaUsuarios) {
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
}