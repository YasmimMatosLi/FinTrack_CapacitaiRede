package fintrack.model;

import java.time.LocalDate;

public class Transacao {
    private int id;
    private String decricao;
    private double valor;
    private boolean ehReceita;
    private LocalDate data;

    public Transacao(String decricao, double valor, boolean ehReceita, LocalDate data) {
        this.decricao = decricao;
        this.valor = valor;
        this.ehReceita = ehReceita;
        this.data = data;
    }

    public Transacao() {
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isEhReceita() {
        return ehReceita;
    }

    public void setEhReceita(boolean ehReceita) {
        this.ehReceita = ehReceita;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "decricao='" + decricao + '\'' +
                ", valor=" + valor +
                ", ehReceita=" + ehReceita +
                ", data=" + data +
                '}';
    }
}
