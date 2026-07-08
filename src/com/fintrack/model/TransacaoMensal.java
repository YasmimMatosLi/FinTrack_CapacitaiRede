package com.fintrack.model;

import java.time.LocalDate;

public class TransacaoMensal extends Transacao{
    private String mes;

    public TransacaoMensal(String decricao, double valor, boolean ehReceita, LocalDate data, String mes) {
        super(decricao, valor, ehReceita, data);
        this.mes = mes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    @Override
    public String toString() {
        return super.toString() + "TransacaoMensal{" +
                "mes='" + mes + '\'' +
                '}';
    }
}
