import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    public Home() {
        setTitle("Sistema - Home");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("Bem-vindo ao Sistema", JLabel.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();

        JPanel abaInicio = new JPanel();
        abas.addTab("Início", abaInicio);

        JPanel abaUsuario = new JPanel();
        abas.addTab("Usuários", abaUsuario);

        JPanel abaProjeto = new JPanel();
        abas.addTab("Projetos", abaProjeto);

        JPanel abaEquipe = new JPanel();
        abas.addTab("Equipe", abaEquipe);

        JPanel abaTarefas = new JPanel();
        abas.addTab("Tarefas", abaTarefas);

        JPanel abaRelatorios = new JPanel();
        abas.addTab("Relatórios", abaRelatorios);

        JPanel abaSair = new JPanel();
        JButton btnSair = new JButton("Sair do Sistema");
        btnSair.addActionListener(e -> System.exit(0));
        abaSair.add(btnSair);
        abas.addTab("Sair", abaSair);

        painelPrincipal.add(abas, BorderLayout.CENTER);

        add(painelPrincipal);
        setVisible(true);
    }
}
