package model;

public class Process {


    int tempoDeChegada;
    int tempoDeExecucao;
    int quantProcess;
    double tempoUltimaExec;

    public Process(int tempoDeChegada, int tempoDeExecucao) {
        this.tempoDeChegada = tempoDeChegada;
        this.tempoDeExecucao = tempoDeExecucao;
        this.quantProcess = 0;
    }

    public double getTempoUltimaExec() {
        return tempoUltimaExec;
    }

    public void setTempoUltimaExec(double tempoUltimaExec) {
        this.tempoUltimaExec = tempoUltimaExec;
    }

    public int getQuantProcess() {
        return quantProcess;
    }

    public void setQuantProcess(int quantProcess) {
        this.quantProcess = quantProcess;
    }

    public int getTempoDeChegada() {
        return tempoDeChegada;
    }

    public int getTempoDeExecucao() {
        return tempoDeExecucao;
    }

    public void setTempoDeExecucao(int tempoDeExecucao) {
        this.tempoDeExecucao = tempoDeExecucao;
    }

}

