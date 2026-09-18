package br.edu.ifgoiano.aulas.henrique.gabriel;

public class AlunoRegular extends  Estudante {
    public AlunoRegular(String matricula, String nome, double energia, double dinheiro) {
        super(matricula, nome, energia, dinheiro);
    }

    @Override
    public void realizarAtividadeDiaria() {
        this.energia -= 20.0; // Estudar consome energia
        System.out.println(getNome() + " estudou para LP2. Energia atual: " + this.energia + "%");
    }
}
