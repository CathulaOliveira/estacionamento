package br.edu.utfpr.service.CRUD.impl;

import br.edu.utfpr.model.Estacionamento;
import br.edu.utfpr.model.Movimentacao;
import br.edu.utfpr.repository.MovimentacaoRepository;
import br.edu.utfpr.service.CRUD.CRUDService;

import java.util.List;

import static br.edu.utfpr.logger.Logger.log;
import static br.edu.utfpr.logger.Tipo.INFO;

public class MovimentacaoServiceImpl implements CRUDService<Movimentacao> {

    MovimentacaoRepository repository = new MovimentacaoRepository();

    @Override
    public List<Movimentacao> buscarTodos() {
        log(INFO, "Buscando movimentações");
        var retorno= repository.buscarTodos();
        log(INFO,"Finalizou busca movimentações");
        return retorno;
    }

    @Override
    public Movimentacao atualizar(Movimentacao registro) {
        log(INFO, "Iniciou atualizarMovimentacao() " + registro.toString());
        repository.atualizar(registro);
        log(INFO, "Finalizou" + registro.toString());
        return registro;
    }

    @Override
    public Movimentacao buscarPorId(int idRegistro) {
        log(INFO, "Buscando movimentacao id " + idRegistro);
        var retorno= repository.buscarPorId(idRegistro);
        log(INFO,"Finalizou busca movimentação");
        return retorno;
    }

    public Movimentacao buscarMovimentacaoAtivaPorIdCliente(int idCliente) {
        log(INFO, "Buscando movimentacao filtrando pelo cliente " + idCliente);
        var retorno= repository.buscarMovimentacaoAtivaPorIdCliente(idCliente);
        log(INFO,"Finalizou busca movimentação por cliente");
        return retorno;
    }

    @Override
    public Movimentacao salvar(Movimentacao registro) {
        log(INFO, "Iniciou salvarMovimentacao() " + registro.toString());
        repository.salvar(registro);
        log(INFO, "Finalizou" + registro.toString());
        return registro;
    }

    @Override
    public boolean removerRegistro(Movimentacao registro) {
        log(INFO, "Iniciar remover movimentacao "+ registro.getId());
        var retorno = repository.remover(registro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }

    @Override
    public boolean removerRegistroPorId(int idRegistro) {
        log(INFO, "Iniciar remover movimentacao id = "+ idRegistro);
        var retorno = repository.remover(idRegistro);
        log(INFO, "Terminou de excluir");
        return retorno;
    }

}
