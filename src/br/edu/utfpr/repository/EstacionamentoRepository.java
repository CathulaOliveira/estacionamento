package br.edu.utfpr.repository;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Estacionamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class EstacionamentoRepository implements Repository<Estacionamento> {

    @Override
    public List<Estacionamento> buscarTodos() {
        Connection conn = ConnectDataBase.createConnections();
        List<Estacionamento> retorno = new ArrayList<>();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM estacionamento"
            );
            findAndBuild(conn, retorno, psBuscar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public Estacionamento buscarPorId(int idEstacionamento) {
        Connection conn = ConnectDataBase.createConnections();
        Estacionamento retorno = new Estacionamento();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM estacionamento WHERE id = ?"
            );
            psBuscar.setInt(1, idEstacionamento);
            psBuscar.executeQuery();
            ResultSet resultSet = psBuscar.getResultSet();
            while (resultSet.next()) {
                retorno = Estacionamento.builder()
                        .nome(resultSet.getString(2))
                        .endereco(resultSet.getString(3))
                        .telefone(resultSet.getString(4))
                        .valorHora(resultSet.getDouble(4))
                        .minutosTolerancia(resultSet.getInt(4))
                        .totalVagas(resultSet.getInt(5))
                        .build();
                retorno.setId(resultSet.getInt(1));
            }
            psBuscar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public Estacionamento salvar(Estacionamento estacionamento) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psSalvar = conn.prepareStatement(
                    "INSERT INTO estacionamento" +
                            "(nome, endereco, telefone, valorHora, minutosTolerancia, totalVagas) " +
                            "VALUES(?, ?, ?, ?, ?, ?)", RETURN_GENERATED_KEYS
            );
            psSalvar.setString(1, estacionamento.getNome());
            psSalvar.setString(2, estacionamento.getEndereco());
            psSalvar.setString(3, estacionamento.getTelefone());
            psSalvar.setDouble(4, estacionamento.getValorHora());
            psSalvar.setInt(5, estacionamento.getMinutosTolerancia());
            psSalvar.setInt(6, estacionamento.getTotalVagas());

            int linhasAfetadas = psSalvar.executeUpdate();
            ResultSet generatedKeys = psSalvar.getGeneratedKeys();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ADICIONAR ESTACIONAMENTO %s%n", estacionamento.getNome().toUpperCase());
            else {
                if(generatedKeys.next())
                    estacionamento.setId(generatedKeys.getInt(1));
            }
            psSalvar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CADASTRAR ESTACIONAMENTO");
        }
        return estacionamento;
    }

    @Override
    public Estacionamento atualizar(Estacionamento estacionamento) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psAlterar = conn.prepareStatement(
                    "UPDATE estacionamento " +
                            "SET nome = ? AND " +
                            "endereco = ? AND " +
                            "telefone = ? AND " +
                            "valorHora = ? AND " +
                            "minutosTolerancia = ? AND " +
                            "totalVagas = ? " +
                            "WHERE id = ? "
            );
            psAlterar.setString(1, estacionamento.getNome());
            psAlterar.setString(2, estacionamento.getEndereco());
            psAlterar.setString(3, estacionamento.getTelefone());
            psAlterar.setDouble(4, estacionamento.getValorHora());
            psAlterar.setInt(5, estacionamento.getMinutosTolerancia());
            psAlterar.setInt(6, estacionamento.getTotalVagas());
            psAlterar.setInt(7, estacionamento.getId());

            int linhasAfetadas = psAlterar.executeUpdate();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ATUALIZAR ESTACIONAMENTO %s%n", estacionamento.getNome().toUpperCase());

            psAlterar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ATUALIZAR ESTACIONAMENTO");
        }
        return estacionamento;
    }

    @Override
    public boolean remover(Estacionamento estacionamento) {
        return remover(estacionamento.getId());
    }

    @Override
    public boolean remover(int i) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psExcluir = conn.prepareStatement("" +
                    "DELETE FROM estacionamento WHERE id=?"
            );
            psExcluir.setInt(1, i);
            int linhasAfetadas = psExcluir.executeUpdate();
            psExcluir.close();
            conn.close();

            return linhasAfetadas == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO EXCLUIR ESTACIONAMENTO");
        }
        return false;
    }

    private void findAndBuild(Connection conn, List<Estacionamento> retorno, PreparedStatement psBuscar) throws SQLException {
        psBuscar.executeQuery();
        ResultSet resultSet = psBuscar.getResultSet();
        while (resultSet.next()) {
            Estacionamento estacionamento = Estacionamento.builder()
                    .nome(resultSet.getString(2))
                    .endereco(resultSet.getString(3))
                    .telefone(resultSet.getString(4))
                    .valorHora(resultSet.getDouble(4))
                    .minutosTolerancia(resultSet.getInt(4))
                    .totalVagas(resultSet.getInt(5))
                    .build();
            estacionamento.setId(resultSet.getInt(1));
            retorno.add(estacionamento);
        }
        psBuscar.close();
        conn.close();
    }
}
