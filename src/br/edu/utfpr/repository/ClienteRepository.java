package br.edu.utfpr.repository;

import br.edu.utfpr.database.ConnectDataBase;
import br.edu.utfpr.model.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.*;

public class ClienteRepository implements Repository<Cliente> {

    @Override
    public List<Cliente> buscarTodos() {
        Connection conn = ConnectDataBase.createConnections();
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM cliente"
            );
            findAndBuild(conn, retorno, psBuscar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        Connection conn = ConnectDataBase.createConnections();
        Cliente retorno = new Cliente();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT * FROM cliente WHERE id = ?"
            );
            psBuscar.setInt(1, idCliente);
            psBuscar.executeQuery();
            ResultSet resultSet = psBuscar.getResultSet();
            while (resultSet.next()) {
                retorno = Cliente.builder()
                        .nome(resultSet.getString(2))
                        .telefone(resultSet.getString(3))
                        .email(resultSet.getString(4))
                        .dataNascimento(LocalDate.parse(resultSet.getString(5)))
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
    public Cliente salvar(Cliente cliente) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psSalvar = conn.prepareStatement(
                    "INSERT INTO cliente(nome, telefone, email, dataNascimento) " +
                            "VALUES(?, ?, ?, ?)", RETURN_GENERATED_KEYS
            );
            psSalvar.setString(1, cliente.getNome());
            psSalvar.setString(2, cliente.getTelefone());
            psSalvar.setString(3, cliente.getEmail());
            psSalvar.setDate(4, Date.valueOf(cliente.getDataNascimento()));

            int linhasAfetadas = psSalvar.executeUpdate();
            ResultSet generatedKeys = psSalvar.getGeneratedKeys();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ADICIONAR CLIENTE %s%n", cliente.getNome().toUpperCase());
            else {
                if(generatedKeys.next())
                    cliente.setId(generatedKeys.getInt(1));
            }
            psSalvar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO CADASTRAR CLIENTE");
        }
        return cliente;
    }

    @Override
    public Cliente atualizar(Cliente cliente) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psAlterar = conn.prepareStatement(
                    "UPDATE cliente " +
                            "SET nome = ? AND " +
                            "telefone = ? AND " +
                            "email = ? AND " +
                            "dataNascimento = ? " +
                            "WHERE id = ? "
            );
            psAlterar.setString(1, cliente.getNome());
            psAlterar.setString(2, cliente.getTelefone());
            psAlterar.setString(3, cliente.getEmail());
            psAlterar.setDate(4, Date.valueOf(cliente.getDataNascimento()));
            psAlterar.setInt(5, cliente.getId());

            int linhasAfetadas = psAlterar.executeUpdate();

            if(linhasAfetadas == 0)
                System.out.printf("ERRO AO ATUALIZAR CLIENTE %s%n", cliente.getNome().toUpperCase());

            psAlterar.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO ATUALIZAR CLIENTE");
        }
        return cliente;
    }

    @Override
    public boolean remover(Cliente cliente) {
        return remover(cliente.getId());
    }

    @Override
    public boolean remover(int i) {
        Connection conn = ConnectDataBase.createConnections();
        try {
            PreparedStatement psExcluir = conn.prepareStatement("" +
                    "DELETE FROM cliente WHERE id=?"
            );
            psExcluir.setInt(1, i);
            int linhasAfetadas = psExcluir.executeUpdate();
            psExcluir.close();
            conn.close();

            return linhasAfetadas == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERRO AO EXCLUIR CLIENTE");
        }
        return false;
    }

    public List<Cliente> buscarPorDisciplina(int id) {
        Connection conn = ConnectDataBase.createConnections();
        List<Cliente> retorno = new ArrayList<>();
        try {
            PreparedStatement psBuscar = conn.prepareStatement(
                    "SELECT a.* FROM cliente a " +
                    "INNER JOIN aluno_disciplina d on d.id_disciplina = ?"
            );
            psBuscar.setInt(1, id);
            findAndBuild(conn, retorno, psBuscar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private void findAndBuild(Connection conn, List<Cliente> retorno, PreparedStatement psBuscar) throws SQLException {
        psBuscar.executeQuery();
        ResultSet resultSet = psBuscar.getResultSet();
        while (resultSet.next()) {
            Cliente cliente = Cliente.builder()
                    .nome(resultSet.getString(2))
                    .telefone(resultSet.getString(3))
                    .email(resultSet.getString(4))
                    .dataNascimento(LocalDate.parse(resultSet.getString(5)))
                    .build();
            cliente.setId(resultSet.getInt(1));
            retorno.add(cliente);
        }
        psBuscar.close();
        conn.close();
    }

}
