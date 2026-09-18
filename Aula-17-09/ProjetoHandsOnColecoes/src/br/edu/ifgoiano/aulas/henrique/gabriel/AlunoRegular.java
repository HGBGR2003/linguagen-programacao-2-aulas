package br.edu.ifgoiano.aulas.henrique.gabriel;

class AlunoRegular extends Aluno {
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro) {
        super(matricula, nome, energia, dinheiro);
    }

    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 20.0;
        System.out.println(getNome() + " estudou para LP2. Energia restante: " + this.energia + "%");
    }
}