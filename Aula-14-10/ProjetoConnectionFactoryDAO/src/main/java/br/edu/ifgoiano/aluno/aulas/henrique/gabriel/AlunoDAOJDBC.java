package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// IMPLEMENTAÇÃO CONCRETA DO DAO UTILIZANDO JDBC E POSTGRESQL
public class AlunoDAOJDBC implements AlunoDAO {

    @Override
    public void inicializarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS aluno (" +
                "matricula VARCHAR(20) PRIMARY KEY, " +
                "nome VARCHAR(100) NOT NULL, " +
                "energia REAL NOT NULL, " +
                "dinheiro REAL NOT NULL, " +
                "status VARCHAR(20) NOT NULL)";

        try (Connection conn = ConnectionFactory.getConexao();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[DAO] Tabela 'aluno' pronta no PostgreSQL.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inicializar tabela 'aluno'", e);
        }
    }

    @Override
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, energia, dinheiro, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setDouble(3, aluno.getEnergia());
            stmt.setDouble(4, aluno.getDinheiro());
            stmt.setString(5, aluno.getStatus().name());

            stmt.executeUpdate();
            System.out.println("[DAO] Aluno '" + aluno.getNome() + "' salvo com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno no PostgreSQL", e);
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> turma = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conn = ConnectionFactory.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String mat = rs.getString("matricula");
                String nome = rs.getString("nome");
                double energia = rs.getDouble("energia");
                double dinheiro = rs.getDouble("dinheiro");
                StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));

                turma.add(new AlunoRegular(mat, nome, energia, dinheiro, status));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }
        return turma;
    }

    @Override
    public Aluno buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String mat = rs.getString("matricula");
                    String nome = rs.getString("nome");
                    double energia = rs.getDouble("energia");
                    double dinheiro = rs.getDouble("dinheiro");
                    StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));
                    return new AlunoRegular(mat, nome, energia, dinheiro, status);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por matrícula", e);
        }
        return null;
    }

    @Override
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET energia = ?, dinheiro = ?, status = ? WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, aluno.getEnergia());
            stmt.setDouble(2, aluno.getDinheiro());
            stmt.setString(3, aluno.getStatus().name());
            stmt.setString(4, aluno.getMatricula());

            stmt.executeUpdate();
            System.out.println("[DAO] Aluno '" + aluno.getNome() + "' atualizado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno", e);
        }
    }

    @Override
    public void deletar(String matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.executeUpdate();
            System.out.println("[DAO] Aluno com matrícula '" + matricula + "' removido.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno", e);
        }
    }
}