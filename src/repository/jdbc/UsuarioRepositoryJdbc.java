package repository.jdbc;

import config.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Usuario;
import repository.RepositoryException;
import repository.UsuarioRepository;

public class UsuarioRepositoryJdbc implements UsuarioRepository {

    @Override
    public Usuario salvar(Usuario usuario) {
        String sql = """
                INSERT INTO usuario
                (nome, email, senha, tipo, ativo)
                VALUES (?, ?, SHA2(?, 256), ?, ?)
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getTipo());
            comando.setBoolean(5, usuario.isAtivo());

            comando.executeUpdate();

            try (ResultSet chaves = comando.getGeneratedKeys()) {
                if (chaves.next()) {
                    usuario.setIdUsuario(chaves.getInt(1));
                }
            }

            usuario.setSenha(null);

            return usuario;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível salvar o usuário.", erro);
        }
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = """
                SELECT id_usuario, nome, email, tipo, ativo,
                       data_criacao, data_atualizacao
                FROM usuario
                ORDER BY id_usuario
                """;

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            while (resultado.next()) {
                usuarios.add(mapearUsuario(resultado));
            }

            return usuarios;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível listar os usuários.", erro);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int idUsuario) {
        String sql = """
                SELECT id_usuario, nome, email, tipo, ativo,
                       data_criacao, data_atualizacao
                FROM usuario
                WHERE id_usuario = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idUsuario);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearUsuario(resultado));
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível buscar o usuário.", erro);
        }
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = """
                SELECT id_usuario, nome, email, tipo, ativo,
                       data_criacao, data_atualizacao
                FROM usuario
                WHERE email = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, email);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearUsuario(resultado));
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível buscar o usuário pelo e-mail.", erro);
        }
    }

    @Override
    public Optional<Usuario> autenticar(String email, String senha) {
        String sql = """
                SELECT id_usuario, nome, email, tipo, ativo,
                       data_criacao, data_atualizacao
                FROM usuario
                WHERE email = ?
                  AND senha = SHA2(?, 256)
                  AND ativo = TRUE
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, email);
            comando.setString(2, senha);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(mapearUsuario(resultado));
                }
            }

            return Optional.empty();

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível autenticar o usuário.", erro);
        }
    }

    @Override
    public boolean atualizar(Usuario usuario) {
        String sql = """
                UPDATE usuario
                SET nome = ?,
                    email = ?,
                    senha = SHA2(?, 256),
                    tipo = ?,
                    ativo = ?,
                    data_atualizacao = NOW()
                WHERE id_usuario = ?
                """;

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getSenha());
            comando.setString(4, usuario.getTipo());
            comando.setBoolean(5, usuario.isAtivo());
            comando.setInt(6, usuario.getIdUsuario());

            boolean atualizado = comando.executeUpdate() > 0;

            usuario.setSenha(null);

            return atualizado;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível atualizar o usuário.", erro);
        }
    }

    @Override
    public boolean excluir(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, idUsuario);

            return comando.executeUpdate() > 0;

        } catch (SQLException erro) {
            throw new RepositoryException(
                    "Não foi possível excluir o usuário.", erro);
        }
    }

    private Usuario mapearUsuario(ResultSet resultado)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setIdUsuario(
                resultado.getInt("id_usuario")
        );

        usuario.setNome(
                resultado.getString("nome")
        );

        usuario.setEmail(
                resultado.getString("email")
        );

        usuario.setTipo(
                resultado.getString("tipo")
        );

        usuario.setAtivo(
                resultado.getBoolean("ativo")
        );

        Timestamp dataCriacao =
                resultado.getTimestamp("data_criacao");

        if (dataCriacao != null) {
            usuario.setDataCriacao(
                    dataCriacao.toLocalDateTime()
            );
        }

        Timestamp dataAtualizacao =
                resultado.getTimestamp("data_atualizacao");

        if (dataAtualizacao != null) {
            usuario.setDataAtualizacao(
                    dataAtualizacao.toLocalDateTime()
            );
        }

        return usuario;
    }
}