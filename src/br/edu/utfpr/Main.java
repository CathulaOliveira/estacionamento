package br.edu.utfpr;

import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.model.Estacionamento;
import br.edu.utfpr.model.Movimentacao;
import br.edu.utfpr.service.CRUD.CRUDService;
import br.edu.utfpr.service.CRUD.impl.ClienteServiceImpl;
import br.edu.utfpr.service.CRUD.impl.MovimentacaoServiceImpl;
import br.edu.utfpr.service.CRUD.impl.EstacionamentoServiceImpl;
import br.edu.utfpr.service.CalculaValorAPagarService;
import br.edu.utfpr.service.ControleEntradaSaidaService;
import br.edu.utfpr.sql.TableControl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {
        CRUDService clienteService = new ClienteServiceImpl();
        CRUDService estacionamentoService = new EstacionamentoServiceImpl();
        ControleEntradaSaidaService controleEntradaSaidaService = new ControleEntradaSaidaService();

        TableControl.createTablesV1();

        Cliente c1 = new Cliente("Teste", "46 99998 9999", "cliente@gmail.com", LocalDate.now());
        Cliente c2 = new Cliente("Tiago", "46 99998 9999", "cliente@gmail.com", LocalDate.now());
        Cliente c3 = new Cliente("Pedro", "46 99998 9999", "cliente@gmail.com", LocalDate.now());
        clienteService.salvar(c1);
        clienteService.salvar(c2);
        clienteService.salvar(c3);

        System.out.println("BUSCANDO TODOS OS CLIENTES " +
                clienteService.buscarTodos().toString());

        Estacionamento e1 = Estacionamento.builder()
                .nome("Teste")
                .valorHora(5)
                .minutosTolerancia(30)
                .totalVagas(2)
                .build();
        estacionamentoService.salvar(e1);

        System.out.println("BUSCANDO ESTACIONAMENTO 1 " + estacionamentoService.buscarPorId(1));

        System.out.println("CLIENTE 1 CHEGOU NO ESTACIONAMENTO");
        Movimentacao movimentacao1 = Movimentacao.builder()
                .dataHoraEntrada(LocalDateTime.now())
                .cliente(c1)
                .estacionamento(e1)
                .placaCarro("AAAA")
                .build();
        controleEntradaSaidaService.realizarEntrada(movimentacao1);

        System.out.println("CLIENTE 2 CHEGOU NO ESTACIONAMENTO");
        Movimentacao movimentacao2 = Movimentacao.builder()
                .dataHoraEntrada(LocalDateTime.now())
                .cliente(c2)
                .estacionamento(e1)
                .placaCarro("BBBB")
                .build();
        controleEntradaSaidaService.realizarEntrada(movimentacao2);

        System.out.println("CLIENTE 3 CHEGOU NO ESTACIONAMENTO");
        Movimentacao movimentacao3 = Movimentacao.builder()
                .dataHoraEntrada(LocalDateTime.now())
                .cliente(c3)
                .estacionamento(e1)
                .placaCarro("CCCC")
                .build();
        controleEntradaSaidaService.realizarEntrada(movimentacao3);

        System.out.println("CLIENTE 1 SAINDO NO ESTACIONAMENTO");
        Movimentacao movimentacaoSaida1 =
                controleEntradaSaidaService.realizarSaida(
                        c1,
                        LocalDateTime.now().plusHours(1),
                        movimentacao1
                );
        System.out.println("CLIENTE 1 SAIU DO ESTACIONAMENTO");


        System.out.println("CLIENTE 3 CHEGOU NO ESTACIONAMENTO");
        Movimentacao movimentacao4 = Movimentacao.builder()
                .dataHoraEntrada(LocalDateTime.now())
                .cliente(c3)
                .estacionamento(e1)
                .placaCarro("CCCC")
                .build();
        controleEntradaSaidaService.realizarEntrada(movimentacao3);

        System.out.println("CLIENTE 3 CHEGOU NO ESTACIONAMENTO NOVAMENTE");
        Movimentacao movimentacao5 = Movimentacao.builder()
                .dataHoraEntrada(LocalDateTime.now())
                .cliente(c3)
                .estacionamento(e1)
                .placaCarro("CCCC")
                .build();
        controleEntradaSaidaService.realizarEntrada(movimentacao3);

        System.out.println("CLIENTE 2 SAINDO NO ESTACIONAMENTO");
        Movimentacao movimentacaoSaida2 =
                controleEntradaSaidaService.realizarSaida(
                        c2,
                        LocalDateTime.now().plusMinutes(30),
                        movimentacao2
                );
        System.out.println("CLIENTE 2 SAIU DO ESTACIONAMENTO");

        System.out.println("CLIENTE 3 SAINDO NO ESTACIONAMENTO");
        Movimentacao movimentacaoSaida3 =
                controleEntradaSaidaService.realizarSaida(
                        c3,
                        LocalDateTime.now().plusHours(5),
                        movimentacao3
                );
        System.out.println("CLIENTE 3 SAIU DO ESTACIONAMENTO");
    }
}
