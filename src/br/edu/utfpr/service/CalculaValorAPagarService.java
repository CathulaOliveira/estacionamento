package br.edu.utfpr.service;

import br.edu.utfpr.model.Movimentacao;
import br.edu.utfpr.service.CRUD.impl.MovimentacaoServiceImpl;

import java.time.temporal.ChronoUnit;

public class CalculaValorAPagarService {

    MovimentacaoServiceImpl movimentacaoService = new MovimentacaoServiceImpl();

    public Double calcularSaida(Movimentacao movimentacao) {
        long totalHoras = ChronoUnit.HOURS.between(movimentacao.getDataHoraEntrada(), movimentacao.getDataHoraSaida());
        if (totalHoras >= 1) {
            double valorHora = movimentacao.getEstacionamento().getValorHora();
            return totalHoras * valorHora;
        }
        return Double.valueOf(0);
    }
}
