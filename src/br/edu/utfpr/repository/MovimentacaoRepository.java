package br.edu.utfpr.repository;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Cliente;
import br.edu.utfpr.model.Movimentacao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MovimentacaoRepository implements Repository<Movimentacao> {

    @Override
    public List<Movimentacao> buscarTodos() {
        Connection conn = ConnectDataBase.createConnections();
        List<Movimentacao> retorno = new ArrayList<>();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM movimentacao"
            );
            findAndBuild(conn, retorno, psBuscar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public Movimentacao buscarPorId(int idMovimentacao) {
        Connection conn = ConnectDataBase.createConnections();
        Movimentacao retorno = new Movimentacao();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM moviementacao WHERE id = ?"
            );
            psBuscar.setInt(1, idMovimentacao);
            psBuscar.executeQuery();
            ResultSet resultSet = psBuscar.getResultSet();
            while (resultSet.next()) {
                retorno = Movimentacao.builder()
                        .dataHoraEntrada(LocalDateTime.parse(resultSet.getString(2)))
                        .dataHoraSaida(LocalDateTime.parse(resultSet.getString(3)))
                        .placaCarro(resultSet.getString(5))
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

    public Movimentacao buscarMovimentacaoAtivaPorIdCliente(int idCliente) {
        Connection conn = ConnectDataBase.createConnections();
        Movimentacao retorno = new Movimentacao();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM movimentacao " +
                            "WHERE id_cliente = ? AND " +
                            "dataHoraSaida is null"
            );
            psBuscar.setInt(1, idCliente);
            psBuscar.executeQuery();
            ResultSet resultSet = psBuscar.getResultSet();
            while (resultSet.next()) {
                retorno = Movimentacao.builder()
                        // TODO: Dúvida, como obter os objetos e nulos e como fazer o parse de data?
//                        .dataHoraEntrada(LocalDateTime.parse(resultSet.getString(2)))

//                        .dataHoraEntrada(resultSet.getString(3) != null ? LocalDateTime.parse(resultSet.getString(3)) : null)
                        .placaCarro(resultSet.getString(5))
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
    public Movimentacao salvar(Movimentacao movimentacao) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psSalvar = conn.prepareStatement(
                    "INSERT INTO movimentacao(dataHoraEntrada, dataHoraSaida, id_cliente, id_estacionamento, placaCarro) " +
                            "VALUES(?, ?, ?, ?, ?)", RETURN_GENERATED_KEYS
            );
            psSalvar.setTimestamp(1, Timestamp.valueOf(movimentacao.getDataHoraEntrada()));
            if (movimentacao.getDataHoraSaida() != null)
                psSalvar.setTimestamp(2, Timestamp.valueOf(movimentacao.getDataHoraSaida()));
            else
                psSalvar.setTimestamp(2, null);
            psSalvar.setInt(3, movimentacao.getCliente().getId());
            psSalvar.setInt(4, movimentacao.getEstacionamento().getId());
            if (movimentacao.getPlacaCarro() != null)
                psSalvar.setString(5, movimentacao.getPlacaCarro());
            else
                psSalvar.setString(5, null);
// Todo: dúvida, oq fazer quando possuem campos que podem ser nulos, está correto os ifs aqui?
            int linhasAfetadas = psSalvar.executeUpdate();
            ResultSet generatedKeys = psSalvar.getGeneratedKeys();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ADICIONAR MOVIMENTACAO %s%n", movimentacao.getDataHoraEntrada());
            else {
                if(generatedKeys.next())
                    movimentacao.setId(generatedKeys.getInt(1));
            }
            psSalvar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CADASTRAR MOVIMENTACAO");
        }
        return movimentacao;
    }

    @Override
    public Movimentacao atualizar(Movimentacao movimentacao) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psAlterar = conn.prepareStatement(
                    "UPDATE movimentacao " +
                            "SET dataHoraEntrada = ? AND " +
                            "dataHoraSaida = ? AND " +
                            "id_cliente = ? AND " +
                            "id_estacionamento = ? AND " +
                            "placaCarro = ? " +
                            "WHERE id = ? "
            );
            // TODO: Dúvida, como montar a dataHora para salvar?
            // ERROR: column "datahoraentrada" is of type timestamp without time zone but expression is of type boolean
            //  Dica: You will need to rewrite or cast the expression.
            psAlterar.setTimestamp(1, Timestamp.valueOf(movimentacao.getDataHoraEntrada()));
            psAlterar.setTimestamp(2, Timestamp.valueOf(movimentacao.getDataHoraSaida()));
            psAlterar.setInt(3, movimentacao.getCliente().getId());
            psAlterar.setInt(4, movimentacao.getEstacionamento().getId());
            psAlterar.setString(5, movimentacao.getPlacaCarro());
            psAlterar.setInt(6, movimentacao.getId());

            int linhasAfetadas = psAlterar.executeUpdate();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ATUALIZAR MOVIMENTACAO %s%n", movimentacao.getDataHoraEntrada());

            psAlterar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ATUALIZAR MOVIMENTACAO");
        }
        return movimentacao;
    }

    @Override
    public boolean remover(Movimentacao movimentacao) {
        return remover(movimentacao.getId());
    }

    @Override
    public boolean remover(int i) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psExcluir = conn.prepareStatement("" +
                    "DELETE FROM movimentacao WHERE id=?"
            );
            psExcluir.setInt(1, i);
            int linhasAfetadas = psExcluir.executeUpdate();
            psExcluir.close();
            conn.close();

            return linhasAfetadas == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO EXCLUIR MOVIMENTACAO");
        }
        return false;
    }

    private void findAndBuild(Connection conn, List<Movimentacao> retorno, PreparedStatement psBuscar) throws SQLException {
        psBuscar.executeQuery();
        ResultSet resultSet = psBuscar.getResultSet();
        while (resultSet.next()) {
            Movimentacao movimentacao = Movimentacao.builder()
                    .dataHoraEntrada(LocalDateTime.parse(resultSet.getString(2)))
                    .dataHoraSaida(LocalDateTime.parse(resultSet.getString(3)))
                    .placaCarro(resultSet.getString(5))
                    .build();
            movimentacao.setId(resultSet.getInt(1));
            retorno.add(movimentacao);
        }
        psBuscar.close();
        conn.close();
    }

//    public Double buscarMediaPorAlunoEDisciplina(int alunoId, int disciplinaId) {
//        Connection conn = ConnectDataBase.createConnections();
//        Double retorno = null;
//        try {
//            PreparedStatement psBuscar = conn.prepareStatement(
//                    "SELECT sum(nota)/count(a.*) " +
//                            "FROM avaliacao a " +
//                            "WHERE a.id_aluno = ? " +
//                            "and a.id_disciplina = ?;"
//            );
//            psBuscar.setInt(1, alunoId);
//            psBuscar.setInt(2, disciplinaId);
//            psBuscar.executeQuery();
//            ResultSet resultSet = psBuscar.getResultSet();
//            resultSet.next();
//            retorno = resultSet.getDouble(1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return retorno;
//    }
}
