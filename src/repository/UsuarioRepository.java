package repository;

import java.util.List;
import java.util.Optional;
import model.Usuario;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);

    List<Usuario> listarTodos();

    Optional<Usuario> buscarPorId(int idUsuario);

    Optional<Usuario> buscarPorEmail(String email);

    Optional<Usuario> autenticar(String email, String senha);

    boolean atualizar(Usuario usuario);

    boolean excluir(int idUsuario);
}