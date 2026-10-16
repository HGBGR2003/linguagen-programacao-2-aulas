package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// PADRÃO CONNECTION FACTORY
public class ConnectionFactory {

    private ConnectionFactory() {
        // Impede a instanciacao da classe utilitaria
    }

    public static Connection getConexao() throws SQLException {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstancia();

        try {
            // Garante o carregamento do Driver JDBC na JVM
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(
                    config.getUrlBanco(),
                    config.getUsuario(),
                    config.getSenha());
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do PostgreSQL nao localizado no classpath!", e);
        }
    }
}