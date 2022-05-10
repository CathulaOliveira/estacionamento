package br.edu.utfpr.service.CRUD;

import java.util.List;

public interface CRUDService<T> {

    List<T> buscarTodos();

    T buscarPorId(int idRegistro);

    T salvar(T registro);

    T atualizar(T registro);

    boolean removerRegistro(T registro);

    boolean removerRegistroPorId(int idRegistro);

}
