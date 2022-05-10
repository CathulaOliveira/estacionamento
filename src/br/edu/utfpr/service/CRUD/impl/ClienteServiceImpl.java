package br.edu.utfpr.service.CRUD.impl;

import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.repository.ClienteRepository;
import br.edu.utfpr.service.CRUD.CRUDService;

import java.util.List;

import static br.edu.utfpr.logger.Logger.*;
import static br.edu.utfpr.logger.Tipo.*;

public class ClienteServiceImpl implements CRUDService<Cliente> {

    ClienteRepository repository = new ClienteRepository();

    @Override
    public Cliente salvar(Cliente registro) {
        log(INFO, "Iniciou salvarCliente() " + registro.toString());
        repository.salvar(registro);
        log(INFO, "Finalizou" + registro.toString());
        return registro;
    }

    @Override
    public Cliente atualizar(Cliente registro) {
        log(INFO, "Iniciou atualizarCliente() " + registro.toString());
        repository.atualizar(registro);
        log(INFO, "Finalizou" + registro.toString());
        return registro;
    }

    @Override
    public List<Cliente> buscarTodos() {
        log(INFO, "Buscando clientes");
        var retorno= repository.buscarTodos();
        log(INFO,"Finalizou busca clientes");
        return retorno;
    }

    @Override
    public Cliente buscarPorId(int idRegistro) {
        log(INFO, "Buscando cliente id " + idRegistro);
        var retorno= repository.buscarPorId(idRegistro);
        log(INFO,"Finalizou busca cliente " + retorno.toString());
        return retorno;
    }

    @Override
    public boolean removerRegistro(Cliente registro) {
        log(INFO, "Iniciar removerCliente "+ registro.getNome());
        var retorno = repository.remover(registro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }

    @Override
    public boolean removerRegistroPorId(int idRegistro) {
        log(INFO, "Iniciar removerCliente id = "+ idRegistro);
        var retorno = repository.remover(idRegistro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }

}
