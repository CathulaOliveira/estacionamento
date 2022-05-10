package br.edu.utfpr.model;

import br.edu.utfpr.sql.CreateTableHelper;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(callSuper = true)
public class Movimentacao extends Model {

    private LocalDateTime dataHoraEntrada;
    private LocalDateTime dataHoraSaida;
    private Cliente cliente;
    private String placaCarro;
    private Estacionamento estacionamento;

    @Override
    public CreateTableHelper generateCreateTableSQL() {
        String createTable = "" +
                "CREATE TABLE IF NOT EXISTS movimentacao (" +
                "id SERIAL PRIMARY KEY, " +
                "dataHoraEntrada TIMESTAMP NOT NULL, " +
                "dataHoraSaida TIMESTAMP, " +
                "id_cliente INT NOT NULL, " +
                "id_estacionamento INT NOT NULL, " +
                "placaCarro VARCHAR(7))";

        String dropTable = "" +
                "DROP TABLE IF EXISTS movimentacao";

        return new CreateTableHelper(createTable, dropTable);
    }
}
