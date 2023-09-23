import escalonadores.Escalonador;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        String path = "\"C:/Projetos/Java/SistemaOperacional/Escalonadores/src/resource/file.txt\"";

          //  IOFiles.writterFile(path);
        Escalonador escalonador = new Escalonador();
        escalonador.FCFS();
       // escalonador.SJF();
        escalonador.roundRobin();
    }
    }