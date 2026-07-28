package repository;

import java.util.List;
import java.util.Optional;
import model.Auditoria;

public interface AuditoriaRepository {

    Auditoria salvar(Auditoria auditoria);

    List<Auditoria> listarTodos();

    List<Auditoria> listarPorProjeto(int idProjeto);

    Optional<Auditoria> buscarPorId(int idAuditoria);

    boolean atualizar(Auditoria auditoria);

    boolean excluir(int idAuditoria);
}