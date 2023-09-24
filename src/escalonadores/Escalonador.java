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
        System.out.printf("FCFS %.2f %.2f %.2f%n", mediaTempoRetorno, mediaTempoResposta, mediaTempoEspera);
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
        double tempoaux;
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

/*            if (processoAtual.getTempoDeExecucao() == 0 && processoAtual.getQuantProcess() == 0) { //nao funciona se o processo tem mais d uma exec
                listaDeProntidao.set(0, processoAtual).setTempoUltimaExec((tempoResposta));
            }*/

            listaDeProntidao.get(0).setQuantProcess(++i);

            // Atualize o tempo atual

            if(flag) {
                tempoaux = tempoAtual - processoAtual.getTempoUltimaExec();
                listaDeProntidao.set(0, processoAtual).setTempoUltimaExec((tempoAtual));
                tempoEsperaTotal += tempoaux;
            }
            tempoAtual += tempoExecutado;

            // Verifique se o processo ainda não terminou
            if (processoAtual.getTempoDeExecucao() >= 0) {
                // Coloque o processo no final da lista
                if (processoAtual.getTempoDeExecucao() == 0) {
                    // O processo terminou, imprima informações
                    tempoRetorno = tempoAtual - listaDeProntidao.get(0).getTempoDeChegada();
               //     tempoEsperaTotal = tempoEsperaTotal + processoAtual.getTempoUltimaExec();
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

        double mediaTempoRetorno = tempoRetornoTotal / tamList;
        double mediaTempoResposta = tempoRespostaTotal / tamList;
        double mediaTempoEspera = (tempoEsperaTotal + tempoResposta) / tamList;

        System.out.printf("RR %.2f %.2f %.2f%n", mediaTempoRetorno, mediaTempoResposta, mediaTempoEspera);
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


        System.out.printf("SJF %.2f %.2f %.2f%n", mediaTempoRetorno, mediaTempoResposta, mediaTempoEspera);
    }
}


