import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public void salvar(Projeto projeto) {
        String sql = "INSERT INTO projetos (nome, descricao, data_inicio, data_fim, id_responsavel, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataInicio = LocalDate.parse(projeto.getDataInicio(), formatter);
            LocalDate dataFim = LocalDate.parse(projeto.getDataFim(), formatter);

            pstmt.setString(1, projeto.getNome());
            pstmt.setString(2, projeto.getDescricao());
            pstmt.setDate(3, java.sql.Date.valueOf(dataInicio));
            pstmt.setDate(4, java.sql.Date.valueOf(dataFim));
            pstmt.setInt(5, Integer.parseInt(projeto.getResponsavel().getId()));
            pstmt.setString(6, projeto.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Projeto> listarTodos() {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT p.*, u.id as user_id, u.nome as user_nome, u.cpf as user_cpf, u.email as user_email, u.login as user_login, u.cargo as user_cargo, u.perfil as user_perfil " +
                "FROM projetos p " +
                "LEFT JOIN usuarios u ON p.id_responsavel = u.id";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario responsavel = new Usuario(
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("user_nome"),
                        rs.getString("user_cpf"),
                        rs.getString("user_email"),
                        rs.getString("user_login"),
                        rs.getString("user_cargo"),
                        rs.getString("user_perfil")
                );

                Projeto projeto = new Projeto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDate("data_inicio").toString(),
                        rs.getDate("data_fim").toString(),
                        responsavel,
                        rs.getString("status")
                );
                projetos.add(projeto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projetos;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM projetos WHERE id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}