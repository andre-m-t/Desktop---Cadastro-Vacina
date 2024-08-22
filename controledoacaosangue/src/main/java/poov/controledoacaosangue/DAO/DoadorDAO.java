package poov.controledoacaosangue.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poov.controledoacaosangue.DAO.core.GenericJDBCDAO;
import poov.controledoacaosangue.Model.Doador;
import poov.controledoacaosangue.Model.RH;
import poov.controledoacaosangue.Model.Situacao;
import poov.controledoacaosangue.Model.TipoSanguineo;

public class DoadorDAO extends GenericJDBCDAO<Doador, Long> {

    private static final String FIND_ALL_QUERY = "SELECT codigo, nome, cpf, contato, tipo_sanguineo, rh, tipo_rh_corretos, situacao FROM doador WHERE situacao = 'ATIVO'";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + " AND codigo=?";
    private static final String UPDATE_QUERY = "UPDATE doador SET nome=?, cpf=?, contato=?, tipo_sanguineo=?, rh=?, tipo_rh_corretos=?, situacao=? WHERE codigo=?";
    private static final String CREATE_QUERY = "INSERT INTO doador (nome, cpf, contato, tipo_sanguineo, rh, tipo_rh_corretos, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM doador WHERE codigo=?";

    public DoadorDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Doador entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Doador entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    @Override
    protected Doador toEntity(ResultSet resultSet) throws SQLException {
        Doador doador = new Doador();
        doador.setCodigo(resultSet.getLong("codigo"));
        doador.setNome(resultSet.getString("nome"));
        doador.setCpf(resultSet.getString("cpf"));
        doador.setContato(resultSet.getString("contato"));

        switch (resultSet.getString("tipo_sanguineo")) {
            case "A":
                doador.setTipoSanguineo(TipoSanguineo.A);
                break;
            case "B":
                doador.setTipoSanguineo(TipoSanguineo.B);
                break;
            case "AB":
                doador.setTipoSanguineo(TipoSanguineo.AB);
                break;
            case "O":
                doador.setTipoSanguineo(TipoSanguineo.O);
                break;
            case "DESCONHECIDO":
                doador.setTipoSanguineo(TipoSanguineo.DESCONHECIDO);
                break;
        }

        switch (resultSet.getString("rh")) {
            case "POSITIVO":
                doador.setRh(RH.POSITIVO);
                break;
            case "NEGATIVO":
                doador.setRh(RH.NEGATIVO);
                break;
            case "DESCONHECIDO":
                doador.setRh(RH.DESCONHECIDO);
                break;
        }

        doador.setTipoERhCorretos(resultSet.getBoolean("tipo_rh_corretos"));

        if (resultSet.getString("situacao").equals("ATIVO")) {
            doador.setSituacao(Situacao.ATIVO);
        } else {
            doador.setSituacao(Situacao.INATIVO);
        }
        return doador;
    }

    @Override
    protected void addParameters(PreparedStatement statement, Doador entity) throws SQLException {
        statement.setString(1, entity.getNome().toString());
        statement.setString(2, entity.getCpf().toString());
        statement.setString(3, entity.getContato().toString());
        statement.setString(4, entity.getTipoSanguineo().toString());
        statement.setString(5, entity.getRh().toString());
        statement.setBoolean(6, entity.isTipoERhCorretos());
        statement.setString(7, entity.getSituacao().toString());
        if (entity.getCodigo() != null) {
            statement.setLong(8, entity.getCodigo());
        }
    }

    @Override
    protected String findByKeyQuery() {
        return FIND_BY_KEY_QUERY;
    }

    @Override
    protected String findAllQuery() {
        return FIND_ALL_QUERY;
    }

    @Override
    protected String updateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    protected String createQuery() {
        return CREATE_QUERY;
    }

    @Override
    protected String removeQuery() {
        return REMOVE_QUERY;
    }

