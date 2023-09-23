package FilesManipulator;

import model.Process;

import java.io.*;
import java.util.*;

public class IOFiles {

    public static List<Process> readerFile(String path) throws IOException {
        try {
            List<Process> processList = new LinkedList<>();

            File arquivo = new File(path); // Substitua "seuarquivo.txt" pelo nome do seu arquivo.

            Scanner scanner = new Scanner(arquivo);
            int i = 1;

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(" "); // Divide a linha em partes usando o espaço como separador.
                if (partes.length == 2) {
                    int tempoChegada = Integer.parseInt(partes[0]);
                    int tempoExecucao = Integer.parseInt(partes[1]);

                    Process processo = new Process( tempoChegada, tempoExecucao);
                    processList.add(processo);
                } else {
                    System.out.println("Formato inválido na linha: " + linha);
                }
            }
            scanner.close();
            return processList;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado.");
        }
        return null;
    }

    public static void writterFile(String path) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
        String linha = "";
        Scanner in = new Scanner(System.in);
        System.out.println("Escreva algo: ");
        linha = in.nextLine();
        buffWrite.append(linha + "\n");
        buffWrite.close();
    }

}