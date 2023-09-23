import escalonadores.Escalonador;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        String path = "\"C:/Projetos/Java/SistemaOperacional/Escalonadores/src/resource/file.txt\"";

        Escalonador escalonador = new Escalonador();
        escalonador.FCFS();
        escalonador.SJF();
        escalonador.roundRobin();
    }
    }