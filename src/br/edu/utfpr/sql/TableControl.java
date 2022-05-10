package br.edu.utfpr.sql;

import br.edu.utfpr.model.*;

import java.util.List;

public class TableControl {
    public static final List<Model> NEW_TABLES_V1 =
            List.of(
                    new Cliente(),
                    new Estacionamento(),
                    new Movimentacao()
            );

    public static void createTablesV1(){
        System.out.println("INICIANDO CRIAÇÃO DE TABELAS");
        NEW_TABLES_V1.forEach(Model::createTable);
        System.out.println("FIM DA CRIAÇÃO DE TABELAS");
    }
}
