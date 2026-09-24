package br.edu.ifgoiano.aulas.henrique.gabriel;

class AlunoRegular extends Aluno {
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro, StatusMatricula status, String token) {
        super(matricula, nome, energia, dinheiro, status, token);
    }

    @Override // 1. Valida se o método realmente existe na superclasse
    public void realizarAtividadeDiaria() {
        if (getStatus() == StatusMatricula.ATIVO) {
            this.energia -= (20.0 * getStatus().getFatorGastoEnergia());
            System.out.println(getNome() + " estudou. Energia restante: " + this.energia + "%");
        } else {
            System.out.println(getNome() + " impedido de estudar. Status: " + getStatus());
        }
    }

    @Deprecated // 2. Marca este método como obsoleto
    public void aplicarDescontoEnergiaManual(double valor) {
        this.energia -= valor;
    }

    @SuppressWarnings("deprecation") // 3. Silencia o aviso ao chamar o método antigo
    public void executarRotinaAntiga() {
        aplicarDescontoEnergiaManual(10.0);
    }
}