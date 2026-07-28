package service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import model.NaoConformidade;
import repository.NaoConformidadeRepository;

public class NaoConformidadeService {

    private static final Set<String> GRAVIDADES_VALIDAS =
            Set.of(
                    "Baixa",
                    "Média",
                    "Alta",
                    "Crítica"
            );

    private static final Set<String> STATUS_VALIDOS =
            Set.of(
                    "Registrada",
                    "Em Correção",
                    "Corrigida",
                    "Verificada",
                    "Fechada"
            );

    private final NaoConformidadeRepository
            naoConformidadeRepository;

    public NaoConformidadeService(
            NaoConformidadeRepository
                    naoConformidadeRepository) {

        if (naoConformidadeRepository == null) {
            throw new IllegalArgumentException(
                    "O repositório de não conformidades é obrigatório."
            );
        }

        this.naoConformidadeRepository =
                naoConformidadeRepository;
    }

    public NaoConformidade cadastrar(
            NaoConformidade naoConformidade) {

        validarNaoConformidade(naoConformidade);

        naoConformidade.setIdNaoConformidade(0);

        if (naoConformidade.getStatus() == null
                || naoConformidade.getStatus().isBlank()) {

            naoConformidade.setStatus("Registrada");
        }

        return naoConformidadeRepository.salvar(
                naoConformidade
        );
    }

    public List<NaoConformidade> listarTodos() {
        return naoConformidadeRepository.listarTodos();
    }

    public List<NaoConformidade> listarPorProjeto(
            int idProjeto) {

        validarIdProjeto(idProjeto);

        return naoConformidadeRepository
                .listarPorProjeto(idProjeto);
    }

    public Optional<NaoConformidade> buscarPorId(
            int idNaoConformidade) {

        validarIdNaoConformidade(
                idNaoConformidade
        );

        return naoConformidadeRepository.buscarPorId(
                idNaoConformidade
        );
    }

    public boolean atualizar(
            NaoConformidade naoConformidade) {

        if (naoConformidade == null) {
            throw new ServiceException(
                    "A não conformidade informada é obrigatória."
            );
        }

        validarIdNaoConformidade(
                naoConformidade
                        .getIdNaoConformidade()
        );

        validarNaoConformidade(
                naoConformidade
        );

        if (naoConformidadeRepository
                .buscarPorId(
                        naoConformidade
                                .getIdNaoConformidade()
                )
                .isEmpty()) {

            throw new ServiceException(
                    "Não conformidade não encontrada."
            );
        }

        return naoConformidadeRepository.atualizar(
                naoConformidade
        );
    }

    public boolean excluir(
            int idNaoConformidade) {

        validarIdNaoConformidade(
                idNaoConformidade
        );

        if (naoConformidadeRepository
                .buscarPorId(idNaoConformidade)
                .isEmpty()) {

            throw new ServiceException(
                    "Não conformidade não encontrada."
            );
        }

        return naoConformidadeRepository.excluir(
                idNaoConformidade
        );
    }

    private void validarNaoConformidade(
            NaoConformidade naoConformidade) {

        if (naoConformidade == null) {
            throw new ServiceException(
                    "A não conformidade informada é obrigatória."
            );
        }

        validarIdProjeto(
                naoConformidade.getIdProjeto()
        );

        if (naoConformidade.getDescricao() == null
                || naoConformidade
                        .getDescricao()
                        .isBlank()) {

            throw new ServiceException(
                    "A descrição da não conformidade é obrigatória."
            );
        }

        if (naoConformidade
                .getDescricao()
                .trim()
                .length() < 5) {

            throw new ServiceException(
                    "A descrição deve possuir pelo menos 5 caracteres."
            );
        }

        if (naoConformidade.getGravidade() == null
                || naoConformidade
                        .getGravidade()
                        .isBlank()) {

            throw new ServiceException(
                    "A gravidade é obrigatória."
            );
        }

        if (!GRAVIDADES_VALIDAS.contains(
                naoConformidade
                        .getGravidade()
                        .trim())) {

            throw new ServiceException(
                    "A gravidade deve ser Baixa, Média, Alta ou Crítica."
            );
        }

        if (naoConformidade
                .getIdResponsavelCorrecao() <= 0) {

            throw new ServiceException(
                    "O responsável pela correção é obrigatório."
            );
        }

        if (naoConformidade.getPrazoCorrecao()
                == null) {

            throw new ServiceException(
                    "O prazo para correção é obrigatório."
            );
        }

        String status =
                naoConformidade.getStatus();

        if (status == null || status.isBlank()) {
            status = "Registrada";
            naoConformidade.setStatus(status);
        }

        if (!STATUS_VALIDOS.contains(status.trim())) {
            throw new ServiceException(
                    "O status informado é inválido."
            );
        }

        if (naoConformidade.getDataCorrecao() != null
                && status.equals("Registrada")) {

            throw new ServiceException(
                    "Uma não conformidade corrigida não pode permanecer com o status Registrada."
            );
        }

        if ((status.equals("Corrigida")
                || status.equals("Verificada")
                || status.equals("Fechada"))
                && naoConformidade
                        .getDataCorrecao() == null) {

            throw new ServiceException(
                    "A data da correção deve ser informada para esse status."
            );
        }

        naoConformidade.setDescricao(
                naoConformidade
                        .getDescricao()
                        .trim()
        );

        if (naoConformidade.getCausaRaiz() != null) {
            naoConformidade.setCausaRaiz(
                    naoConformidade
                            .getCausaRaiz()
                            .trim()
            );
        }

        naoConformidade.setGravidade(
                naoConformidade
                        .getGravidade()
                        .trim()
        );

        naoConformidade.setStatus(
                status.trim()
        );
    }

    private void validarIdNaoConformidade(
            int idNaoConformidade) {

        if (idNaoConformidade <= 0) {
            throw new ServiceException(
                    "O código da não conformidade deve ser maior que zero."
            );
        }
    }

    private void validarIdProjeto(int idProjeto) {
        if (idProjeto <= 0) {
            throw new ServiceException(
                    "O código do projeto deve ser maior que zero."
            );
        }
    }
}