package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {
    // Configuração do Banco H2 em Memória
    // 'DB_CLOSE_DELAY=-1' mantém o banco em memória vivo enquanto a JVM estiver
    // rodando
    private static final String URL_H2 = "jdbc:h2:mem:perrenguedb;DB_CLOSE_DELAY=-1";
    private static final String USUARIO = "sa";
    private static final String SENHA = "";

    private Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL_H2, USUARIO, SENHA);
    }

    // 1. DDL: Criação da Tabela no H2
    public void inicializarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS aluno (" +
                "matricula VARCHAR(20) PRIMARY KEY, " +
                "nome VARCHAR(100) NOT NULL, " +
                "energia DOUBLE NOT NULL, " +
                "dinheiro DOUBLE NOT NULL, " +
                "status VARCHAR(20) NOT NULL)";

        try (Connection conn = obterConexao();
                Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("[H2 DAO] Tabela 'aluno' criada/verificada com sucesso!");

        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao criar tabela: " + e.getMessage());
        }
    }

    // 2. DML: Inserir Registro com PreparedStatement
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, energia, dinheiro, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setDouble(3, aluno.getEnergia());
            stmt.setDouble(4, aluno.getDinheiro());
            stmt.setString(5, aluno.getStatus().name()); // Salva o nome do Enum (ex: "ATIVO")

            stmt.executeUpdate();
            System.out.println("[H2 DAO] Aluno '" + aluno.getNome() + "' inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao inserir aluno: " + e.getMessage());
        }
    }

    // 3. DQL: Consultar Registros e Mapear ResultSet -> Objetos Heap
    public List<Aluno> listarTodos() {
        List<Aluno> turma = new ArrayList<>();
        String sql = "SELECT * FROM aluno";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String mat = rs.getString("matricula");
                String nome = rs.getString("nome");
                double energia = rs.getDouble("energia");
                double dinheiro = rs.getDouble("dinheiro");

                // Converte a String do banco de volta para o Enum
                StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));

                // Reconstrói o objeto na memória Heap
                Aluno aluno = new AlunoRegular(mat, nome, energia, dinheiro, status, null);
                turma.add(aluno);
            }

        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao listar alunos: " + e.getMessage());
        }

        return turma;
    }

    // 4. READ (Buscar por Chave Primária)
    public Aluno buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaAluno(rs); // Aqui poderia criar o aluno, mas defini um método para
                                                         // simplificar
                }
            }
        } catch (SQLException e) {
            System.err.println("[DAO] Erro na busca: " + e.getMessage());
        }
        return null;
    }

    // 5. UPDATE (Atualizar Registro)
    public void atualizar(Aluno aluno) {
        String sql = "UPDATE aluno SET energia = ?, dinheiro = ?, status = ? WHERE matricula = ?";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, aluno.getEnergia());
            stmt.setDouble(2, aluno.getDinheiro());
            stmt.setString(3, aluno.getStatus().name());
            stmt.setString(4, aluno.getMatricula());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("[DAO] Atualizado: " + aluno.getNome());
            }
        } catch (SQLException e) {
            System.err.println("[DAO] Erro ao atualizar: " + e.getMessage());
        }
    }

    // 6. DELETE (Remover Registro)
    public void deletar(String matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = ?";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("[DAO] Registro removido: " + matricula);
            }
        } catch (SQLException e) {
            System.err.println("[DAO] Erro ao deletar: " + e.getMessage());
        }
    }

    // 7. TRANSAÇÃO MANUAL: PIX entre Alunos (Atomicidade com Commit/Rollback)
    public boolean transferirDinheiro(String matOrigem, String matDestino, double valor) {
        String sqlDebito = "UPDATE aluno SET dinheiro = dinheiro - ? WHERE matricula = ? AND dinheiro >= ?";
        String sqlCredito = "UPDATE aluno SET dinheiro = dinheiro + ? WHERE matricula = ?";

        Connection conn = null;
        try {
            conn = obterConexao();
            // Desativa o auto-commit para controlar a transação manualmente
            conn.setAutoCommit(false);

            // Passo 1: Debitar da origem
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlDebito)) {
                stmt1.setDouble(1, valor);
                stmt1.setString(2, matOrigem);
                stmt1.setDouble(3, valor);
                int afetados = stmt1.executeUpdate();

                if (afetados == 0) {
                    throw new SQLException("Saldo insuficiente ou conta de origem não encontrada.");
                }
            }

            // Passo 2: Creditar no destino
            try (PreparedStatement stmt2 = conn.prepareStatement(sqlCredito)) {
                stmt2.setDouble(1, valor);
                stmt2.setString(2, matDestino);
                int afetados = stmt2.executeUpdate();

                if (afetados == 0) {
                    throw new SQLException("Conta de destino não encontrada.");
                }
            }

            // Se ambos os passos funcionaram, confirma a transação no banco
            conn.commit();
            System.out.println("[TRANSAÇÃO OK] PIX de R$ " + valor + " realizado com sucesso!");
            return true;

        } catch (SQLException e) {
            System.err.println("[ROLLBACK] Falha na transação (" + e.getMessage() + "). Desfazendo alterações...");
            if (conn != null) {
                try {
                    conn.rollback(); // Restaura o banco ao estado anterior
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restaura o comportamento padrão
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método auxiliar de mapeamento
    private Aluno mapearResultSetParaAluno(ResultSet rs) throws SQLException {
        String mat = rs.getString("matricula");
        String nome = rs.getString("nome");
        double energia = rs.getDouble("energia");
        double dinheiro = rs.getDouble("dinheiro");
        StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));

        return new AlunoRegular(mat, nome, energia, dinheiro, status, null);
    }

    // DDL: Criação da Tabela historico_perrengues
    public void criacaoTabelaHistorico() {
        String sql = "CREATE TABLE IF NOT EXISTS historico_perrengues (" +
                "id          INT AUTO_INCREMENT PRIMARY KEY, " +
                "matricula   VARCHAR(20)  NOT NULL, " +
                "descricao   VARCHAR(255) NOT NULL, " +
                "data        VARCHAR(10)  NOT NULL, " +
                "FOREIGN KEY (matricula) REFERENCES aluno(matricula))";

        try (Connection conn = obterConexao();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("[H2 DAO] Tabela 'historico_perrengues' criada/verificada com sucesso!");
        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao criar tabela histórico: " + e.getMessage());
        }
    }

    public void concederAuxilioEmLote(List<String> matriculas, double valorAuxilio) {
        String sql = "UPDATE aluno SET dinheiro = dinheiro + ? WHERE matricula = ?";

        Connection conn = null;

        try {
            conn = obterConexao();
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (String matricula : matriculas) {
                    stmt.setDouble(1, valorAuxilio);
                    stmt.setString(2, matricula);
                    stmt.addBatch();
                }

                int[] resultados = stmt.executeBatch();
                int naoEncontrados = 0;

                for (int r : resultados) {
                    if (r == 0)
                        naoEncontrados++;
                }

                conn.commit();
                System.out.println("[LOTE OK] Auxílio de R$ " + valorAuxilio +
                        " concedido para " + (matriculas.size() - naoEncontrados) + " aluno(s)." +
                        (naoEncontrados > 0 ? " (" + naoEncontrados + " matrícula(s) não encontrada(s))" : ""));
            }

        } catch (SQLException e) {
            System.err.println("[ROLLBACK] Falha no lote (" + e.getMessage() + "). Desfazendo alterações...");
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException sl) {
                    sl.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException sl) {
                    sl.printStackTrace();
                }
            }
        }

    }

    public void adicionarPerrengue(String matricula, String descricao, String data) {
        String sql = "INSERT INTO historico_perrengues (matricula, descricao, data) VALUES (?, ?, ?)";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.setString(2, descricao);
            stmt.setString(3, data);
            System.out.println("[H2 DAO] Perrengue registrado para matrícula: " + matricula);
        } catch (SQLException sl) {
            System.err.println("[H2 DAO] Erro ao adicionar perrengue: " + sl.getMessage());
        }
    }

    public List<HistoricoPerrengues> listarPerrengues(String matricula) {
        List<HistoricoPerrengues> historico = new ArrayList<>();
        String sql = "SELECT * FROM historico_perrengues WHERE matricula = ? ORDER BY data ASC";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    HistoricoPerrengues h = new HistoricoPerrengues(
                            rs.getInt("id"),
                            rs.getString("matricula"),
                            rs.getString("descricao"),
                            rs.getString("data"));
                    historico.add(h);
                }
            }
        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao listar histórico: " + e.getMessage());
        }
        return historico;
    }
}
