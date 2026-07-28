package repository.jdbc;

import config.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Projeto;
import repository.ProjetoRepository;
import repository.RepositoryException;

public class ProjetoRepositoryJdbc implements ProjetoRepository {

    @Override
    public Projeto salvar(Projeto projeto) {
        String sql = """
                INSERT INTO projeto
                (nome, descricao, id_cliente, data_inicio, data_termino,
                 orcamento, status, id_responsavel)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            preencherParametros(comando, projeto);

            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (chaves.next()) {
                    projeto.setIdProjeto(chaves.getInt(1));
                }
            }

            return projeto;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível salvar o projeto.", erro);
        }
    }

    @Override
    public List<Projeto> listarTodos() {
        String sql = """
                SELECT id_projeto, nome, descricao, id_cliente,
                       data_inicio, data_termino, orcamento, status,
                       id_responsavel, data_criacao, data_atualizacao
                FROM projeto
                ORDER BY id_projeto DESC
                """;

        List<Projeto> projetos = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                projetos.add(mapearProjeto(resultado));
            }

            return projetos;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar os projetos.", erro);
        }
    }

    @Override
    public Optional<Projeto> buscarPorId(int idProjeto) {
        String sql = """
                SELECT id_projeto, nome, descricao, id_cliente,
                       data_inicio, data_termino, orcamento, status,
                       id_responsavel, data_criacao, data_atualizacao
                FROM projeto
                WHERE id_projeto = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idProjeto);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearProjeto(resultado));
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível buscar o projeto.", erro);
        }
    }

    @Override
    public boolean atualizar(Projeto projeto) {
        String sql = """
                UPDATE projeto
                SET nome = ?,
                    descricao = ?,
                    id_cliente = ?,
                    data_inicio = ?,
                    data_termino = ?,
                    orcamento = ?,
                    status = ?,
                    id_responsavel = ?,
                    data_atualizacao = NOW()
                WHERE id_projeto = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            preencherParametros(comando, projeto);
            comando.setInt(9, projeto.getIdProjeto());

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível atualizar o projeto.", erro);
        }
    }

    @Override
    public boolean excluir(int idProjeto) {
        String sql = "DELETE FROM projeto WHERE id_projeto = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idProjeto);

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível excluir o projeto.", erro);
        }
    }

    private void preencherParametros(
            PreparedStatement comando,
            Projeto projeto) throws SQLException {

        comando.setString(1, projeto.getNome());
        comando.setString(2, projeto.getDescricao());

        if (projeto.getIdCliente() == null) {
            comando.setNull(3, Types.INTEGER);
        } else {
            comando.setInt(3, projeto.getIdCliente());
        }

        comando.setDate(
                4,
                Date.valueOf(projeto.getDataInicio())
        );

        if (projeto.getDataTermino() == null) {
            comando.setNull(5, Types.DATE);
        } else {
            comando.setDate(
                    5,
                    Date.valueOf(projeto.getDataTermino())
            );
        }

        if (projeto.getOrcamento() == null) {
            comando.setNull(6, Types.DECIMAL);
        } else {
            comando.setBigDecimal(
                    6,
                    projeto.getOrcamento()
            );
        }

        comando.setString(7, projeto.getStatus());
        comando.setInt(8, projeto.getIdResponsavel());
    }

    private Projeto mapearProjeto(ResultSet resultado)
            throws SQLException {

        Projeto projeto = new Projeto();

        projeto.setIdProjeto(
                resultado.getInt("id_projeto")
        );

        projeto.setNome(
                resultado.getString("nome")
        );

        projeto.setDescricao(
                resultado.getString("descricao")
        );

        int idCliente = resultado.getInt("id_cliente");

        if (resultado.wasNull()) {
            projeto.setIdCliente(null);
        } else {
            projeto.setIdCliente(idCliente);
        }

        Date dataInicio = resultado.getDate("data_inicio");

        if (dataInicio != null) {
            projeto.setDataInicio(dataInicio.toLocalDate());
        }

        Date dataTermino = resultado.getDate("data_termino");

        if (dataTermino != null) {
            projeto.setDataTermino(dataTermino.toLocalDate());
        }

        projeto.setOrcamento(
                resultado.getBigDecimal("orcamento")
        );

        projeto.setStatus(
                resultado.getString("status")
        );

        projeto.setIdResponsavel(
                resultado.getInt("id_responsavel")
        );

        Timestamp dataCriacao =
                resultado.getTimestamp("data_criacao");

        if (dataCriacao != null) {
            projeto.setDataCriacao(
                    dataCriacao.toLocalDateTime()
            );
        }

        Timestamp dataAtualizacao =
                resultado.getTimestamp("data_atualizacao");

        if (dataAtualizacao != null) {
            projeto.setDataAtualizacao(
                    dataAtualizacao.toLocalDateTime()
            );
        }

        return projeto;
    }
}