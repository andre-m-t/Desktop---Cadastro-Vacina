package poov.controledoacaosangue.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import poov.controledoacaosangue.DAO.core.GenericJDBCDAO;
import poov.controledoacaosangue.Model.Doacao;
import poov.controledoacaosangue.Model.Doador;
import poov.controledoacaosangue.Model.RH;
import poov.controledoacaosangue.Model.Situacao;
import poov.controledoacaosangue.Model.TipoSanguineo;

public class DoacaoDAO extends GenericJDBCDAO<Doacao, Long> {

    private static final String FIND_ALL_QUERY = "SELECT dc.codigo AS codigoDoacao, dc.data, dc.hora, dc.volume, dc.codigo_doador, dc.situacao AS situacaoDoacao, dd.codigo as codigoDoador, dd.nome, dd.cpf, dd.contato, dd.tipo_sanguineo, dd.rh, dd.tipo_rh_corretos, dd.situacao AS situacaoDoador FROM doacao AS dc INNER JOIN doador AS dd ON dc.codigo_doador = dd.codigo WHERE dc.situacao = 'ATIVO' ";
    private static final String FIND_BY_KEY_QUERY = FIND_ALL_QUERY + "AND codigo=? ";
    private static final String UPDATE_QUERY = "UPDATE doacao SET data=?, hora=?, volume=?, codigo_doador=?, situacao=? WHERE codigo=?";
    private static final String CREATE_QUERY = "INSERT INTO doacao (data, hora, volume, codigo_doador, situacao) VALUES (?, ?, ?, ?, ?)";
    private static final String REMOVE_QUERY = "DELETE FROM doacao WHERE codigo=?";

    public DoacaoDAO(Connection conexao) {
        super(conexao);
    }

    @Override
    protected Doacao toEntity(ResultSet resultSet) throws SQLException {
        Doacao doacao = new Doacao();
        doacao.setCodigo(resultSet.getLong("codigo"));
        doacao.setData(resultSet.getDate("data").toLocalDate());
        doacao.setHora(resultSet.getTime("hora").toLocalTime());
        doacao.setVolume(resultSet.getDouble("volume"));
        doacao.setDoador(new Doador());
        doacao.getDoador().setCodigo(resultSet.getLong("codigo_doador"));

        if (resultSet.getString("situacao").equals("ATIVO")) {
            doacao.setSituacao(Situacao.ATIVO);
        } else {
            doacao.setSituacao(Situacao.INATIVO);
        }

        return doacao;
    }

    @Override
    protected void addParameters(PreparedStatement resultSet, Doacao entity) throws SQLException {
        resultSet.setDate(1, java.sql.Date.valueOf(entity.getData()));
        resultSet.setTime(2, java.sql.Time.valueOf(entity.getHora()));
        resultSet.setDouble(3, entity.getVolume());
        resultSet.setLong(4, entity.getDoador().getCodigo());
        resultSet.setString(5, entity.getSituacao().toString());
        if (entity.getCodigo() != null) {
            resultSet.setLong(6, entity.getCodigo());
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

    @Override
    protected void setKeyInStatementFromEntity(PreparedStatement statement, Doacao entity) throws SQLException {
        statement.setLong(1, entity.getCodigo());
    }

    @Override
    protected void setKeyInStatement(PreparedStatement statement, Long key) throws SQLException {
        statement.setLong(1, key);
    }

    @Override
    protected void setKeyInEntity(ResultSet rs, Doacao entity) throws SQLException {
        entity.setCodigo(rs.getLong(1));
    }

    public List<Doacao> buscarDoacoesAtivas() throws SQLException {
        String query = "SELECT * FROM doacao  WHERE situacao = 'ATIVO'";
        try(PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<Doacao> buscarDoacoesDeDoador(Doador novo) throws SQLException {
        String query = "SELECT * FROM doacao WHERE codigo_doador = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, novo.getCodigo());
            ResultSet resultSet = statement.executeQuery();
            return toEntityList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        }
    }

    public String novaDoacao(Doacao novaDoacao) {
        String msg = "";
        try {
            // Preparando a consulta de inserção
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            // Definindo os parâmetros da consulta
            addParameters(preparedStatement, novaDoacao);
            // Executando a consulta
            preparedStatement.executeUpdate();
            // Fechando o prepared statement
            preparedStatement.close();
            msg = "Sucesso na criacao";
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratamento de erro, como registrar o erro ou exibir mensagem ao usuário
            msg = "Problema";
        }
        return msg;
    }
    

}
