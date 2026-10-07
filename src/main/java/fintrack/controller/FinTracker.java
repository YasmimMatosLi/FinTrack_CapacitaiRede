package fintrack.controller;

import fintrack.exceptions.EntradaInvalidaException;
import fintrack.model.Transacao;

import java.util.ArrayList;

public class FinTracker {
    private ArrayList<Transacao> transacoes = new ArrayList<>();

    public void adicionarTransacao(Transacao transacao)
            throws EntradaInvalidaException {

        if (transacao.getValor() <= 0) {
            throw new EntradaInvalidaException(
                    "O valor da transação deve ser maior que zero."
            );
        }

        transacoes.add(transacao);
    }

    public void listarTransacoes(){
        for (Transacao transacao : transacoes){
            System.out.println(transacao);
        }
    }

    public void removerTransacao(int indice){
        if(indice >= 0 && indice < transacoes.size()){
            transacoes.remove(indice);
        }
    }

    public double calcularSaldoTotal(){
        double saldo = 0;

        for(Transacao transacao : transacoes){
            if(transacao.isEhReceita()){
                saldo += transacao.getValor();
            }else {
               saldo -= transacao.getValor();
            }
        }
        return saldo;
    }
}
