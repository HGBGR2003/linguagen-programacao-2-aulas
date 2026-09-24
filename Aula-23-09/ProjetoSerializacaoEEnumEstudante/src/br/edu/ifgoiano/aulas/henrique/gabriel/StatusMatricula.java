package br.edu.ifgoiano.aulas.henrique.gabriel;

enum StatusMatricula {
    ATIVO("Matrícula Regular", 1.0),
    TRANCADO("Matrícula Trancada", 0.0),
    JUBILADO("Jubilado por Exaustão", 0.0);

    private final String descricao;
    private final double fatorGastoEnergia;

    StatusMatricula(String descricao, double fatorGastoEnergia) {
        this.descricao = descricao;
        this.fatorGastoEnergia = fatorGastoEnergia;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getFatorGastoEnergia() {
        return fatorGastoEnergia;
    }
}