package repository;

import java.util.List;
import java.util.Optional;
import model.NaoConformidade;

public interface NaoConformidadeRepository {

    NaoConformidade salvar(NaoConformidade naoConformidade);

    List<NaoConformidade> listarTodos();

    List<NaoConformidade> listarPorProjeto(int idProjeto);

    Optional<NaoConformidade> buscarPorId(int idNaoConformidade);

    boolean atualizar(NaoConformidade naoConformidade);

    boolean excluir(int idNaoConformidade);
}