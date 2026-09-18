import java.util.Objects;

public abstract class Aluno {
    private String matricula;
    private String nome;
    protected double energia;
    protected double dinheiro;

    public Aluno(String matricula, String nome, double energia, double dinheiro) {
        this.matricula = matricula;
        this.nome = nome;
        this.energia = energia;
        this.dinheiro = dinheiro;
    }

    public String getMatricula() { return matricula; }
    public String getNome() { return nome; }
    public double getEnergia() { return energia; }
    public double getDinheiro() { return dinheiro; }

    public abstract void realizarAtividadeDiaria();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(matricula, aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matricula);
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")\n - Energia: " + energia + "%\n - R$ " + dinheiro + "\n";
    }
}