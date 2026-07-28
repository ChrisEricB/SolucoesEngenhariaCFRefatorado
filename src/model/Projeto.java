package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Projeto {

    private int idProjeto;
    private String nome;
    private String descricao;
    private Integer idCliente;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private BigDecimal orcamento;
    private String status;
    private int idResponsavel;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Projeto() {
    }

    public int getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(int idProjeto) {
        this.idProjeto = idProjeto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(BigDecimal orcamento) {
        this.orcamento = orcamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(int idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public String toString() {
        return "Projeto{" +
                "idProjeto=" + idProjeto +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", idCliente=" + idCliente +
                ", dataInicio=" + dataInicio +
                ", dataTermino=" + dataTermino +
                ", orcamento=" + orcamento +
                ", status='" + status + '\'' +
                ", idResponsavel=" + idResponsavel +
                '}';
    }
}
