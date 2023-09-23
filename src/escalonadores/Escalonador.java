package escalonadores;

import FilesManipulator.IOFiles;
import model.Process;

import java.io.IOException;
import java.util.*;

public class Escalonador {

    String path = "C:/Projetos/Java/SistemaOperacional/Escalonadores/src/resource/file.txt";

    public void FCFS() throws IOException {
        List<Process> processos = new ArrayList<>();

        processos = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        assert processos != null;
        processos.sort(Comparator.comparingInt(Process::getTempoDeChegada)); //Ordenando por tempo de chegada os processos que chegaram

        int tempoAtual = 0;
        int tempoInicio = 0;

        // Calcular métricas
        double tempoEspera = 0;
        double tempoRetorno = 0;
        double tempoResposta = 0;

        double tempoEsperaTotal = 0;
        double tempoRetornoTotal = 0;
        double tempoRespostaTotal = 0;


        for (Process processo : processos) {

            System.out.println(processo.getTempoDeChegada() + " " + processo.getTempoDeExecucao());
            if (tempoAtual < processo.getTempoDeChegada()) {
                // Se o processo ainda não chegou, espere
                tempoAtual = processo.getTempoDeChegada();

            }
            int tempoDeEntrada = Math.max(tempoAtual, processo.getTempoDeChegada());
            tempoResposta = tempoDeEntrada - processo.getTempoDeChegada();
            tempoRespostaTotal += tempoResposta; // Tempo Resposta
            tempoEsperaTotal = tempoRespostaTotal; //Tempo de espera - No FCFS o tempo de  resposta é igual ao tempo de espera

            tempoAtual = tempoDeEntrada + processo.getTempoDeExecucao();
            tempoRetorno = (tempoAtual - processo.getTempoDeChegada());
            tempoRetornoTotal += tempoRetorno; //Tempo Retorno

        }

        // Calcular valores médios necessários
        double mediaTempoRetorno = tempoRetornoTotal / processos.size();
        double mediaTempoResposta = tempoRespostaTotal / processos.size();
        double mediaTempoEspera = tempoEsperaTotal / processos.size();


        // Imprimir resultados
        System.out.println("FCFS " + String.format("Media tempo retorno: %.2f", mediaTempoRetorno) + " " + String.format("Media tempo resposta: %.1f", mediaTempoResposta) + " " + String.format("Media tempo Espera: %.1f", mediaTempoEspera));
    }

/*    public void SJF() throws IOException {
        List<Process> processos = new LinkedList<>();

        processos = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        List<Process> lista = new LinkedList<>(processos);

        assert processos != null;
        lista.sort(Comparator.comparingInt(Process::getTempoDeExecucao)); //Ordenando por tempo de execução os processos que chegaram

        int tempoAtual = 0;
        int tempoInicio = 0;

        // Calcular métricas
        double tempoEspera = 0;
        double tempoRetorno = 0;
        double tempoResposta = 0;

        double tempoEsperaTotal = 0;
        double tempoRetornoTotal = 0;
        double tempoRespostaTotal = 0;

        Queue<Process> processoAux = new LinkedList<>() ;

        Queue<Process> filaOrdenada = new LinkedList<>(lista);


        for (Process processo : filaOrdenada) {

                if (tempoAtual < processo.getTempoDeChegada()) {
                    // Se o processo ainda não chegou, espere

                    processoAux.add(processo);
                   tempoAtual = processo.getTempoDeChegada();
                   filaOrdenada.poll();
                    lista.addAll(filaOrdenada);
                    System.out.println(processo.getTempoDeChegada() + " " + processo.getTempoDeExecucao());

                    continue;

                }
            System.out.println(processo.getTempoDeChegada() + " " + processo.getTempoDeExecucao());
            processoAux.add(processo);
        }


/*
                int tempoDeEntrada = Math.max(tempoAtual, processo.getTempoDeChegada());
                tempoResposta = tempoDeEntrada - processo.getTempoDeChegada();
                tempoRespostaTotal += tempoResposta; // Tempo Resposta
                tempoEsperaTotal = tempoRespostaTotal; //Tempo de espera - No FCFS o tempo de  resposta é igual ao tempo de espera

                tempoAtual = tempoDeEntrada + processo.getTempoDeExecucao();
                tempoRetorno = (tempoAtual - processo.getTempoDeChegada());
                tempoRetornoTotal += tempoRetorno; //Tempo Retorno
            }
        }


        // Calcular valores médios necessários
        double mediaTempoRetorno = tempoRetornoTotal / processos.size();
        double mediaTempoResposta = tempoRespostaTotal / processos.size();
        double mediaTempoEspera = tempoEsperaTotal / processos.size();


        // Imprimir resultados
        System.out.println("SJF " + String.format("Media tempo retorno: %.2f", mediaTempoRetorno) + " " + String.format("Media tempo resposta: %.1f", mediaTempoResposta) + " " + String.format("Media tempo Espera: %.1f", mediaTempoEspera));
    }*/
/*
    public void SJFT() throws IOException {
        Queue<Process> processos = new ArrayList<>();

        processos = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        assert processos != null;
        processos.sort(Comparator.comparingInt(Process::getTempoDeExecucao));

        // Simular a execução dos processos
        int tempoAtual = 0;
        for (Process processo : processos) {
            System.out.println(processo.getNomeProcesso() + " " + processo.getTempoDeChegada() + " " + processo.getTempoDeExecucao());
            if (tempoAtual < processo.getTempoDeChegada()) {
                // Se o processo ainda não chegou, espere
                tempoAtual = processo.getTempoDeChegada();
            }


            System.out.println("Executando " + processo.getNomeProcesso() + " no tempo " + tempoAtual);
            tempoAtual += processo.getTempoDeExecucao();
        }
    }*/

   public void roundRobin() throws IOException {
        int tempoAtual = 0;
        List<Process> processos = new ArrayList<>();

        processos = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)


        while (!processos.isEmpty()) {
            Process processoAtual = processos.get(0);
            if (processoAtual.getTempoRestante() > 0) {
                int tempoDeExecucaoAtual = Math.min(2, processoAtual.getTempoRestante());

                System.out.println(tempoDeExecucaoAtual + " unidades de tempo.");

                processoAtual.setTempoRestante(processoAtual.getTempoRestante() - tempoDeExecucaoAtual);
                tempoAtual += tempoDeExecucaoAtual;

                // Movemos o processo para o final da lista se ele ainda não foi concluído
                if (processoAtual.getTempoRestante() > 0) {
                    processos.add(processos.remove(0));
                } else {
                    // Removemos o processo se ele foi concluído
                    processos.remove(0);
                }
            } else {
                // Removemos o processo se ele foi concluído
                processos.remove(0);
            }
        }
    }
}
