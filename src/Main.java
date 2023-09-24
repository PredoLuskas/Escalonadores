import escalonadores.Escalonador;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {

        Escalonador escalonador = new Escalonador();
        escalonador.FCFS();
        escalonador.SJF();
        escalonador.roundRobin();
    }
    }