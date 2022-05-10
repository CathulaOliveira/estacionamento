package br.edu.utfpr.model;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.sql.CreateTableHelper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
@ToString
public abstract class Model {

    private Integer id;

    public abstract CreateTableHelper generateCreateTableSQL();

    public void createTable() {
        try {
            Connection conn = ConnectDataBase.createConnections();
            System.out.println("Criando tabela " +  this.getClass().getSimpleName());
            PreparedStatement psCreate = conn.prepareStatement(
                    generateCreateTableSQL().getCreateTable());
            PreparedStatement psDrop = conn.prepareStatement(
                    generateCreateTableSQL().getDropTable());

            psDrop.executeUpdate();
            psCreate.executeUpdate();

            psCreate.close();
            psDrop.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("ERRO AO CRIAR TABELA " + this.getClass().getSimpleName());
            e.printStackTrace();
        }
    }

}
