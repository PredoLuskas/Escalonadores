package escalonadores;

import FilesManipulator.IOFiles;
import model.Process;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<Process> listaDeProntidao = new ArrayList<>();

        listaDeProntidao = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeChegada)); //Ordenando por tempo de chegada os processos que chegaram


        int quantum = 2; // Tamanho do quantum em unidades de tempo

        double tempoEspera = 0;
        double tempoRetorno = 0;
        double tempoResposta = 0;
        int tamList = listaDeProntidao.size();

        double tempoEsperaTotal = 0;
        double tempoRetornoTotal = 0;
        double tempoRespostaTotal = 0;

        for (Process process : listaDeProntidao) {
            process.setQuantProcess(0);
        }
        int i = 0;
        while (!listaDeProntidao.isEmpty()) {
            Process processoAtual = listaDeProntidao.get(0);

            // Verifique se o processo chegou antes do tempo atual
     /*      if (processoAtual.getTempoDeChegada() > tempoAtual) {
             //  tempoAtual = processoAtual.getTempoDeChegada();
               listaDeProntidao.add(processoAtual);
               listaDeProntidao.remove(0);
               continue;
           }
*/
            // Execute o processo por um quantum ou até que ele termine
            int tempoExecutado = Math.min(quantum, processoAtual.getTempoDeExecucao());
            processoAtual.setTempoDeExecucao(processoAtual.getTempoDeExecucao() - tempoExecutado);

            if (listaDeProntidao.get(0).getQuantProcess() == 0) {
                tempoResposta = tempoAtual - listaDeProntidao.get(0).getTempoDeChegada();
                tempoRespostaTotal+=tempoResposta;
            }

            listaDeProntidao.get(0).setQuantProcess(++i);
            // Atualize o tempo atual
            tempoAtual += tempoExecutado;

            // Verifique se o processo ainda não terminou
            if (processoAtual.getTempoDeExecucao() >= 0) {
                // Coloque o processo no final da lista
                if (processoAtual.getTempoDeExecucao() == 0) {
                    // O processo terminou, imprima informações
                    System.out.println(" terminou em " + tempoAtual + " unidades de tempo.");
                    tempoRetorno = tempoAtual - listaDeProntidao.get(0).getTempoDeChegada();
                    tempoRetornoTotal += tempoRetorno;
                    listaDeProntidao.remove(0); // Remova da posição atual
                    continue;
                }
                listaDeProntidao.add(processoAtual);
                listaDeProntidao.remove(0); // Remova da posição atual
            }

/*            int tempoDeEntrada = Math.max(tempoAtual, processoAtual.getTempoDeChegada());
            tempoResposta = tempoDeEntrada - processo.getTempoDeChegada();
            tempoRespostaTotal += tempoResposta; // Tempo Resposta
            tempoEsperaTotal = tempoRespostaTotal; //Tempo de espera - No FCFS o tempo de  resposta é igual ao tempo de espera

            tempoAtual = tempoDeEntrada + processo.getTempoDeExecucao();
            tempoRetorno = (tempoAtual - processo.getTempoDeChegada());*/
        }

        System.out.println("Media tempo retorno: " + tempoRetornoTotal / tamList + "media tempo resposta:" +tempoRespostaTotal / tamList );
    }
}

