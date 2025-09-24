import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnLogin;

    public Login() {
        setTitle("Sistema - Login");
        setSize(360, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel titulo = new JLabel("Bem-vindo ao Sistema", JLabel.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        txtUsuario = new JTextField(14);
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuário"));

        txtSenha = new JPasswordField(14);
        txtSenha.setBorder(BorderFactory.createTitledBorder("Senha"));

        btnLogin = new JButton("Entrar");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setPreferredSize(new Dimension(90, 35));

        painelPrincipal.add(titulo);
        painelPrincipal.add(txtUsuario);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(txtSenha);
        painelPrincipal.add(Box.createVerticalStrut(15));
        painelPrincipal.add(btnLogin);

        add(painelPrincipal);

        btnLogin.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String senha = new String(txtSenha.getPassword());

            if ("admin".equals(usuario) && "123".equals(senha)) {
                dispose();
                new Home();
            } else {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
