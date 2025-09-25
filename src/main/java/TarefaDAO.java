import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TarefaDAO {

    public void salvar(Tarefas tarefa) {
        String sql = "INSERT INTO tarefas (titulo, descricao, id_projeto, id_responsavel, status, data_inicio_prevista, data_fim_prevista, data_inicio_real, data_fim_real) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, tarefa.getTitulo());
            pstmt.setString(2, tarefa.getDescricao());
            pstmt.setInt(3, tarefa.getProjetoVinculado().getId());
            pstmt.setInt(4, Integer.parseInt(tarefa.getResponsavel().getId()));
            pstmt.setString(5, tarefa.getStatus());
            pstmt.setDate(6, Date.valueOf(tarefa.getDataInicioPrevista()));
            pstmt.setDate(7, Date.valueOf(tarefa.getDataFimPrevista()));
            pstmt.setDate(8, tarefa.getDataInicioReal() != null ? Date.valueOf(tarefa.getDataInicioReal()) : null);
            pstmt.setDate(9, tarefa.getDataFimReal() != null ? Date.valueOf(tarefa.getDataFimReal()) : null);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tarefas> listarTodas() {
        List<Tarefas> tarefas = new ArrayList<>();
        String sql = "SELECT " +
                "t.id as tarefa_id, t.titulo, t.descricao as tarefa_descricao, t.status as tarefa_status, " +
                "t.data_inicio_prevista, t.data_fim_prevista, t.data_inicio_real, t.data_fim_real, " +
                "p.id as projeto_id, p.nome as projeto_nome, p.descricao as projeto_descricao, p.data_inicio, p.data_fim, p.status as projeto_status, " +
                "u.id as user_id, u.nome as user_nome, u.cpf, u.email, u.login, u.cargo, u.perfil " +
                "FROM tarefas t " +
                "JOIN projetos p ON t.id_projeto = p.id " +
                "JOIN usuarios u ON t.id_responsavel = u.id";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario responsavel = new Usuario(
                        String.valueOf(rs.getInt("user_id")), rs.getString("user_nome"), rs.getString("cpf"),
                        rs.getString("email"), rs.getString("login"), rs.getString("cargo"), rs.getString("perfil")
                );

                Projeto projeto = new Projeto(
                        rs.getInt("projeto_id"), rs.getString("projeto_nome"), rs.getString("projeto_descricao"),
                        rs.getDate("data_inicio").toString(), rs.getDate("data_fim").toString(),
                        responsavel,
                        rs.getString("projeto_status")
                );

                Tarefas tarefa = new Tarefas(
                        rs.getInt("tarefa_id"),
                        rs.getString("titulo"), rs.getString("tarefa_descricao"), projeto, responsavel,
                        rs.getString("tarefa_status"),
                        rs.getDate("data_inicio_prevista").toLocalDate(),
                        rs.getDate("data_fim_prevista").toLocalDate()
                );

                if(rs.getDate("data_inicio_real") != null) tarefa.setDataInicioReal(rs.getDate("data_inicio_real").toLocalDate());
                if(rs.getDate("data_fim_real") != null) tarefa.setDataFimReal(rs.getDate("data_fim_real").toLocalDate());

                tarefas.add(tarefa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tarefas;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = Conexao.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}