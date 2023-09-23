package model;

public class Process {


    int tempoDeChegada;
    int tempoDeExecucao;
    int quantProcess;
    public int getQuantProcess() {
        return quantProcess;
    }

    public void setQuantProcess(int quantProcess) {
        this.quantProcess = quantProcess;
    }



    public int getTempoRestante() {
        return tempoRestante;
    }

    public void setTempoRestante(int tempoRestante) {
        this.tempoRestante = tempoRestante;
    }

    int tempoRestante;

    public Process() {
    }
    public Process(int tempoDeChegada, int tempoDeExecucao) {
        this.tempoDeChegada = tempoDeChegada;
        this.tempoDeExecucao = tempoDeExecucao;
        this.quantProcess = 0;
    }
    public int getTempoDeChegada() {
        return tempoDeChegada;
    }

    public void setTempoDeChegada(int tempoDeChegada) {
        this.tempoDeChegada = tempoDeChegada;
    }
    public int getTempoDeExecucao() {
        return tempoDeExecucao;
    }

    public void setTempoDeExecucao(int tempoDeExecucao) {
        this.tempoDeExecucao = tempoDeExecucao;
    }

}

