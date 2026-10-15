package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

// IMPLEMENTAÇÃO ROBUSTA DO PADRÃO SINGLETON
public class ConfiguracaoSistema {
    
    private static ConfiguracaoSistema instancia;
    private final String urlBanco;
    private final String usuario;
    private final String senha;

    // 1. Construtor estritamente privado
    private ConfiguracaoSistema() {
        this.urlBanco = System.getenv().getOrDefault(
            "DB_URL", "jdbc:postgresql://127.0.0.1:5432/perrenguedb");
        this.usuario = System.getenv().getOrDefault("DB_USER", "postgres");
        this.senha = System.getenv().getOrDefault("DB_PASSWORD", "alunoguida22");
    }

    // 2. Metodo acessor thread-safe com inicializacao tardia (Lazy)
    public static synchronized ConfiguracaoSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }

    public String getUrlBanco() { return urlBanco; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
}