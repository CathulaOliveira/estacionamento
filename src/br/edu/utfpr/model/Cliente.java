package br.edu.utfpr.model;

import br.edu.utfpr.sql.CreateTableHelper;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class Cliente extends Model {

    private String nome;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;

    @Override
    public CreateTableHelper generateCreateTableSQL() {
        String createTable = "" +
                "CREATE TABLE IF NOT EXISTS cliente (" +
                    "id SERIAL PRIMARY KEY, " +
                    "nome VARCHAR(50) NOT NULL, " +
                    "telefone VARCHAR(20) NOT NULL, " +
                    "email VARCHAR(70) NOT NULL, " +
                    "dataNascimento DATE NOT NULL);";


        String dropTable = "" +
                "DROP TABLE IF EXISTS cliente";

        return new CreateTableHelper(createTable, dropTable);


    }

}
