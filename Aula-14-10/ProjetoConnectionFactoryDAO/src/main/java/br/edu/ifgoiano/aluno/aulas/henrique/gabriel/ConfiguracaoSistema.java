package br.edu.ifgoiano.aluno.aulas.henrique.gabriel;

// IMPLEMENTAÇÃO ROBUSTA DO PADRÃO SINGLETON
public class ConfiguracaoSistema {
    
    private static ConfiguracaoSistema instancia;
    private final String urlBanco;
    private final String usuario;
    private final String senha;

    // 1. Construtor estritamente privado
    private ConfiguracaoSistema() {
        this.urlBanco = "jdbc:h2:mem:perrenguedb;DB_CLOSE_DELAY=-1";
        this.usuario = "sa";
        this.senha = "";
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