    public String cadastrarDoador(Doador atual){
        int parametro = 1;
        String mensagem = "";
        String query = "INSERT INTO doador (nome, cpf, contato, tipo_sanguineo, rh, tipo_rh_corretos, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (atual.getNome() != null) {
                statement.setString(parametro++,atual.getNome());
            }
            if (atual.getCpf() != null) {
                statement.setString(parametro++,atual.getCpf());
            }
            if (atual.getContato() != null) {
                statement.setString(parametro++, atual.getContato());
            }
            if (atual.getTipoSanguineo() != null) {
                statement.setString(parametro++, atual.getTipoSanguineo().toString());
            }
            if (atual.getRh() != null) {
                statement.setString(parametro++, atual.getRh().toString());
            }
            statement.setBoolean(parametro++, false);
            statement.setString(parametro++, "ATIVO");
            
            System.out.println(statement.toString());

            statement.execute();

            mensagem += "Cadastro realizado com sucesso!";

        } catch (SQLException e) {
            showSQLException(e);
            mensagem += "Erro no cadastro de usuário!";
        }

        return mensagem;
    }
    public List<Doador> buscarTodos(){
        String query = "SELECT * FROM doador  WHERE situacao = 'ATIVO'";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Doador> buscarPorFiltro(Doador filtro) {
        int parametro = 1;
        String query = "SELECT * FROM doador WHERE situacao = 'ATIVO' ";
        System.out.println(filtro);
        if (filtro.getNome() != null) {
            query += " and LOWER(nome) like ?";
        }
        if (filtro.getCpf() != null) {
            query += " and cpf like ?";
        }
        if (filtro.getCodigo() != null) {
            query += " and codigo = ?";
        }
        if (filtro.getContato() != null) {
            query += " and contato like ? ";
        }
        if (filtro.getTipoSanguineo() != null) {
            query += " and tipo_sanguineo = ?";
        }
        if (filtro.getRh() != null) {
            query += " and rh = ?";
        }
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (filtro.getNome() != null) {
                statement.setString(parametro++, "%" + filtro.getNome().toLowerCase() + "%");
            }
            if (filtro.getCpf() != null) {
                statement.setString(parametro++, "%" + filtro.getCpf() + "%");
            }
            if (filtro.getCodigo() != null) {
                statement.setLong(parametro++, filtro.getCodigo());
            }
            if (filtro.getContato() != null) {
                statement.setString(parametro++, "%" + filtro.getContato() + "%");
            }
            if (filtro.getTipoSanguineo() != null) {
                statement.setString(parametro++, filtro.getTipoSanguineo().toString());
            }
            if (filtro.getRh() != null) {
                statement.setString(parametro++, filtro.getRh().toString());
            }
            System.out.println(statement.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                return toEntityList(resultSet);
            }
        } catch (SQLException e) {
            showSQLException(e);
        }

        return new ArrayList<>();
    }

    public String alterarDoador(Doador doadorAtualizado) {
        String query = "UPDATE doador SET nome=?, cpf=?, contato=?, tipo_sanguineo=?, rh=?, tipo_rh_corretos=? WHERE codigo=?";
        String msg = "";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Define os parâmetros
            statement.setString(1, doadorAtualizado.getNome());
            statement.setString(2, doadorAtualizado.getCpf());
            statement.setString(3, doadorAtualizado.getContato());
            statement.setString(4, doadorAtualizado.getTipoSanguineo().toString());
            statement.setString(5, doadorAtualizado.getRh().toString());
            statement.setBoolean(6, doadorAtualizado.getIsTipoCorreto());
            statement.setLong(7, doadorAtualizado.getCodigo()); // Supondo que 'codigo' é um inteiro
    
            // Executa a atualização
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                msg = doadorAtualizado.getNome() + " atualizado com sucesso.";
            } else {
                msg = "Não foi possível atualizar o doador.";
            }
        } catch (SQLException e) {
            showSQLException(e);
            msg = "Erro ao atualizar o doador!";
        }
        return msg;
    }
    public String desativarDoador(Doador doador) {
        String query = "UPDATE doador SET situacao = 'INATIVO' WHERE codigo = ?";
        String msg = "";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setKeyInStatement(statement, doador.getCodigo());
            statement.executeUpdate();
            msg = doador.getNome() + " Removido com sucesso";
        } catch (SQLException e) {
            showSQLException(e);
            msg = "Problema ao remover doador!";
        }
        return msg;
    }

}
