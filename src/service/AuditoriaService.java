package service;

import java.util.List;
import java.util.Optional;
import model.Auditoria;
import repository.AuditoriaRepository;

public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(
            AuditoriaRepository auditoriaRepository) {

        if (auditoriaRepository == null) {
            throw new IllegalArgumentException(
                    "O repositório de auditorias é obrigatório."
            );
        }

        this.auditoriaRepository = auditoriaRepository;
    }

    public Auditoria cadastrar(Auditoria auditoria) {
        validarAuditoria(auditoria);

        auditoria.setIdAuditoria(0);

        return auditoriaRepository.salvar(auditoria);
    }

    public List<Auditoria> listarTodos() {
        return auditoriaRepository.listarTodos();
    }

    public List<Auditoria> listarPorProjeto(
            int idProjeto) {

        validarIdProjeto(idProjeto);

        return auditoriaRepository.listarPorProjeto(
                idProjeto
        );
    }

    public Optional<Auditoria> buscarPorId(
            int idAuditoria) {

        validarIdAuditoria(idAuditoria);

        return auditoriaRepository.buscarPorId(
                idAuditoria
        );
    }

    public boolean atualizar(Auditoria auditoria) {
        if (auditoria == null) {
            throw new ServiceException(
                    "A auditoria informada é obrigatória."
            );
        }

        validarIdAuditoria(
                auditoria.getIdAuditoria()
        );

        validarAuditoria(auditoria);

        if (auditoriaRepository
                .buscarPorId(auditoria.getIdAuditoria())
                .isEmpty()) {

            throw new ServiceException(
                    "Auditoria não encontrada."
            );
        }

        return auditoriaRepository.atualizar(
                auditoria
        );
    }

    public boolean excluir(int idAuditoria) {
        validarIdAuditoria(idAuditoria);

        if (auditoriaRepository
                .buscarPorId(idAuditoria)
                .isEmpty()) {

            throw new ServiceException(
                    "Auditoria não encontrada."
            );
        }

        return auditoriaRepository.excluir(
                idAuditoria
        );
    }

    private void validarAuditoria(
            Auditoria auditoria) {

        if (auditoria == null) {
            throw new ServiceException(
                    "A auditoria informada é obrigatória."
            );
        }

        validarIdProjeto(
                auditoria.getIdProjeto()
        );

        if (auditoria.getTipo() == null
                || auditoria.getTipo().isBlank()) {

            throw new ServiceException(
                    "O tipo da auditoria é obrigatório."
            );
        }

        if (auditoria.getDataAgendada() == null) {
            throw new ServiceException(
                    "A data agendada é obrigatória."
            );
        }

        if (auditoria.getIdAuditorResponsavel() <= 0) {
            throw new ServiceException(
                    "O auditor responsável é obrigatório."
            );
        }

        if (auditoria.getDataRealizacao() != null
                && auditoria.getDataRealizacao()
                        .isBefore(
                                auditoria.getDataAgendada()
                        )) {

            throw new ServiceException(
                    "A data de realização não pode ser anterior à data agendada."
            );
        }

        if (auditoria.getResultado() != null
                && !auditoria.getResultado().isBlank()
                && auditoria.getDataRealizacao() == null) {

            throw new ServiceException(
                    "Informe a data de realização antes de registrar o resultado."
            );
        }

        auditoria.setTipo(
                auditoria.getTipo().trim()
        );

        if (auditoria.getResultado() != null) {
            auditoria.setResultado(
                    auditoria.getResultado().trim()
            );
        }
    }

    private void validarIdAuditoria(
            int idAuditoria) {

        if (idAuditoria <= 0) {
            throw new ServiceException(
                    "O código da auditoria deve ser maior que zero."
            );
        }
    }

    private void validarIdProjeto(
            int idProjeto) {

        if (idProjeto <= 0) {
            throw new ServiceException(
                    "O código do projeto deve ser maior que zero."
            );
        }
    }
}