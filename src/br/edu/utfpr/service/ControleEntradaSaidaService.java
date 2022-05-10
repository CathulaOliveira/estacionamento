package br.edu.utfpr.service;

import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.model.Movimentacao;
import br.edu.utfpr.service.CRUD.impl.MovimentacaoServiceImpl;

import java.time.LocalDateTime;

public class ControleEntradaSaidaService {

    MovimentacaoServiceImpl movimentacaoService = new MovimentacaoServiceImpl();
    CalculaValorAPagarService calculaValorAPagarService = new CalculaValorAPagarService();

    public Movimentacao realizarEntrada(Movimentacao movimentacao) {
        Movimentacao movimentacaoAtiva =
                movimentacaoService.buscarMovimentacaoAtivaPorIdCliente(movimentacao.getCliente().getId());
        if (movimentacaoAtiva.getId()==null) {
            return movimentacaoService.salvar(movimentacao);
        } else {
            System.out.println("CLIENTE JÁ ESTÁ NO ESTACIONAMENTO, ENTRADA NEGADA");

        }
        return  null;
    }

    public Movimentacao realizarSaida(Cliente cliente, LocalDateTime dataHoraSaida, Movimentacao movimentacao) {
//        Movimentacao movimentacao = movimentacaoService.buscarPorIdCliente(cliente.getId());
        movimentacao.setDataHoraSaida(dataHoraSaida);
//        movimentacaoService.atualizar(movimentacao);
        Double valorAPagar = calculaValorAPagarService.calcularSaida(movimentacao);
        System.out.println("Valor a pagar R$ " + valorAPagar);
        return movimentacao;
    }
}
