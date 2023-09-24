package escalonadores;

import FilesManipulator.IOFiles;
import model.Process;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class Escalonador {

    String path = "C:/Projetos/Java/SistemaOperacional/Escalonadores/src/resource/file.txt";

    public void FCFS() throws IOException {
        List<Process> processos ;

        processos = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        assert processos != null;
        processos.sort(Comparator.comparingInt(Process::getTempoDeChegada)); //Ordenando por tempo de chegada os processos que chegaram

        int tempoAtual = 0;
        double tempoRetorno;
        double tempoResposta;

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

    public void roundRobin() throws IOException {
        int tempoAtual = 0;
        List<Process> listaDeProntidao;

        listaDeProntidao = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        assert listaDeProntidao != null;
        listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeChegada)); //Ordenando por tempo de chegada os processos que chegaram


        int quantum = 2; // Tamanho do quantum em unidades de tempo

        double tempoRetorno ;
        double tempoResposta = 0;
        int tamList = listaDeProntidao.size();

        double tempoEsperaTotal = 0;
        double tempoRetornoTotal = 0;
        double tempoRespostaTotal = 0;

        boolean flag = true;

        for (Process process : listaDeProntidao) {
            process.setQuantProcess(0);
        }
        int i = 0;
        int j= 0;
        int aux = 0;
        while (!listaDeProntidao.isEmpty()) {
            var processoAtual = listaDeProntidao.get(0);

            // Verifique se o processo chegou antes do tempo atual
            if (processoAtual.getTempoDeChegada() > tempoAtual) {
                for (Process process : listaDeProntidao) {
                    if (process.getTempoDeChegada() <= tempoAtual) {
                        aux = j;
                        continue;
                    }
                    j++;
                }
                if(processoAtual.getTempoDeChegada() > tempoAtual && aux == 0){
                    listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeChegada));
                    tempoAtual = listaDeProntidao.get(aux).getTempoDeChegada();
                }
            }

            // Execute o processo por um quantum ou até que ele termine
            int tempoExecutado = Math.min(quantum, processoAtual.getTempoDeExecucao());
            processoAtual.setTempoDeExecucao(processoAtual.getTempoDeExecucao() - tempoExecutado);

            if (listaDeProntidao.get(0).getQuantProcess() == 0) {
                tempoResposta = tempoAtual - listaDeProntidao.get(0).getTempoDeChegada();
                tempoRespostaTotal += tempoResposta;
                listaDeProntidao.set(0,processoAtual).setTempoUltimaExec(tempoAtual + tempoExecutado);
                flag = false;

            }

            if (processoAtual.getTempoDeExecucao() == 0 && processoAtual.getQuantProcess() == 0) {
                listaDeProntidao.set(0, processoAtual).setTempoUltimaExec((tempoResposta));
            }

            listaDeProntidao.get(0).setQuantProcess(++i);

            if(flag) {
                listaDeProntidao.set(0, processoAtual).setTempoUltimaExec((tempoAtual - processoAtual.getTempoUltimaExec()));
            }

            // Atualize o tempo atual
            tempoAtual += tempoExecutado;

            // Verifique se o processo ainda não terminou
            if (processoAtual.getTempoDeExecucao() >= 0) {
                // Coloque o processo no final da lista
                if (processoAtual.getTempoDeExecucao() == 0) {
                    // O processo terminou, imprima informações
                    System.out.println(" terminou em " + tempoAtual + " unidades de tempo.");
                    tempoRetorno = tempoAtual - listaDeProntidao.get(0).getTempoDeChegada();
                    tempoEsperaTotal = tempoEsperaTotal + processoAtual.getTempoUltimaExec();
                    tempoRetornoTotal += tempoRetorno;
                    listaDeProntidao.remove(0); // Remova da posição atual
                    flag = true;

                    continue;
                }
                listaDeProntidao.add(processoAtual);
                listaDeProntidao.remove(0); // Remova da posição atual
            }
            aux = 0;
            flag = true;
        }


        System.out.println(" RR Media tempo retorno: " + tempoRetornoTotal / tamList + "media tempo resposta:" + tempoRespostaTotal / tamList + "Media tempo Espera: " + tempoEsperaTotal / tamList);
    }

    public void SJF() throws IOException {
        int tempoAtual = 0;
        List<Process> listaDeProntidao;

        listaDeProntidao = IOFiles.readerFile(path);  // Adicione processos à lista (nome, tempo de chegada, tempo de execução)

        assert listaDeProntidao != null;
        listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeExecucao)); //Ordenando por tempo de chegada os processos que chegaram


        double tempoRetorno;
        double tempoResposta;
        int tamList = listaDeProntidao.size();

        double tempoEsperaTotal = 0;
        double tempoRetornoTotal = 0;
        double tempoRespostaTotal = 0;
        double tempoInicial;

        for (Process process : listaDeProntidao) {
            process.setQuantProcess(0);
        }
        int i = 0;
        int aux = 0;
        while (!listaDeProntidao.isEmpty()) {
            Process processoAtual = listaDeProntidao.get(0);

            // Verifique se o processo chegou antes do tempo atual
            if (processoAtual.getTempoDeChegada() > tempoAtual) {
                for (Process process : listaDeProntidao) {
                    if (process.getTempoDeChegada() <= tempoAtual) {
                        aux = i;
                        continue;
                    }
                    i++;
                }
                if(processoAtual.getTempoDeChegada() > tempoAtual && aux == 0){
                    listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeChegada));
                    tempoAtual = listaDeProntidao.get(aux).getTempoDeChegada();
                }
            }

            tempoInicial = tempoAtual;
            tempoAtual += listaDeProntidao.get(aux).getTempoDeExecucao();
            System.out.println(" terminou em " + tempoAtual + " unidades de tempo.");

            double tempoDeEntrada = Math.max(tempoInicial, listaDeProntidao.get(aux).getTempoDeChegada());
            tempoResposta = tempoDeEntrada - listaDeProntidao.get(aux).getTempoDeChegada();
            tempoRespostaTotal += tempoResposta; // Tempo Resposta
            tempoEsperaTotal = tempoRespostaTotal; //Tempo de espera - No FCFS o tempo de  resposta é igual ao tempo de espera

            tempoRetorno = (tempoAtual - listaDeProntidao.get(aux).getTempoDeChegada());
            tempoRetornoTotal += tempoRetorno; //Tempo Retorno

            listaDeProntidao.remove(aux);
            listaDeProntidao.sort(Comparator.comparingInt(Process::getTempoDeExecucao));
            i = 0;
            aux = 0;

        }
        double mediaTempoRetorno = tempoRetornoTotal / tamList;
        double mediaTempoResposta = tempoRespostaTotal / tamList;
        double mediaTempoEspera = tempoEsperaTotal / tamList;


        // Imprimir resultados
        System.out.println("SJF " + String.format("Media tempo retorno: %.2f", mediaTempoRetorno) + " " + String.format("Media tempo resposta: %.2f", mediaTempoResposta) + " " + String.format("Media tempo Espera: %.2f", mediaTempoEspera));
    }
}


