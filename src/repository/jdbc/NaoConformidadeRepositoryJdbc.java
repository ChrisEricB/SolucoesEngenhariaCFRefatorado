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
import model.NaoConformidade;
import repository.NaoConformidadeRepository;
import repository.RepositoryException;

public class NaoConformidadeRepositoryJdbc
        implements NaoConformidadeRepository {

    @Override
    public NaoConformidade salvar(
            NaoConformidade naoConformidade) {

        String sql = """
                INSERT INTO naoconformidade
                (id_auditoria,
                 id_projeto,
                 descricao,
                 causa_raiz,
                 gravidade,
                 id_responsavel_correcao,
                 prazo_correcao,
                 status,
                 data_correcao)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS)) {

            preencherParametros(
                    comando,
                    naoConformidade
            );

            comando.executeUpdate();

            try (ResultSet chaves =
                         comando.getGeneratedKeys()) {

                if (chaves.next()) {
                    naoConformidade.setIdNaoConformidade(
                            chaves.getInt(1)
                    );
                }
            }

            return naoConformidade;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível salvar a não conformidade.",
                    erro
            );
        }
    }

    @Override
    public List<NaoConformidade> listarTodos() {
        String sql = """
                SELECT id_nao_conformidade,
                       id_auditoria,
                       id_projeto,
                       descricao,
                       causa_raiz,
                       gravidade,
                       data_registro,
                       id_responsavel_correcao,
                       prazo_correcao,
                       status,
                       data_correcao
                FROM naoconformidade
                ORDER BY prazo_correcao
                """;

        List<NaoConformidade> lista =
                new ArrayList<>();

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql);
             ResultSet resultado =
                     comando.executeQuery()) {

            while (resultado.next()) {
                lista.add(
                        mapearNaoConformidade(resultado)
                );
            }

            return lista;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar as não conformidades.",
                    erro
            );
        }
    }

    @Override
    public List<NaoConformidade> listarPorProjeto(
            int idProjeto) {

        String sql = """
                SELECT id_nao_conformidade,
                       id_auditoria,
                       id_projeto,
                       descricao,
                       causa_raiz,
                       gravidade,
                       data_registro,
                       id_responsavel_correcao,
                       prazo_correcao,
                       status,
                       data_correcao
                FROM naoconformidade
                WHERE id_projeto = ?
                ORDER BY prazo_correcao
                """;

        List<NaoConformidade> lista =
                new ArrayList<>();

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(1, idProjeto);

            try (ResultSet resultado =
                         comando.executeQuery()) {

                while (resultado.next()) {
                    lista.add(
                            mapearNaoConformidade(resultado)
                    );
                }
            }

            return lista;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar as não conformidades do projeto.",
                    erro
            );
        }
    }

    @Override
    public Optional<NaoConformidade> buscarPorId(
            int idNaoConformidade) {

        String sql = """
                SELECT id_nao_conformidade,
                       id_auditoria,
                       id_projeto,
                       descricao,
                       causa_raiz,
                       gravidade,
                       data_registro,
                       id_responsavel_correcao,
                       prazo_correcao,
                       status,
                       data_correcao
                FROM naoconformidade
                WHERE id_nao_conformidade = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(
                    1,
                    idNaoConformidade
            );

            try (ResultSet resultado =
                         comando.executeQuery()) {

                if (resultado.next()) {
                    return Optional.of(
                            mapearNaoConformidade(resultado)
                    );
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível buscar a não conformidade.",
                    erro
            );
        }
    }

    @Override
    public boolean atualizar(
            NaoConformidade naoConformidade) {

        String sql = """
                UPDATE naoconformidade
                SET id_auditoria = ?,
                    id_projeto = ?,
                    descricao = ?,
                    causa_raiz = ?,
                    gravidade = ?,
                    id_responsavel_correcao = ?,
                    prazo_correcao = ?,
                    status = ?,
                    data_correcao = ?
                WHERE id_nao_conformidade = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            preencherParametros(
                    comando,
                    naoConformidade
            );

            comando.setInt(
                    10,
                    naoConformidade.getIdNaoConformidade()
            );

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível atualizar a não conformidade.",
                    erro
            );
        }
    }

    @Override
    public boolean excluir(
            int idNaoConformidade) {

        String sql = """
                DELETE FROM naoconformidade
                WHERE id_nao_conformidade = ?
                """;

        try (Connection conexao =
                     ConnectionFactory.getConnection();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setInt(
                    1,
                    idNaoConformidade
            );

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível excluir a não conformidade.",
                    erro
            );
        }
    }

    private void preencherParametros(
            PreparedStatement comando,
            NaoConformidade naoConformidade)
            throws SQLException {

        if (naoConformidade.getIdAuditoria() == null) {
            comando.setNull(1, Types.INTEGER);
        } else {
            comando.setInt(
                    1,
                    naoConformidade.getIdAuditoria()
            );
        }

        comando.setInt(
                2,
                naoConformidade.getIdProjeto()
        );

        comando.setString(
                3,
                naoConformidade.getDescricao()
        );

        if (naoConformidade.getCausaRaiz() == null
                || naoConformidade.getCausaRaiz().isBlank()) {

            comando.setNull(4, Types.VARCHAR);

        } else {
            comando.setString(
                    4,
                    naoConformidade.getCausaRaiz()
            );
        }

        comando.setString(
                5,
                naoConformidade.getGravidade()
        );

        comando.setInt(
                6,
                naoConformidade.getIdResponsavelCorrecao()
        );

        comando.setDate(
                7,
                Date.valueOf(
                        naoConformidade.getPrazoCorrecao()
                )
        );

        String status = naoConformidade.getStatus();

        if (status == null || status.isBlank()) {
            status = "Registrada";
        }

        comando.setString(8, status);

        if (naoConformidade.getDataCorrecao() == null) {
            comando.setNull(9, Types.TIMESTAMP);
        } else {
            comando.setTimestamp(
                    9,
                    Timestamp.valueOf(
                            naoConformidade.getDataCorrecao()
                    )
            );
        }
    }

    private NaoConformidade mapearNaoConformidade(
            ResultSet resultado) throws SQLException {

        NaoConformidade naoConformidade =
                new NaoConformidade();

        naoConformidade.setIdNaoConformidade(
                resultado.getInt(
                        "id_nao_conformidade"
                )
        );

        int idAuditoria =
                resultado.getInt("id_auditoria");

        if (resultado.wasNull()) {
            naoConformidade.setIdAuditoria(null);
        } else {
            naoConformidade.setIdAuditoria(idAuditoria);
        }

        naoConformidade.setIdProjeto(
                resultado.getInt("id_projeto")
        );

        naoConformidade.setDescricao(
                resultado.getString("descricao")
        );

        naoConformidade.setCausaRaiz(
                resultado.getString("causa_raiz")
        );

        naoConformidade.setGravidade(
                resultado.getString("gravidade")
        );

        Timestamp dataRegistro =
                resultado.getTimestamp(
                        "data_registro"
                );

        if (dataRegistro != null) {
            naoConformidade.setDataRegistro(
                    dataRegistro.toLocalDateTime()
            );
        }

        naoConformidade.setIdResponsavelCorrecao(
                resultado.getInt(
                        "id_responsavel_correcao"
                )
        );

        Date prazoCorrecao =
                resultado.getDate(
                        "prazo_correcao"
                );

        if (prazoCorrecao != null) {
            naoConformidade.setPrazoCorrecao(
                    prazoCorrecao.toLocalDate()
            );
        }

        naoConformidade.setStatus(
                resultado.getString("status")
        );

        Timestamp dataCorrecao =
                resultado.getTimestamp(
                        "data_correcao"
                );

        if (dataCorrecao != null) {
            naoConformidade.setDataCorrecao(
                    dataCorrecao.toLocalDateTime()
            );
        }

        return naoConformidade;
    }
}