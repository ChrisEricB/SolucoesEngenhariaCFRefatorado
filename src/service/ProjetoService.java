package service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import model.Projeto;
import repository.ProjetoRepository;

public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        if (projetoRepository == null) {
            throw new IllegalArgumentException(
                    "O repositório de projetos é obrigatório."
            );
        }

        this.projetoRepository = projetoRepository;
    }

    public Projeto cadastrar(Projeto projeto) {
        validarProjeto(projeto);
        projeto.setIdProjeto(0);

        return projetoRepository.salvar(projeto);
    }

    public List<Projeto> listarTodos() {
        return projetoRepository.listarTodos();
    }

    public Optional<Projeto> buscarPorId(int idProjeto) {
        validarId(idProjeto);

        return projetoRepository.buscarPorId(idProjeto);
    }

    public boolean atualizar(Projeto projeto) {
        if (projeto == null) {
            throw new ServiceException(
                    "O projeto informado é obrigatório."
            );
        }

        validarId(projeto.getIdProjeto());
        validarProjeto(projeto);

        if (projetoRepository
                .buscarPorId(projeto.getIdProjeto())
                .isEmpty()) {

            throw new ServiceException(
                    "Projeto não encontrado."
            );
        }

        return projetoRepository.atualizar(projeto);
    }

    public boolean excluir(int idProjeto) {
        validarId(idProjeto);

        if (projetoRepository
                .buscarPorId(idProjeto)
                .isEmpty()) {

            throw new ServiceException(
                    "Projeto não encontrado."
            );
        }

        return projetoRepository.excluir(idProjeto);
    }

    private void validarProjeto(Projeto projeto) {
        if (projeto == null) {
            throw new ServiceException(
                    "O projeto informado é obrigatório."
            );
        }

        if (projeto.getNome() == null
                || projeto.getNome().isBlank()) {

            throw new ServiceException(
                    "O nome do projeto é obrigatório."
            );
        }

        if (projeto.getNome().trim().length() < 3) {
            throw new ServiceException(
                    "O nome do projeto deve possuir pelo menos 3 caracteres."
            );
        }

        if (projeto.getDataInicio() == null) {
            throw new ServiceException(
                    "A data de início é obrigatória."
            );
        }

        if (projeto.getDataTermino() != null
                && projeto.getDataTermino()
                        .isBefore(projeto.getDataInicio())) {

            throw new ServiceException(
                    "A data de término não pode ser anterior à data de início."
            );
        }

        if (projeto.getOrcamento() != null
                && projeto.getOrcamento()
                        .compareTo(BigDecimal.ZERO) < 0) {

            throw new ServiceException(
                    "O orçamento não pode ser negativo."
            );
        }

        if (projeto.getStatus() == null
                || projeto.getStatus().isBlank()) {

            throw new ServiceException(
                    "O status do projeto é obrigatório."
            );
        }

        if (projeto.getIdResponsavel() <= 0) {
            throw new ServiceException(
                    "O responsável pelo projeto é obrigatório."
            );
        }

        projeto.setNome(projeto.getNome().trim());

        if (projeto.getDescricao() != null) {
            projeto.setDescricao(
                    projeto.getDescricao().trim()
            );
        }

        projeto.setStatus(
                projeto.getStatus().trim()
        );
    }

    private void validarId(int idProjeto) {
        if (idProjeto <= 0) {
            throw new ServiceException(
                    "O código do projeto deve ser maior que zero."
            );
        }
    }
}