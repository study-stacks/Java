import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JTextField campoUsuario;
    private JPasswordField campoSenha;
    private JButton btnEntrar;

    public Login() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Usuário:"));
        campoUsuario = new JTextField();
        add(campoUsuario);

        add(new JLabel("Senha:"));
        campoSenha = new JPasswordField();
        add(campoSenha);

        btnEntrar = new JButton("Entrar");
        add(btnEntrar);

        btnEntrar.addActionListener(e -> {
            String usuario = campoUsuario.getText();
            String senha = String.valueOf(campoSenha.getPassword());

            Connection con = Conexao.conectar();
            if (con != null) {
                try {
                    String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";
                    PreparedStatement ps = con.prepareStatement(sql);
                    ps.setString(1, usuario);
                    ps.setString(2, senha);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(null,
                                "Login realizado! Perfil: " + rs.getString("perfil"));
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
                    }

                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private boolean verificarLogin(String usuario, String senha) {
        if (usuario.equals("admin") && senha.equals("admin123")) {
            JOptionPane.showMessageDialog(this, "Bem-vindo Administrador!");
            return true;
        } else if (usuario.equals("gerente") && senha.equals("gerente123")) {
            JOptionPane.showMessageDialog(this, "Bem-vindo Gerente!");
            return true;
        } else if (usuario.equals("colab") && senha.equals("colab123")) {
            JOptionPane.showMessageDialog(this, "Bem-vindo Colaborador!");
            return true;
        } else if (usuario.equals("estagiario") && senha.equals("estagiario123")) {
            JOptionPane.showMessageDialog(this, "Bem-vindo Estagiário!");
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos!");
            return false;
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}

