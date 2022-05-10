package br.edu.utfpr.model;

import br.edu.utfpr.sql.CreateTableHelper;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class Estacionamento extends Model {

    private String nome;
    private String endereco;
    private String telefone;
    private double valorHora;
    private int minutosTolerancia;
    private int totalVagas;

    @Override
    public CreateTableHelper generateCreateTableSQL() {
        String createTable = "" +
                "CREATE TABLE IF NOT EXISTS estacionamento (" +
                "id SERIAL PRIMARY KEY, " +
                "nome VARCHAR(50) NOT NULL, " +
                "endereco VARCHAR(50), " +
                "telefone VARCHAR(20), " +
                "valorHora NUMERIC(5,2) NOT NULL, " +
                "minutosTolerancia INT NOT NULL, " +
                "totalVagas INT NOT NULL);";


        String dropTable = "" +
                "DROP TABLE IF EXISTS estacionamento";

        return new CreateTableHelper(createTable, dropTable);
    }
}
