package br.edu.ifgoiano.aulas.henrique.gabriel;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.EnumSet;
import java.util.EnumMap;

public class Main {

    public static List<Aluno> carregarTurma() {
        List<Aluno> turma = new ArrayList<>();
        turma.add(new AlunoRegular("20261EC001", "Lucas Santos", 50.0, 15.0, StatusMatricula.ATIVO, "TOK-001"));
        turma.add(new AlunoRegular("20261EC002", "Ana Vilela", 80.0, 120.0, StatusMatricula.ATIVO, "TOK-002"));
        turma.add(new AlunoRegular("20261EC003", "Bruno Carlos", 60.0, 10.0, StatusMatricula.ATIVO, "TOK-003"));
        turma.add(new AlunoRegular("20261EC004", "Carla Ferreira", 90.0, 200.0, StatusMatricula.ATIVO, "TOK-004"));
        turma.add(new AlunoRegular("20261EC005", "Dario Jubilado", 0.0, 10.0, StatusMatricula.JUBILADO, "TOK-005"));
        turma.add(new AlunoRegular("20261EC006", "Lucas Silva", 0.0, 12.50, StatusMatricula.JUBILADO, "TOK-006"));
        turma.add(new AlunoRegular("20261EC007", "Ana Souza", 65.0, 8.00, StatusMatricula.ATIVO, "TOK-007"));
        turma.add(new AlunoRegular("20261EC008", "Bruno Lima", 45.0, 22.00, StatusMatricula.TRANCADO, "TOK-008"));
        turma.add(new AlunoRegular("20261EC009", "Carla Dias", 15.0, 85.00, StatusMatricula.ATIVO, "TOK-009"));
        turma.add(new AlunoRegular("20261EC010", "Diego Sato", 85.0, 180.0, StatusMatricula.ATIVO, "TOK-010"));
        turma.add(new AlunoRegular("20261EC011", "Fernanda Cruz", 90.0, 250.0, StatusMatricula.ATIVO, "TOK-011"));
        turma.add(new AlunoRegular("20261EC012", "Gabriel Mendes", 25.0, 310.0, StatusMatricula.TRANCADO, "TOK-012"));
        turma.add(new AlunoRegular("20261EC013", "Helena Rocha", 70.0, 45.0, StatusMatricula.ATIVO, "TOK-013"));
        return turma;
    }

    public static void main(String[] args) {
        List<Aluno> turma = carregarTurma();
        System.out.println("Turma carregada com " + turma.size() + " alunos.");

        System.out.println("=== 1. DEMONSTRAÇÃO DO ENUMSET ===");
        // Conjunto contendo apenas os status de inatividade
        EnumSet<StatusMatricula> inativos = EnumSet.of(StatusMatricula.TRANCADO, StatusMatricula.JUBILADO);

        for (Aluno e : turma) {
            if (inativos.contains(e.getStatus())) {
                System.out.println("Aluno inativo detectado via EnumSet: " + e.getNome() + " -> " + e.getStatus());
            }
        }

        System.out.println("\n=== 2. DEMONSTRAÇÃO DO ENUMMAP ===");
        // Mapa indexado eficientemente pelas chaves do StatusMatricula
        EnumMap<StatusMatricula, List<Aluno>> mapaPorStatus = new EnumMap<>(StatusMatricula.class);

        for (StatusMatricula s : StatusMatricula.values()) {
            mapaPorStatus.put(s, new ArrayList<>());
        }

        for (Aluno e : turma) {
            mapaPorStatus.get(e.getStatus()).add(e);
        }

        mapaPorStatus.forEach((status, lista) -> {
            System.out.println("Categoria EnumMap [" + status.getDescricao() + "]: " + lista.size() + " aluno(s)");
        });

        System.out.println("\n=== 3. SERIALIZANDO O ENUMMAP COMPLETO EM DISCO ===");
        String arquivoMapa = "mapa_turma.dat";

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivoMapa))) {
            oos.writeObject(mapaPorStatus);
            System.out.println("EnumMap serializado com sucesso em " + arquivoMapa);
        } catch (IOException e) {
            System.out.println("Erro ao serializar EnumMap: " + e.getMessage());
        }

        System.out.println("\n=== 4. DESSERIALIZANDO E LENDO DO DISCO ===");
        EnumMap<StatusMatricula, List<Aluno>> mapaRestaurado = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoMapa))) {
            @SuppressWarnings("unchecked")
            EnumMap<StatusMatricula, List<Aluno>> temp =
                    (EnumMap<StatusMatricula, List<Aluno>>) ois.readObject();
            mapaRestaurado = temp;

            System.out.println("Dados restaurados do arquivo " + arquivoMapa + ":");
            mapaRestaurado.forEach((status, lista) -> {
                System.out.println("Group " + status + ": " + lista.size() + " aluno(s).");
                lista.forEach(a -> System.out.println("   " + a.getNome() + " | token: " + a.getTokenMoodle()));
            });
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao desserializar: " + e.getMessage());
        }

        // === 5. GERAR RELATÓRIO LEGÍVEL EM TEXTO ===
        // O arquivo .dat é binário (normal para Java Serialization).
        // Este bloco gera um .txt legível com os mesmos dados, para leitura humana.
        System.out.println("\n=== 5. GERANDO RELATÓRIO LEGÍVEL EM TEXTO ===");
        String arquivoRelatorio = "relatorio_turma.txt";
        if (mapaRestaurado != null) {
            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(
                    new java.io.FileWriter(arquivoRelatorio))) {
                bw.write("======================================");
                bw.newLine();
                bw.write("  RELATÓRIO DA TURMA - MAPA POR STATUS");
                bw.newLine();
                bw.write("======================================");
                bw.newLine();
                for (StatusMatricula status : mapaRestaurado.keySet()) {
                    List<Aluno> lista = mapaRestaurado.get(status);
                    bw.newLine();
                    bw.write("[" + status + "] - " + lista.size() + " aluno(s)");
                    bw.newLine();
                    bw.write("--------------------------------------");
                    bw.newLine();
                    for (Aluno a : lista) {
                        bw.write("  Matricula : " + a.getMatricula());
                        bw.newLine();
                        bw.write("  Nome      : " + a.getNome());
                        bw.newLine();
                        bw.write("  Token     : " + a.getTokenMoodle());
                        bw.newLine();
                        bw.write("  Energia   : " + a.getEnergia() + "%");
                        bw.newLine();
                        bw.write("  Dinheiro  : R$ " + a.getDinheiro());
                        bw.newLine();
                        bw.write("  ..............................");
                        bw.newLine();
                    }
                }
                System.out.println("Relatório legível gerado com sucesso em: " + arquivoRelatorio);
            } catch (IOException e) {
                System.out.println("Erro ao gerar relatório: " + e.getMessage());
            }
        }
    }
}