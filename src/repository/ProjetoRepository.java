package repository;

import java.util.List;
import java.util.Optional;
import model.Projeto;

public interface ProjetoRepository {

    Projeto salvar(Projeto projeto);

    List<Projeto> listarTodos();

    Optional<Projeto> buscarPorId(int idProjeto);

    boolean atualizar(Projeto projeto);

    boolean excluir(int idProjeto);
}