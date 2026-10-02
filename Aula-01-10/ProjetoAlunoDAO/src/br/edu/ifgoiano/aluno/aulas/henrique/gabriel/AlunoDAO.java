package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Camada de Acesso a Dados (Data Access Object - DAO) para persistência e operações
 * relacionadas às entidades de alunos e seus respectivos históricos de perrengues.
 * <p>
 * Utiliza o banco de dados em memória <strong>H2 Database</strong> através da API JDBC,
 * implementando operações CRUD, instruções DDL, consultas estruturadas, controle transacional 
 * manual com garantia de atomicidade (commit/rollback) e execução de comandos em lote (batch).
 * </p>
 *
 * @author Henrique Gabriel
 * @version 1.0
 * @see Aluno
 * @see HistoricoPerrengues
 * @see StatusMatricula
 */
public class AlunoDAO {

    /**
     * URL de conexão JDBC com o banco H2 em memória.
     * <p>
     * O parâmetro {@code DB_CLOSE_DELAY=-1} garante que o banco mantenha os dados
     * na memória ativa enquanto o processo da JVM estiver em execução.
     * </p>
     */
    private static final String URL_H2 = "jdbc:h2:mem:perrenguedb;DB_CLOSE_DELAY=-1";

    /**
     * Nome de usuário padrão para autenticação no banco H2.
     */
    private static final String USUARIO = "sa";

    /**
     * Senha de acesso ao banco H2 (vazia por padrão no modo em memória).
     */
    private static final String SENHA = "";

    /**
     * Estabelece e retorna uma conexão ativa com o banco de dados H2.
     *
     * @return uma instância ativa de {@link Connection}
     * @throws SQLException se ocorrer um erro durante a tentativa de conexão
     */
    private Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL_H2, USUARIO, SENHA);
    }

    /**
     * Executa a instrução DDL para criar a tabela {@code aluno}, caso ela não exista.
     * <p>
     * Estrutura da tabela:
     * <ul>
     *   <li>{@code matricula}: Chave primária (VARCHAR 20)</li>
     *   <li>{@code nome}: Nome do aluno (VARCHAR 100)</li>
     *   <li>{@code energia}: Nível de energia (DOUBLE)</li>
     *   <li>{@code dinheiro}: Saldo financeiro (DOUBLE)</li>
     *   <li>{@code status}: Nome do enum do status (VARCHAR 20)</li>
     * </ul>
     * </p>
     */
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

    /**
     * Insere um novo registro de aluno no banco de dados.
     *
     * @param aluno objeto {@link Aluno} contendo as informações a serem persistidas
     */
    public void salvar(Aluno aluno) {
        String sql = "INSERT INTO aluno (matricula, nome, energia, dinheiro, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, aluno.getMatricula());
            stmt.setString(2, aluno.getNome());
            stmt.setDouble(3, aluno.getEnergia());
            stmt.setDouble(4, aluno.getDinheiro());
            stmt.setString(5, aluno.getStatus().name());

            stmt.executeUpdate();
            System.out.println("[H2 DAO] Aluno '" + aluno.getNome() + "' inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao inserir aluno: " + e.getMessage());
        }
    }

    /**
     * Consulta e retorna todos os registros de alunos cadastrados na tabela.
     *
     * @return {@link List} contendo os objetos de alunos recuperados do banco
     */
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

                StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));

                Aluno aluno = new AlunoRegular(mat, nome, energia, dinheiro, status, null);
                turma.add(aluno);
            }

        } catch (SQLException e) {
            System.err.println("[H2 DAO] Erro ao listar alunos: " + e.getMessage());
        }

        return turma;
    }

    /**
     * Localiza um aluno com base em sua chave primária (matrícula).
     *
     * @param matricula a matrícula a ser pesquisada
     * @return o objeto {@link Aluno} correspondente, ou {@code null} se não encontrado
     */
    public Aluno buscarPorMatricula(String matricula) {
        String sql = "SELECT * FROM aluno WHERE matricula = ?";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaAluno(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DAO] Erro na busca: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza os dados dinâmicos (energia, dinheiro e status) de um aluno já existente.
     *
     * @param aluno objeto {@link Aluno} com os novos valores a serem atualizados
     */
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

    /**
     * Exclui o registro de um aluno do banco de dados através da matrícula informada.
     *
     * @param matricula matrícula do aluno a ser removido
     */
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

    /**
     * Realiza uma transferência monetária (PIX) atômica entre dois alunos.
     * <p>
     * A transação utiliza controle manual de commit e rollback. Se o saldo de origem
     * for insuficiente ou qualquer das contas não for localizada, toda a operação
     * é desfeita para evitar inconsistências no banco de dados.
     * </p>
     *
     * @param matOrigem  matrícula do aluno pagador
     * @param matDestino matrícula do aluno recebedor
     * @param valor      quantia em dinheiro a ser transferida
     * @return {@code true} se a transação foi confirmada com sucesso; {@code false} em caso de falha/rollback
     */
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

    /**
     * Mapeia a linha atual apontada pelo {@link ResultSet} para um objeto {@link Aluno}.
     *
     * @param rs o cursor do {@link ResultSet} posicionado no registro desejado
     * @return nova instância de {@link Aluno} correspondente aos dados da linha
     * @throws SQLException se houver falha ao ler os dados das colunas
     */
    private Aluno mapearResultSetParaAluno(ResultSet rs) throws SQLException {
        String mat = rs.getString("matricula");
        String nome = rs.getString("nome");
        double energia = rs.getDouble("energia");
        double dinheiro = rs.getDouble("dinheiro");
        StatusMatricula status = StatusMatricula.valueOf(rs.getString("status"));

        return new AlunoRegular(mat, nome, energia, dinheiro, status, null);
    }

    /**
     * Executa a instrução DDL para criar a tabela {@code historico_perrengues} com chave estrangeira
     * apontando para a tabela {@code aluno}.
     */
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

    /**
     * Concede auxílio financeiro para uma lista de matrículas executando comandos em lote (*batch*).
     * <p>
     * A operação é realizada sob transação manual com {@code commit} e {@code rollback} garantindo
     * que todas as atualizações sejam aplicadas em conjunto.
     * </p>
     *
     * @param matriculas   lista de matrículas dos alunos beneficiados
     * @param valorAuxilio valor monetário a ser adicionado ao saldo de cada aluno
     */
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

    /**
     * Registra um novo perrengue/incidente associado à matrícula de um aluno.
     *
     * @param matricula matrícula do aluno envolvido
     * @param descricao detalhamento textual do perrengue
     * @param data      data da ocorrência no formato textual (ex.: "DD/MM/AAAA")
     */
    public void adicionarPerrengue(String matricula, String descricao, String data) {
        String sql = "INSERT INTO historico_perrengues (matricula, descricao, data) VALUES (?, ?, ?)";

        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            stmt.setString(2, descricao);
            stmt.setString(3, data);
            stmt.executeUpdate();
            System.out.println("[H2 DAO] Perrengue registrado para matrícula: " + matricula);
        } catch (SQLException sl) {
            System.err.println("[H2 DAO] Erro ao adicionar perrengue: " + sl.getMessage());
        }
    }

    /**
     * Consulta e retorna o histórico de perrengues associados a um aluno específico,
     * ordenado crescentemente pela data.
     *
     * @param matricula matrícula do aluno a ter os perrengues consultados
     * @return {@link List} contendo os registros de {@link HistoricoPerrengues} encontrados
     */
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