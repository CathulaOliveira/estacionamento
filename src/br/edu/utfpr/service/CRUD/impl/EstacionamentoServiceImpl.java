package br.edu.utfpr.service.CRUD.impl;

import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.model.Estacionamento;
import br.edu.utfpr.repository.EstacionamentoRepository;
import br.edu.utfpr.service.CRUD.CRUDService;

import java.util.List;

import static br.edu.utfpr.logger.Logger.log;
import static br.edu.utfpr.logger.Tipo.INFO;

public class EstacionamentoServiceImpl implements CRUDService<Estacionamento> {

    EstacionamentoRepository estacionamentoRepository = new EstacionamentoRepository();

    @Override
    public Estacionamento salvar(Estacionamento registro) {
        log(INFO, "Iniciou salvarEstacionamento() " + registro.toString());
        estacionamentoRepository.salvar(registro);
        log(INFO, "Finalizou salvar: " + registro.toString());
        return registro;
    }

    @Override
    public Estacionamento atualizar(Estacionamento registro) {
        log(INFO, "Iniciou atualizarCliente() " + registro.toString());
        estacionamentoRepository.atualizar(registro);
        log(INFO, "Finalizou" + registro.toString());
        return registro;
    }

    @Override
    public List<Estacionamento> buscarTodos() {
        log(INFO, "Buscando estacionamentos");
        var retorno= estacionamentoRepository.buscarTodos();
        log(INFO,"Finalizou busca estacionamentos");
        return retorno;
    }

    @Override
    public Estacionamento buscarPorId(int idRegistro) {
        log(INFO, "Buscando estacionamento " + idRegistro);
        var retorno= estacionamentoRepository.buscarPorId(idRegistro);
        log(INFO,"Finalizou busca estacionamento " + retorno.toString());
        return retorno;
    }

    @Override
    public boolean removerRegistro(Estacionamento registro) {
        log(INFO, "Iniciar removerEstacionamento "+ registro.getNome());
        var retorno = estacionamentoRepository.remover(registro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }

    @Override
    public boolean removerRegistroPorId(int idRegistro) {
        log(INFO, "Iniciar removerEstacionamento id = " + idRegistro);
        var retorno = estacionamentoRepository.remover(idRegistro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }


}
