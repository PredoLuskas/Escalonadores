package FilesManipulator;

import model.Process;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class IOFiles {

    public static List<Process> readerFile(String path) {
        try {
            List<Process> processList = new LinkedList<>();

            File arquivo = new File(path); // Substitua "seuarquivo.txt" pelo nome do seu arquivo.

            Scanner scanner = new Scanner(arquivo);

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(" "); // Divide a linha em partes usando o espaço como separador.
                if (partes.length == 2) {
                    int tempoChegada = Integer.parseInt(partes[0]);
                    int tempoExecucao = Integer.parseInt(partes[1]);

                    Process processo = new Process(tempoChegada, tempoExecucao);
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

}