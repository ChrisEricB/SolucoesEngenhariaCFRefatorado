package repository.jdbc;

import config.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Auditoria;
import repository.AuditoriaRepository;
import repository.RepositoryException;

public class AuditoriaRepositoryJdbc
        implements AuditoriaRepository {

    @Override
    public Auditoria salvar(Auditoria auditoria) {
        String sql = """
                INSERT INTO auditoria
                (id_projeto, tipo, data_agendada, data_realizacao,
                 id_auditor_responsavel, resultado, relatorio_path)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            preencherParametros(comando, auditoria);

            comando.executeUpdate();

            try (ResultSet chaves =
                         comando.getGeneratedKeys()) {

                if (chaves.next()) {
                    auditoria.setIdAuditoria(
                            chaves.getInt(1)
                    );
                }
            }

            return auditoria;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível salvar a auditoria.",
                    erro
            );
        }
    }

    @Override
    public List<Auditoria> listarTodos() {
        String sql = """
                SELECT id_auditoria,
                       id_projeto,
                       tipo,
                       data_agendada,
                       data_realizacao,
                       id_auditor_responsavel,
                       resultado
                FROM auditoria
                ORDER BY data_agendada DESC
                """;

        List<Auditoria> auditorias = new ArrayList<>();

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     comando.executeQuery()) {

            while (resultado.next()) {
                auditorias.add(
                        mapearAuditoria(resultado)
                );
            }

            return auditorias;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar as auditorias.",
                    erro
            );
        }
    }

    @Override
    public List<Auditoria> listarPorProjeto(
            int idProjeto) {

        String sql = """
                SELECT id_auditoria,
                       id_projeto,
                       tipo,
                       data_agendada,
                       data_realizacao,
                       id_auditor_responsavel,
                       resultado
                FROM auditoria
                WHERE id_projeto = ?
                ORDER BY data_agendada DESC
                """;

        List<Auditoria> auditorias = new ArrayList<>();

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(1, idProjeto);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {
                    auditorias.add(
                            mapearAuditoria(resultado)
                    );
                }
            }

            return auditorias;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar as auditorias do projeto.",
                    erro
            );
        }
    }

    @Override
    public Optional<Auditoria> buscarPorId(
            int idAuditoria) {

        String sql = """
                SELECT id_auditoria,
                       id_projeto,
                       tipo,
                       data_agendada,
                       data_realizacao,
                       id_auditor_responsavel,
                       resultado
                FROM auditoria
                WHERE id_auditoria = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(1, idAuditoria);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {
                    return Optional.of(
                            mapearAuditoria(resultado)
                    );
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível buscar a auditoria.",
                    erro
            );
        }
    }

    @Override
    public boolean atualizar(Auditoria auditoria) {
        String sql = """
                UPDATE auditoria
                SET id_projeto = ?,
                    tipo = ?,
                    data_agendada = ?,
                    data_realizacao = ?,
                    id_auditor_responsavel = ?,
                    resultado = ?
                WHERE id_auditoria = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            preencherParametrosBasicos(
                    comando,
                    auditoria
            );

            comando.setInt(
                    7,
                    auditoria.getIdAuditoria()
            );

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível atualizar a auditoria.",
                    erro
            );
        }
    }

    @Override
    public boolean excluir(int idAuditoria) {
        String sql = """
                DELETE FROM auditoria
                WHERE id_auditoria = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(1, idAuditoria);

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível excluir a auditoria.",
                    erro
            );
        }
    }

    private void preencherParametros(
            PreparedStatement comando,
            Auditoria auditoria) throws SQLException {

        preencherParametrosBasicos(
                comando,
                auditoria
        );

        comando.setNull(7, Types.VARCHAR);
    }

    private void preencherParametrosBasicos(
            PreparedStatement comando,
            Auditoria auditoria) throws SQLException {

        comando.setInt(
                1,
                auditoria.getIdProjeto()
        );

        comando.setString(
                2,
                auditoria.getTipo()
        );

        comando.setTimestamp(
                3,
                Timestamp.valueOf(
                        auditoria.getDataAgendada()
                )
        );

        if (auditoria.getDataRealizacao() == null) {
            comando.setNull(4, Types.TIMESTAMP);
        } else {
            comando.setTimestamp(
                    4,
                    Timestamp.valueOf(
                            auditoria.getDataRealizacao()
                    )
            );
        }

        comando.setInt(
                5,
                auditoria.getIdAuditorResponsavel()
        );

        if (auditoria.getResultado() == null
                || auditoria.getResultado().isBlank()) {

            comando.setNull(6, Types.VARCHAR);

        } else {
            comando.setString(
                    6,
                    auditoria.getResultado()
            );
        }
    }

    private Auditoria mapearAuditoria(
            ResultSet resultado) throws SQLException {

        Auditoria auditoria = new Auditoria();

        auditoria.setIdAuditoria(
                resultado.getInt("id_auditoria")
        );

        auditoria.setIdProjeto(
                resultado.getInt("id_projeto")
        );

        auditoria.setTipo(
                resultado.getString("tipo")
        );

        Timestamp dataAgendada =
                resultado.getTimestamp(
                        "data_agendada"
                );

        if (dataAgendada != null) {
            auditoria.setDataAgendada(
                    dataAgendada.toLocalDateTime()
            );
        }

        Timestamp dataRealizacao =
                resultado.getTimestamp(
                        "data_realizacao"
                );

        if (dataRealizacao != null) {
            auditoria.setDataRealizacao(
                    dataRealizacao.toLocalDateTime()
            );
        }

        auditoria.setIdAuditorResponsavel(
                resultado.getInt(
                        "id_auditor_responsavel"
                )
        );

        auditoria.setResultado(
                resultado.getString("resultado")
        );

        return auditoria;
    }
}
