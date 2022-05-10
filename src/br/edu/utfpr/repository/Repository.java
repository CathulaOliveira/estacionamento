package br.edu.utfpr.repository;

import br.edu.utfpr.model.Model;

import java.util.List;

public interface Repository <T extends Model>{
    List<T> buscarTodos();
    T buscarPorId(int id);
    T salvar(T t);
    T atualizar(T t);
    boolean remover(T t);
    boolean remover(int i);
}
